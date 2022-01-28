//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.emf.data;

import java.awt.image.BufferedImage;

public interface Space {
    int M = 0;
    int L = 1;
    int C = 2;
    int S = 3;
    int Z = 4;
    int NORMAL = 0;
    int BOLD = 1;
    int ITALIC = 2;
    int BOLD_ITALIC = 3;
    int SOLID = 0;
    int DASH = 1;
    int DOT = 2;

    void sLine(int var1, int var2, int var3, int var4);

    void sPath(int[] var1);

    void fPath(int[] var1);

    void sfPath(int[] var1);

    void rect(int var1, int var2, int var3, int var4);

    void ellipse(int var1, int var2, int var3, int var4);

    void image(int var1, int var2, BufferedImage var3);

    void bmpImage(int var1, int var2, byte[] var3);

    void fill();

    void stroke();

    void setSW(int var1);

    void sStyle(int var1);

    void text(int var1, int var2, String var3);

    void clip(int var1, int var2, int var3, int var4);

    void font(String var1, int var2, int var3);

    void setFontC(int var1);

    int getFontC();

    void setSC(int var1);

    void setFC(int var1);

    int getSC();

    int getFC();

    void setMatrix(float[][] var1);

    float[][] getMatrix();

    void setFontMatrix(float[][] var1);

    float[][] getFontMatrix();

    void setFontName(String var1);

    String getFontName();

    void setFontSize(int var1);

    int getFontSize();

    void reset();

    void close();

    void setDesign(Design var1);

    Design getDesign();
}
