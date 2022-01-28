//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

public class DataByteLittle implements DataReader {
    private int p;
    private final byte[] data;

    public DataByteLittle(byte[] data) {
        this.data = data;
        this.p = 0;
    }

    public int getU8() {
        return this.data[this.p++] & 255;
    }

    public int getU16() {
        return this.data[this.p++] & 255 | (this.data[this.p++] & 255) << 8;
    }

    public int getU24() {
        return this.data[this.p++] & 255 | (this.data[this.p++] & 255) << 8 | (this.data[this.p++] & 255) << 16;
    }

    public int getU32() {
        return this.data[this.p++] & 255 | (this.data[this.p++] & 255) << 8 | (this.data[this.p++] & 255) << 16 | (this.data[this.p++] & 255) << 24;
    }

    public void read(byte[] copyTo) {
        System.arraycopy(this.data, this.p, copyTo, 0, copyTo.length);
        this.p += copyTo.length;
    }

    public void skip(int n) {
        this.p += n;
    }

    public void moveTo(int p) {
        this.p = p;
    }

    public int getLength() {
        return this.data.length;
    }

    public void close() {
    }

    public int getPosition() {
        return this.p;
    }

    public String getFOURCC() {
        byte[] bb = new byte[4];
        this.read(bb);
        return new String(bb);
    }
}
