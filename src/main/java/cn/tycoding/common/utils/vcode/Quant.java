package cn.tycoding.common.utils.vcode;

public class Quant {
    private static final int NETSIZE = 256;
    private static final int PRIME1 = 499;
    private static final int PRIME2 = 491;
    private static final int PRIME3 = 487;
    private static final int PRIME4 = 503;

    private static final int MINPICTUREBYTES = (3 * PRIME4);


    private static final int MAXNETPOS = (NETSIZE - 1);
    private static final int NETBIASSHIFT = 4;
    private static final int NCYCLES = 100;

    private static final int INTBIASSHIFT = 16;
    private static final int INTBIAS = (1 << INTBIASSHIFT);
    private static final int GAMMASHIFT = 10;
    private static final int BETASHIFT = 10;
    private static final int BETA = (INTBIAS >> BETASHIFT);
    private static final int BETAGAMMA = (INTBIAS << (GAMMASHIFT - BETASHIFT));

    private static final int INITRAD = (NETSIZE >> 3);
    private static final int RADIUSBIASSHIFT = 6;
    private static final int RADIUSBIAS = (1 << RADIUSBIASSHIFT);
    private static final int INITRADIUS = (INITRAD * RADIUSBIAS);
    private static final int RADIUSDEC = 30;
    private static final int ALPHABIASSHIFT = 10;
    private static final int INITALPHA = (1 << ALPHABIASSHIFT);


    private static final int RADBIASSHIFT = 8;
    private static final int RADBIAS = (1 << RADBIASSHIFT);
    private static final int ALPHARADBSHIFT = (ALPHABIASSHIFT + RADBIASSHIFT);
    private static final int ALPHARADBIAS = (1 << ALPHARADBSHIFT);


    private byte[] thepicture;
    private int lengthcount;

    private int samplefac;


    private int[][] network;

    private int[] netindex = new int[256];


    private int[] bias = new int[NETSIZE];

    private int[] freq = new int[NETSIZE];
    private int[] radpower = new int[INITRAD];

    Quant(byte[] thepic, int len, int sample) {

        int i;
        int[] p;

        thepicture = thepic;
        lengthcount = len;
        samplefac = sample;

        network = new int[NETSIZE][];
        for (i = 0; i < NETSIZE; i++) {
            network[i] = new int[4];
            p = network[i];
            p[0] = p[1] = p[2] = (i << (NETBIASSHIFT + 8)) / NETSIZE;
            freq[i] = INTBIAS / NETSIZE;
            bias[i] = 0;
        }
    }

    private byte[] colorMap() {
        byte[] map = new byte[3 * NETSIZE];
        int[] index = new int[NETSIZE];
        for (int i = 0; i < NETSIZE; i++)
            index[network[i][3]] = i;
        int k = 0;
        for (int i = 0; i < NETSIZE; i++) {
            int j = index[i];
            map[k++] = (byte) (network[j][0]);
            map[k++] = (byte) (network[j][1]);
            map[k++] = (byte) (network[j][2]);
        }
        return map;
    }

    private void inxbuild() {

        int i;
        int j;
        int smallpos;
        int smallval;
        int[] p;
        int[] q;
        int previouscol;
        int startpos;

        previouscol = 0;
        startpos = 0;
        for (i = 0; i < NETSIZE; i++) {
            p = network[i];
            smallpos = i;
            smallval = p[1];

            for (j = i + 1; j < NETSIZE; j++) {
                q = network[j];
                if (q[1] < smallval) {
                    smallpos = j;
                    smallval = q[1];
                }
            }
            q = network[smallpos];
            if (i != smallpos) {
                j = q[0];
                q[0] = p[0];
                p[0] = j;
                j = q[1];
                q[1] = p[1];
                p[1] = j;
                j = q[2];
                q[2] = p[2];
                p[2] = j;
                j = q[3];
                q[3] = p[3];
                p[3] = j;
            }

            if (smallval != previouscol) {
                netindex[previouscol] = (startpos + i) >> 1;
                for (j = previouscol + 1; j < smallval; j++)
                    netindex[j] = i;
                previouscol = smallval;
                startpos = i;
            }
        }
        netindex[previouscol] = (startpos + MAXNETPOS) >> 1;
        for (j = previouscol + 1; j < 256; j++)
            netindex[j] = MAXNETPOS;
    }


    private void learn() {

        int i;
        int j;
        int b;
        int g;
        int r;
        int radius;
        int rad;
        int alpha;
        int step;
        int delta;
        int samplepixels;
        byte[] p;
        int pix;
        int lim;

        if (lengthcount < MINPICTUREBYTES)
            samplefac = 1;

        int alphadec = 30 + ((samplefac - 1) / 3);
        p = thepicture;
        pix = 0;
        lim = lengthcount;
        samplepixels = lengthcount / (3 * samplefac);
        delta = samplepixels / NCYCLES;
        alpha = INITALPHA;
        radius = INITRADIUS;

        rad = radius >> RADIUSBIASSHIFT;
        if (rad <= 1)
            rad = 0;
        for (i = 0; i < rad; i++)
            radpower[i] = alpha * (((rad * rad - i * i) * RADBIAS) / (rad * rad));


        if (lengthcount < MINPICTUREBYTES)
            step = 3;
        else if ((lengthcount % PRIME1) != 0)
            step = 3 * PRIME1;
        else {
            if ((lengthcount % PRIME2) != 0)
                step = 3 * PRIME2;
            else {
                if ((lengthcount % PRIME3) != 0)
                    step = 3 * PRIME3;
                else
                    step = 3 * PRIME4;
            }
        }

        i = 0;
        while (i < samplepixels) {
            b = (p[pix] & 0xff) << NETBIASSHIFT;
            g = (p[pix + 1] & 0xff) << NETBIASSHIFT;
            r = (p[pix + 2] & 0xff) << NETBIASSHIFT;
            j = contest(b, g, r);

            altersingle(alpha, j, b, g, r);
            if (rad != 0)
                alterneigh(rad, j, b, g, r);

            pix += step;
            if (pix >= lim)
                pix -= lengthcount;

            i++;
            if (delta == 0)
                delta = 1;
            if (i % delta == 0) {
                alpha -= alpha / alphadec;
                radius -= radius / RADIUSDEC;
                rad = radius >> RADIUSBIASSHIFT;
                if (rad <= 1)
                    rad = 0;
                for (j = 0; j < rad; j++)
                    radpower[j] = alpha * (((rad * rad - j * j) * RADBIAS) / (rad * rad));
            }
        }
    }

