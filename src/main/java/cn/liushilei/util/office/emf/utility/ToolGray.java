//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ToolGray implements Tooler {
    private final int w;
    private final int h;
    private final byte[] dd;
    private final BufferedImage img;

    public ToolGray(int width, int height) {
        this.img = new BufferedImage(width, height, 10);
        this.h = height;
        this.w = width;
        this.dd = ((DataBufferByte)this.img.getRaster().getDataBuffer()).getData();
    }

    public void set(int x, int y, int v) {
        this.dd[y * this.w + x] = (byte)v;
    }

    public BufferedImage getBufferedImage() {
        return this.img;
    }

    public void setData(byte[] data) {
        int max = Math.min(this.h * this.w, data.length);
        System.arraycopy(data, 0, this.dd, 0, max);
    }

    public void setStrips(int y, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
