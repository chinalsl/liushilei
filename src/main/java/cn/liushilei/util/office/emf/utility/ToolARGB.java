//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ToolARGB implements Tooler {
    private final int w;
    private final BufferedImage img;
    private final int[] dd;

    public ToolARGB(int width, int height) {
        this.img = new BufferedImage(width, height, 2);
        this.dd = ((DataBufferInt)this.img.getRaster().getDataBuffer()).getData();
        this.w = width;
    }

    public void set(int x, int y, int v) {
        this.dd[y * this.w + x] = v;
    }

    public BufferedImage getBufferedImage() {
        return this.img;
    }

    public void setData(byte[] data) {
        int p = 0;
        int i = 0;

        for(int ii = data.length / 4; i < ii; ++i) {
            this.dd[i] = (data[p++] & 255) << 16 | (data[p++] & 255) << 8 | data[p++] & 255 | (data[p++] & 255) << 24;
        }

    }

    public void setStrips(int y, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