    public int map(int b, int g, int r) {

        int i;
        int j;
        int dist;
        int a;
        int bestd;
        int[] p;
        int best;

        bestd = 1000;
        best = -1;
        i = netindex[g];
        j = i - 1;

        while ((i < NETSIZE) || (j >= 0)) {
            if (i < NETSIZE) {
                p = network[i];
                dist = p[1] - g;
                if (dist >= bestd)
                    i = NETSIZE;
                else {
                    i++;
                    if (dist < 0)
                        dist = -dist;
                    a = p[0] - b;
                    if (a < 0)
                        a = -a;
                    dist += a;
                    if (dist < bestd) {
                        a = p[2] - r;
                        if (a < 0)
                            a = -a;
                        dist = dist + a;
                        if (dist < bestd) {
                            bestd = dist;
                            best = p[3];
                        }
                    }
                }
            }
            if (j >= 0) {
                p = network[j];
                dist = g - p[1];
                if (dist >= bestd)
                    j = -1;
                else {
                    j--;
                    if (dist < 0)
                        dist = -dist;
                    a = p[0] - b;
                    if (a < 0)
                        a = -a;
                    dist += a;
                    if (dist < bestd) {
                        a = p[2] - r;
                        if (a < 0)
                            a = -a;
                        dist += a;
                        if (dist < bestd) {
                            bestd = dist;
                            best = p[3];
                        }
                    }
                }
            }
        }
        return (best);
    }

    public byte[] process() {
        learn();
        unbiasnet();
        inxbuild();
        return colorMap();
    }


    private void unbiasnet() {

        int i;

        for (i = 0; i < NETSIZE; i++) {
            network[i][0] >>= NETBIASSHIFT;
            network[i][1] >>= NETBIASSHIFT;
            network[i][2] >>= NETBIASSHIFT;
            network[i][3] = i;
        }
    }


    private void alterneigh(int rad, int i, int b, int g, int r) {

        int j;
        int k;
        int lo;
        int hi;
        int a;
        int m;
        int[] p;

        lo = i - rad;
        if (lo < -1)
            lo = -1;
        hi = i + rad;
        if (hi > NETSIZE)
            hi = NETSIZE;

        j = i + 1;
        k = i - 1;
        m = 1;
        while ((j < hi) || (k > lo)) {
            a = radpower[m++];
            if (j < hi) {
                p = network[j++];
                try {
                    p[0] -= (a * (p[0] - b)) / ALPHARADBIAS;
                    p[1] -= (a * (p[1] - g)) / ALPHARADBIAS;
                    p[2] -= (a * (p[2] - r)) / ALPHARADBIAS;
                } catch (Exception ignore) {
                    // ignore
                }
            }
            if (k > lo) {
                p = network[k--];
                try {
                    p[0] -= (a * (p[0] - b)) / ALPHARADBIAS;
                    p[1] -= (a * (p[1] - g)) / ALPHARADBIAS;
                    p[2] -= (a * (p[2] - r)) / ALPHARADBIAS;
                } catch (Exception ignore) {
                    // ignore
                }
            }
        }
    }


    private void altersingle(int alpha, int i, int b, int g, int r) {

        /* alter hit neuron */
        int[] n = network[i];
        n[0] -= (alpha * (n[0] - b)) / INITALPHA;
        n[1] -= (alpha * (n[1] - g)) / INITALPHA;
        n[2] -= (alpha * (n[2] - r)) / INITALPHA;
    }


    private int contest(int b, int g, int r) {


        int i;
        int dist;
        int a;
        int biasdist;
        int bETAfreq;
        int bestpos;
        int bestbiaspos;
        int bestd;
        int bestbiasd;
        int[] n;

        bestd = ~(1 << 31);
        bestbiasd = bestd;
        bestpos = -1;
        bestbiaspos = bestpos;

        for (i = 0; i < NETSIZE; i++) {
            n = network[i];
            dist = n[0] - b;
            if (dist < 0)
                dist = -dist;
            a = n[1] - g;
            if (a < 0)
                a = -a;
            dist += a;
            a = n[2] - r;
            if (a < 0)
                a = -a;
            dist += a;
            if (dist < bestd) {
                bestd = dist;
                bestpos = i;
            }
            biasdist = dist - ((bias[i]) >> (INTBIASSHIFT - NETBIASSHIFT));
            if (biasdist < bestbiasd) {
                bestbiasd = biasdist;
                bestbiaspos = i;
            }
            bETAfreq = (freq[i] >> BETASHIFT);
            freq[i] -= bETAfreq;
            bias[i] += (bETAfreq << GAMMASHIFT);
        }
        freq[bestpos] += BETA;
        bias[bestpos] -= BETAGAMMA;
        return (bestbiaspos);
    }
}