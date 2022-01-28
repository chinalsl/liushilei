//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WriterFileBig implements DataWriter {
    private static final int MAXLEN = 8192;
    private static final int MINP = 8182;
    private int p;
    private int len;
    private final OutputStream out;
    private final byte[] buf = new byte[8192];

    public WriterFileBig(File file) throws IOException {
        this.out = new FileOutputStream(file);
    }

    public WriterFileBig(OutputStream stream) {
        this.out = stream;
    }

    public void putU8(int x) throws IOException {
        if (this.p > 8182) {
            this.flushNow();
        }

        this.buf[this.p++] = (byte)x;
    }

    public void putU16(int x) throws IOException {
        if (this.p > 8182) {
            this.flushNow();
        }

        this.buf[this.p++] = (byte)(x >>> 8 & 255);
        this.buf[this.p++] = (byte)(x & 255);
    }

    public void putU24(int x) throws IOException {
        if (this.p > 8182) {
            this.flushNow();
        }

        this.buf[this.p++] = (byte)(x >>> 16 & 255);
        this.buf[this.p++] = (byte)(x >>> 8 & 255);
        this.buf[this.p++] = (byte)(x & 255);
    }

    public void putU32(int x) throws IOException {
        if (this.p > 8182) {
            this.flushNow();
        }

        this.buf[this.p++] = (byte)(x >>> 24 & 255);
        this.buf[this.p++] = (byte)(x >>> 16 & 255);
        this.buf[this.p++] = (byte)(x >>> 8 & 255);
        this.buf[this.p++] = (byte)(x & 255);
    }

    public void putU64(long x) throws IOException {
        if (this.p > 8182) {
            this.flushNow();
        }

        this.buf[this.p++] = (byte)((int)(x >>> 56 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 48 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 40 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 32 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 24 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 16 & 255L));
        this.buf[this.p++] = (byte)((int)(x >>> 8 & 255L));
        this.buf[this.p++] = (byte)((int)(x & 255L));
    }

    public void write(byte[] copyTo) throws IOException {
        if (copyTo.length + this.p > 8182) {
            this.flushNow();
            this.out.write(copyTo);
            this.len += copyTo.length;
        } else {
            System.arraycopy(copyTo, 0, this.buf, this.p, copyTo.length);
            this.p += copyTo.length;
        }

    }

    public int getLength() {
        return this.len + this.p;
    }

    private void flushNow() throws IOException {
        this.out.write(this.buf, 0, this.p);
        this.len += this.p;
        this.p = 0;
        this.out.flush();
    }

    public void close() throws IOException {
        this.flushNow();
        this.out.close();
    }
}
