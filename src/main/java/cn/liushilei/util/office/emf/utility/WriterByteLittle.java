//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

public class WriterByteLittle implements DataWriter {
    private int p;
    private final byte[] dd;

    public WriterByteLittle(byte[] data) {
        this.dd = data;
    }

    public void putU8(int x) {
        this.dd[this.p++] = (byte)x;
    }

    public void putU16(int v) {
        this.dd[this.p++] = (byte)(v & 255);
        this.dd[this.p++] = (byte)(v >> 8 & 255);
    }

    public void putU24(int v) {
        this.dd[this.p++] = (byte)(v & 255);
        this.dd[this.p++] = (byte)(v >> 8 & 255);
        this.dd[this.p++] = (byte)(v >> 16 & 255);
    }

    public void putU32(int v) {
        this.dd[this.p++] = (byte)(v & 255);
        this.dd[this.p++] = (byte)(v >> 8 & 255);
        this.dd[this.p++] = (byte)(v >> 16 & 255);
        this.dd[this.p++] = (byte)(v >> 24 & 255);
    }

    public void putU64(long v) {
        this.dd[this.p++] = (byte)((int)(v & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 8 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 16 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 24 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 32 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 40 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 48 & 255L));
        this.dd[this.p++] = (byte)((int)(v >> 56 & 255L));
    }

    public void write(byte[] copyTo) {
        System.arraycopy(copyTo, 0, this.dd, this.p, copyTo.length);
        this.p += copyTo.length;
    }

    public void seek(int v) {
        this.p = v;
    }

    public int getLength() {
        return this.dd.length;
    }

    public int getPosition() {
        return this.p;
    }

    public void close() {
    }
}
