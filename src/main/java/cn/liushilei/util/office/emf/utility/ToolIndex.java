//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;

public class ToolIndex implements Tooler {
    private final byte[] dd;
    private final int s;
    private final int wp;
    private final int m;
    private final BufferedImage img;
    private final int stripPixels;

    public ToolIndex(int width, int height, int bps, byte[] rr, byte[] gg, byte[] bb) {
        IndexColorModel cm;
        if (bps > 4) {
            cm = new IndexColorModel(bps, 1 << bps, rr, gg, bb);
            this.img = new BufferedImage(width, height, 13, cm);
        } else {
            cm = new IndexColorModel(bps, 1 << bps, rr, gg, bb);
            this.img = new BufferedImage(width, height, 12, cm);
        }

        this.s = bps;
        this.wp = width + 7 >> 3 << 3;
        this.m = (bps << 1) - 1;
        this.dd = ((DataBufferByte)this.img.getRaster().getDataBuffer()).getData();
        this.stripPixels = (width * bps + 7) / 8;
    }

    public void set(int x, int y, int v) {
        if (this.s == 8) {
            this.dd[y * this.wp + x] = (byte)v;
        } else {
            v &= this.m;
            int a = y * this.wp + x * this.s;
            int b = a >> 3;
            int c = a & 7;
            int d = this.dd[b] & 255;
            int r = 8 - c - this.s;
            int mm;
            if (r >= 0) {
                mm = ~(-256 | this.m << r);
                this.dd[b] = (byte)(v << r | d & mm);
            } else {
                d = d << 8 | this.dd[b + 1] & 255;
                r = 16 - c - this.s;
                mm = ~(-65536 | this.m << r);
                d = v << r | d & mm;
                this.dd[b] = (byte)(d >> 8);
                this.dd[b + 1] = (byte)(d & 255);
            }
        }

    }

    public BufferedImage getBufferedImage() {
        return this.img;
    }

    public void setData(byte[] data) {
        System.arraycopy(data, 0, this.dd, 0, data.length);
    }

    public void setStrips(int y, byte[] data) {
        System.arraycopy(data, 0, this.dd, y * this.stripPixels, data.length);
    }
}
