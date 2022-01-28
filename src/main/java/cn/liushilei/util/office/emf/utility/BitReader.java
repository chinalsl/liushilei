//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

public class BitReader {
    private int p;
    private final byte[] data;
    private int bs;
    private int b;

    public BitReader(byte[] data) {
        this.data = data;
    }

    public byte readBit() {
        if (this.bs < 1) {
            this.b = this.b << 8 | this.data[this.p++] & 255;
            this.bs += 8;
        }

        --this.bs;
        return (byte)(this.b >>> this.bs & 1);
    }

    public int readBits(int n) {
        while(this.bs < n) {
            this.b = this.b << 8 | this.data[this.p++] & 255;
            this.bs += 8;
        }

        this.bs -= n;
        return this.b >>> this.bs & (1 << n) - 1;
    }

    public void moovBoundary(int pos) {
        this.p = pos;
        this.bs = 0;
        this.b = 0;
    }
}
