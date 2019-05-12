package cn.tycoding.common.utils.vcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GifEncoder {
    private int width;
    private int height;
    private Color transparent = null;
    private int transIndex;
    private int repeat = -1;
    private int delay = 0;
    private boolean started = false;
    private OutputStream out;
    private BufferedImage image;
    private byte[] pixels;
    private byte[] indexedPixels;
    private int colorDepth;
    private byte[] colorTab;
    private boolean[] usedEntry = new boolean[256];
    private int palSize = 7;
    private int dispose = -1;
    private boolean closeStream = false;
    private boolean firstFrame = true;
    private boolean sizeSet = false;
    private int sample = 10;

    public void setDelay(int ms) {
        delay = Math.round(ms / 10.0f);
    }

    public void setDispose(int code) {
        if (code >= 0) {
            dispose = code;
        }
    }

    public void setRepeat(int iter) {
        if (iter >= 0) {
            repeat = iter;
        }
    }

    public void setTransparent(Color c) {
        transparent = c;
    }

    void addFrame(BufferedImage im) {
        if ((im == null) || !started) {
            return;
        }
        try {
            if (!sizeSet) {
                setSize(im.getWidth(), im.getHeight());
            }
            image = im;
            getImagePixels();
            analyzePixels();
            if (firstFrame) {
                writeLSD();
                writePalette();
                if (repeat >= 0) {
                    writeNetscapeExt();
                }
            }
            writeGraphicCtrlExt();
            writeImageDesc();
            if (!firstFrame) {
                writePalette();
            }
            writePixels();
            firstFrame = false;
        } catch (IOException ignore) {
            // ignore
        }

    }


    public boolean outFlush() {
        try {
            out.flush();
            return true;
        } catch (IOException ignore) {
            // ignore
        }

        return false;
    }

    public byte[] getFrameByteArray() {
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    void finish() {
        if (!started)
            return;
        boolean ok = true;
        started = false;
        try {
            out.write(0x3b);
            out.flush();
            if (closeStream) {
                out.close();
            }
        } catch (IOException ignore) {
            // ignore
        }

    }

    public void reset() {

        transIndex = 0;
        out = null;
        image = null;
        pixels = null;
        indexedPixels = null;
        colorTab = null;
        closeStream = false;
        firstFrame = true;
    }

    public void setFrameRate(float fps) {
        if (fps != 0f) {
            delay = Math.round(100f / fps);
        }
    }

    void setQuality(int quality) {
        if (quality < 1)
            quality = 1;
        sample = quality;
    }

    private void setSize(int w, int h) {
        if (started && !firstFrame)
            return;
        width = w;
        height = h;
        if (width < 1)
            width = 320;
        if (height < 1)
            height = 240;
        sizeSet = true;
    }

    public boolean start(OutputStream os) {
        if (os == null)
            return false;
        boolean ok = true;
        closeStream = false;
        out = os;
        try {
            writeString("GIF89a");
        } catch (IOException e) {
            ok = false;
        }
        started = ok;
        return started;
    }

    public boolean start(String file) {
        boolean ok = true;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            ok = start(out);
            closeStream = true;
        } catch (IOException e) {
            ok = false;
        }
        started = ok;
        return started;
    }


    private void analyzePixels() {
        int len = pixels.length;
        int nPix = len / 3;
        indexedPixels = new byte[nPix];
        Quant nq = new Quant(pixels, len, sample);
        colorTab = nq.process();
        for (int i = 0; i < colorTab.length; i += 3) {
            byte temp = colorTab[i];
            colorTab[i] = colorTab[i + 2];
            colorTab[i + 2] = temp;
            usedEntry[i / 3] = false;
        }
        int k = 0;
        for (int i = 0; i < nPix; i++) {
            int index = nq.map(pixels[k++] & 0xff, pixels[k++] & 0xff, pixels[k++] & 0xff);
            usedEntry[index] = true;
            indexedPixels[i] = (byte) index;
        }
        pixels = null;
        colorDepth = 8;
        palSize = 7;
        if (transparent != null) {
            transIndex = findClosest(transparent);
        }
    }

    private int findClosest(Color c) {
        if (colorTab == null)
            return -1;
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int minpos = 0;
        int dmin = 256 * 256 * 256;
        int len = colorTab.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = i + 1;
            int dr = r - (colorTab[temp] & 0xff);
            int dg = g - (colorTab[temp] & 0xff);
            int db = b - (colorTab[i] & 0xff);
            int d = dr * dr + dg * dg + db * db;
            int index = i / 3;
            if (usedEntry[index] && (d < dmin)) {
                dmin = d;
                minpos = index;
            }
        }
        return minpos;
    }

    private void getImagePixels() {
        int w = image.getWidth();
        int h = image.getHeight();
        int type = image.getType();
        if ((w != width) || (h != height) || (type != BufferedImage.TYPE_3BYTE_BGR)) {
            BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = temp.createGraphics();
            g.drawImage(image, 0, 0, null);
            image = temp;
        }
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    private void writeGraphicCtrlExt() throws IOException {
        out.write(0x21); // extension introducer
        out.write(0xf9); // GCE label
        out.write(4); // data block size
        int transp;
        int disp;
        if (transparent == null) {
            transp = 0;
            disp = 0;
        } else {
            transp = 1;
            disp = 2;
        }
        if (dispose >= 0) {
            disp = dispose & 7;
        }
        disp <<= 2;

        out.write(disp | transp);

        writeShort(delay);
        out.write(transIndex);
        out.write(0);
    }

    private void writeImageDesc() throws IOException {
        out.write(0x2c);
        writeShort(0);
        writeShort(0);
        writeShort(width);
        writeShort(height);
        if (firstFrame) {
            out.write(0);
        } else {
            out.write(0x80 | palSize);
        }
    }

    private void writeLSD() throws IOException {
        writeShort(width);
        writeShort(height);
        out.write((0x80 | 0x70 | palSize));

        out.write(0);
        out.write(0);
    }


    private void writeNetscapeExt() throws IOException {
        out.write(0x21);
        out.write(0xff);
        out.write(11);
        writeString("NETSCAPE" + "2.0");
        out.write(3);
        out.write(1);
        writeShort(repeat);
        out.write(0);
    }


    private void writePalette() throws IOException {
        out.write(colorTab, 0, colorTab.length);
        int n = (3 * 256) - colorTab.length;
        for (int i = 0; i < n; i++) {
            out.write(0);
        }
    }


    private void writePixels() throws IOException {
        Encoder encoder = new Encoder(width, height, indexedPixels, colorDepth);
        encoder.encode(out);
    }


    private void writeShort(int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }


    private void writeString(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) {
            out.write((byte) s.charAt(i));
        }
    }
}
