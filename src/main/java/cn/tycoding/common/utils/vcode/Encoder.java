package cn.tycoding.common.utils.vcode;

import java.io.IOException;
import java.io.OutputStream;

class Encoder {
    private static final int EOF = -1;

    private int imgW;
    private int imgH;
    private byte[] pixAry;
    private int initCodeSize;
    private int remaining;
    private int curPixel;


    private static final int BITS = 12;

    private static final int HSIZE = 5003;


    private int nBits;
    private int maxbits = BITS;
    private int maxcode;
    private int maxmaxcode = 1 << BITS;

    private int[] htab = new int[HSIZE];
    private int[] codetab = new int[HSIZE];

    private int hsizez = HSIZE;

    private int freeEnt = 0;

    private boolean clearFlg = false;


    private int gInitBits;

    private int clearCode;
    private int eOFCode;

    private int curAccum = 0;
    private int curBits = 0;

    private int[] masks = {0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F, 0x007F, 0x00FF, 0x01FF, 0x03FF, 0x07FF,
            0x0FFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF};

    private int aCount;

    private byte[] accum = new byte[256];

    Encoder(int width, int height, byte[] pixels, int colorDepth) {
        imgW = width;
        imgH = height;
        pixAry = pixels;
        initCodeSize = Math.max(2, colorDepth);
    }

    private void charOut(byte c, OutputStream outs) throws IOException {
        accum[aCount++] = c;
        if (aCount >= 254)
            flushChar(outs);
    }

    private void clBlock(OutputStream outs) throws IOException {
        clHash(hsizez);
        freeEnt = clearCode + 2;
        clearFlg = true;
        output(clearCode, outs);
    }

    private void clHash(int hsizez) {
        for (int i = 0; i < hsizez; ++i)
            htab[i] = -1;
    }

    private void compressIt(int initBits, OutputStream outs) throws IOException {
        int fcode;
        int i;
        int c;
        int ent;
        int disp;
        int hsizezReg;
        int hshift;

        gInitBits = initBits;

        clearFlg = false;
        nBits = gInitBits;
        maxcode = maxCode(nBits);

        clearCode = 1 << (initBits - 1);
        eOFCode = clearCode + 1;
        freeEnt = clearCode + 2;

        aCount = 0;

        ent = nextPixel();

        hshift = 0;
        for (fcode = hsizez; fcode < 65536; fcode *= 2)
            ++hshift;
        hshift = 8 - hshift;

        hsizezReg = hsizez;
        clHash(hsizezReg);

        output(clearCode, outs);

        while ((c = nextPixel()) != EOF) {
            fcode = (c << maxbits) + ent;
            i = (c << hshift) ^ ent;

            if (htab[i] == fcode) {
                ent = codetab[i];
                continue;
            } else if (htab[i] >= 0) {
                disp = hsizezReg - i;
                if (i == 0)
                    disp = 1;
                do {
                    int result = (i -= disp);
                    if (result < 0)
                        i += hsizezReg;

                    if (htab[i] == fcode) {
                        ent = codetab[i];
                        break;
                    }
                } while (htab[i] >= 0);
            }
            output(ent, outs);
            ent = c;
            if (freeEnt < maxmaxcode) {
                codetab[i] = freeEnt++;
                htab[i] = fcode;
            } else {
                clBlock(outs);
            }
        }
        output(ent, outs);
        output(eOFCode, outs);
    }

    void encode(OutputStream os) throws IOException {
        os.write(initCodeSize);

        remaining = imgW * imgH;
        curPixel = 0;

        compressIt(initCodeSize + 1, os);

        os.write(0);
    }

    private void flushChar(OutputStream outs) throws IOException {
        if (aCount > 0) {
            outs.write(aCount);
            outs.write(accum, 0, aCount);
            aCount = 0;
        }
    }

    private int maxCode(int nBits) {
        return (1 << nBits) - 1;
    }

    private int nextPixel() {
        if (remaining == 0)
            return EOF;

        --remaining;

        byte pix = pixAry[curPixel++];

        return pix & 0xff;
    }

    private void output(int code, OutputStream outs) throws IOException {
        curAccum &= masks[curBits];

        if (curBits > 0)
            curAccum |= (code << curBits);
        else
            curAccum = code;

        curBits += nBits;

        while (curBits >= 8) {
            charOut((byte) (curAccum & 0xff), outs);
            curAccum >>= 8;
            curBits -= 8;
        }

        if (freeEnt > maxcode || clearFlg) {
            if (clearFlg) {
                int result = nBits = gInitBits;
                maxcode = maxCode(result);
                clearFlg = false;
            } else {
                ++nBits;
                if (nBits == maxbits)
                    maxcode = maxmaxcode;
                else
                    maxcode = maxCode(nBits);
            }
        }

        if (code == eOFCode) {
            while (curBits > 0) {
                charOut((byte) (curAccum & 0xff), outs);
                curAccum >>= 8;
                curBits -= 8;
            }
            flushChar(outs);
        }
    }
}
