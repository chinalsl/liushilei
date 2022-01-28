//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;

public class ToolBinary implements Tooler {
    private final byte[] dd;
    private final int s;
    private final int wp;
    private final int m;
    private BufferedImage img;

    public ToolBinary(int width, int height, int bps) {
        IndexColorModel cm;
        byte[] pal;
        switch(bps) {
            case 1:
                this.img = new BufferedImage(width, height, 12);
                break;
            case 2:
                pal = new byte[]{0, 85, -86, -1};
                cm = new IndexColorModel(2, 4, pal, pal, pal);
                this.img = new BufferedImage(width, height, 12, cm);
            case 3:
            default:
                break;
            case 4:
                pal = new byte[]{0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, -69, -52, -35, -18, -1};
                cm = new IndexColorModel(4, 16, pal, pal, pal);
                this.img = new BufferedImage(width, height, 12, cm);
        }

        this.s = bps;
        this.wp = width + 7 >> 3 << 3;
        this.m = (bps << 1) - 1;
        this.dd = this.img != null ? ((DataBufferByte)this.img.getRaster().getDataBuffer()).getData() : null;
    }

    public void set(int x, int y, int v) {
        v &= this.m;
        int d = y * this.wp + x * this.s;
        int b = d >> 3;
        int c = d & 7;
        d = this.dd[b] & 255;
        int r = 8 - c - this.s;
        int mm = ~(-256 | this.m << r);
        this.dd[b] = (byte)(v << r | d & mm);
    }

    public BufferedImage getBufferedImage() {
        return this.img;
    }

    public void setData(byte[] data) {
        System.arraycopy(data, 0, this.dd, 0, data.length);
    }

    public void setStrips(int y, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
