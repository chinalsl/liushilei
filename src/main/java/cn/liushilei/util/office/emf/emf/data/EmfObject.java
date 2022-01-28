//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.emf.data;

public class EmfObject {
    public static final int BRUSH = 1;
    public static final int PEN = 2;
    public static final int FONT = 3;
    public static final int PALETTE = 4;
    public static final int REGION = 5;
    public static final int COLORSPACE = 6;
    public final int type;
    public final int offset;

    public EmfObject(int type, int offset, int size) {
        this.type = type;
        this.offset = offset;
    }
}
