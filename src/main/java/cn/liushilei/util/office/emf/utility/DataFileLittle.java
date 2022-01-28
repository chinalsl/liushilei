//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DataFileLittle implements DataReader {
    private int pos;
    private final RandomAccessFile ra;
    private final int len;
    private final byte[] temp;
    private final int tSize;
    private int ts;
    private int te;

    public DataFileLittle(File f) throws IOException {
        this.ra = new RandomAccessFile(f, "r");
        this.len = (int)f.length();
        this.tSize = Math.min(8192, this.len);
        this.temp = new byte[this.tSize];
        this.te = this.tSize;
        this.ra.read(this.temp);
    }

    public DataFileLittle(RandomAccessFile raf) throws IOException {
        this.ra = raf;
        this.len = (int)raf.length();
        this.tSize = Math.min(8192, this.len);
        this.temp = new byte[this.tSize];
        this.te = this.tSize;
        this.ra.read(this.temp);
    }

    public int getU8() throws IOException {
        int max;
        if (this.pos >= this.ts && this.pos < this.te) {
            max = this.temp[this.pos - this.ts] & 255;
            ++this.pos;
            return max;
        } else {
            this.ts = this.pos;
            this.te = this.ts + this.tSize;
            this.ra.seek((long)this.pos);
            max = Math.min(this.len - this.pos, this.tSize);
            this.ra.read(this.temp, 0, max);
            ++this.pos;
            return this.temp[0] & 255;
        }
    }

    public int getU16() throws IOException {
        return this.getU8() | this.getU8() << 8;
    }

    public int getU24() throws IOException {
        return this.getU8() | this.getU8() << 8 | this.getU8() << 16;
    }

    public int getU32() throws IOException {
        return this.getU8() | this.getU8() << 8 | this.getU8() << 16 | this.getU8() << 24;
    }

    public void read(byte[] copyTo) throws IOException {
        int i = 0;

        for(int ii = Math.min(copyTo.length, this.len - this.pos); i < ii; ++i) {
            copyTo[i] = (byte)this.getU8();
        }

    }

    public int getPosition() {
        return this.pos;
    }

    public void skip(int n) {
        this.pos += n;
    }

    public void moveTo(int p) {
        this.pos = p;
    }

    public int getLength() {
        return this.len;
    }

    public void close() throws IOException {
        this.ra.close();
    }
}
