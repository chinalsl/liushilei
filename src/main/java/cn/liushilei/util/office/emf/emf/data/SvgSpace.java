//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.emf.data;

import java.awt.image.BufferedImage;
import java.util.Base64;

public class SvgSpace implements Space {
    private int sc;
    private int ss = 0;
    private int fc;
    private int sw = 1;
    private int fontC = -16777216;
    private String fontName = "arial";
    private int fontSize = 12;
    private int fontWeight = 0;
    private final StringBuilder sb;
    private float[][] ctm = FM.getIdentity();
    private float[][] fontTM = FM.getIdentity();
    private Design design;

    public SvgSpace(StringBuilder sb) {
        this.sb = sb;
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    public void sLine(int x, int y, int x2, int y2) {
    }

    public void rect(int x, int y, int w, int h) {
    }

    private static String rgbToHex(int argb) {
        if (argb == 0) {
            return "none";
        } else if (argb == -1) {
            return "#fff";
        } else if (argb == -16777216) {
            return "#000";
        } else {
            int r = argb >> 16 & 255;
            int g = argb >> 8 & 255;
            int b = argb & 255;
            return String.format("#%02x%02x%02x", r, g, b);
        }
    }

    private void initPath(int[] cmds) {
        this.sb.append("\n<path d='");
        int i = 0;
        float[] xy = new float[2];

        while(i < cmds.length) {
            switch(cmds[i]) {
                case 0:
                    this.sb.append('M');
                    xy[0] = (float)cmds[i + 1];
                    xy[1] = (float)cmds[i + 2];
                    FM.transform(this.ctm, xy);
                    this.sb.append(Math.round(xy[0]));
                    this.sb.append(' ');
                    this.sb.append(Math.round(xy[1]));
                    i += 3;
                    break;
                case 1:
                    this.sb.append('L');
                    xy[0] = (float)cmds[i + 1];
                    xy[1] = (float)cmds[i + 2];
                    FM.transform(this.ctm, xy);
                    this.sb.append(Math.round(xy[0]));
                    this.sb.append(' ');
                    this.sb.append(Math.round(xy[1]));
                    i += 3;
                    break;
                case 2:
                    this.sb.append('C');
                    xy[0] = (float)cmds[i + 1];
                    xy[1] = (float)cmds[i + 2];
                    FM.transform(this.ctm, xy);
                    this.sb.append(Math.round(xy[0]));
                    this.sb.append(' ');
                    this.sb.append(Math.round(xy[1]));
                    this.sb.append(',');
                    xy[0] = (float)cmds[i + 3];
                    xy[1] = (float)cmds[i + 4];
                    FM.transform(this.ctm, xy);
                    this.sb.append(Math.round(xy[0]));
                    this.sb.append(' ');
                    this.sb.append(Math.round(xy[1]));
                    this.sb.append(',');
                    xy[0] = (float)cmds[i + 5];
                    xy[1] = (float)cmds[i + 6];
                    FM.transform(this.ctm, xy);
                    this.sb.append(Math.round(xy[0]));
                    this.sb.append(' ');
                    this.sb.append(Math.round(xy[1]));
                    i += 7;
                case 3:
                default:
                    break;
                case 4:
                    this.sb.append('Z');
                    ++i;
            }
        }

        this.sb.append("' ");
    }

    public void sPath(int[] cmds) {
        this.initPath(cmds);
        this.sb.append("fill='none' stroke='");
        this.sb.append(rgbToHex(this.sc));
        this.sb.append("' ");
        float temp = Math.abs((float)this.sw * this.ctm[0][0]);
        if (temp < 1.0F && temp > 0.0F) {
            temp = 1.0F;
        }

        int t = Math.round(temp);
        if (t > 1) {
            this.sb.append("stroke-width='");
            this.sb.append(t);
            this.sb.append("' ");
        }

        this.sb.append("/>");
    }

    public void fPath(int[] cmds) {
        this.initPath(cmds);
        this.sb.append("fill='");
        this.sb.append(rgbToHex(this.fc));
        this.sb.append("'/>");
    }

    public void sfPath(int[] cmds) {
        this.initPath(cmds);
        this.sb.append("fill='");
        this.sb.append(rgbToHex(this.fc));
        this.sb.append("' stroke='");
        this.sb.append(rgbToHex(this.sc));
        this.sb.append("' ");
        float temp = Math.abs((float)this.sw * this.ctm[0][0]);
        if (temp < 1.0F && temp > 0.0F) {
            temp = 1.0F;
        }

        int t = Math.round(temp);
        if (t > 1) {
            this.sb.append("stroke-width='");
            this.sb.append(t);
            this.sb.append("' ");
        }

        this.sb.append("/>");
    }

    public void image(int x, int y, BufferedImage image) {
    }

    public void bmpImage(int x, int y, byte[] data) {
        byte[] enc = Base64.getEncoder().encode(data);
        this.sb.append("\n<image ");
        this.sb.append(" transform='");
        this.sb.append(getMatrixString(this.ctm));
        this.sb.append("' ");
        this.sb.append("xlink:href='data:image/bmp;base64,");
        this.sb.append(new String(enc));
        this.sb.append("'/>");
    }

    public void fill() {
    }

    public void stroke() {
    }

    public void text(int x, int y, String str) {
        this.sb.append("\n<text x='");
        this.sb.append(x);
        this.sb.append("' y='");
        this.sb.append(y);
        this.sb.append("' font-size='");
        this.sb.append(this.fontSize);
        this.sb.append("' font-family='");
        this.sb.append(this.fontName);
        this.sb.append("' transform='");
        this.sb.append(getMatrixString(this.ctm));
        switch(this.fontWeight) {
            case 1:
                this.sb.append("' font-weight='");
                this.sb.append("bold");
                break;
            case 2:
                this.sb.append("' font-weight='");
                this.sb.append("italic");
        }

        if (this.fontC != -16777216) {
            this.sb.append("' fill='");
            this.sb.append(rgbToHex(this.fontC));
        }

        this.sb.append("'>");
        char[] chars = str.toCharArray();
        char[] var5 = chars;
        int var6 = chars.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            char c = var5[var7];
            if (c == ' ' || c > '/' && c < ':' || c > '@' && c < '[' || c > '`' && c < '{') {
                this.sb.append(c);
            } else {
                this.sb.append("&#x");
                this.sb.append(Integer.toHexString(c));
                this.sb.append(';');
            }
        }

        this.sb.append("</text>");
    }

    private static String getMatrixString(float[][] mm) {
        StringBuilder sb = new StringBuilder();
        sb.append("matrix(");
        double t = (double)Math.round((double)mm[0][0] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(' ');
        t = (double)Math.round((double)mm[0][1] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(' ');
        t = (double)Math.round((double)mm[1][0] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(' ');
        t = (double)Math.round((double)mm[1][1] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(' ');
        t = (double)Math.round((double)mm[2][0] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(' ');
        t = (double)Math.round((double)mm[2][1] * 100.0D) / 100.0D;
        sb.append(t);
        sb.append(')');
        return sb.toString();
    }

    public void clip(int x, int y, int x1, int y1) {
    }

    public void font(String name, int size, int weight) {
        this.fontName = name;
        this.fontSize = size;
        this.fontWeight = weight;
    }

    public void setFontC(int argb) {
        this.fontC = argb;
    }

    public void setSC(int argb) {
        this.sc = argb;
    }

    public void setFC(int argb) {
        this.fc = argb;
        this.design = null;
    }

    public void setSW(int w) {
        this.sw = w;
    }

    public void reset() {
        this.sc = 0;
        this.fc = 0;
        this.sw = 1;
        this.fontC = -16777216;
        this.fontName = "arial";
        this.fontSize = 12;
        this.fontWeight = 0;
    }

    public void sStyle(int s) {
        this.ss = s;
    }

    public void setMatrix(float[][] m) {
        this.ctm = m;
    }

    public float[][] getMatrix() {
        return this.ctm;
    }

    public void close() {
        this.sb.append("\n</svg>");
    }

    public int getSC() {
        return this.sc;
    }

    public int getFC() {
        return this.fc;
    }

    public int getFontC() {
        return this.fontC;
    }

    public void setFontMatrix(float[][] m) {
        this.fontTM = m;
    }

    public float[][] getFontMatrix() {
        return this.fontTM;
    }

    public void setFontName(String name) {
        this.fontName = name;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public Design getDesign() {
        return this.design;
    }

    public void ellipse(int x, int y, int rx, int ry) {
        this.sb.append("<ellipse cx='");
        this.sb.append(x);
        this.sb.append("' cy='");
        this.sb.append(y);
        this.sb.append("' rx='");
        this.sb.append(rx);
        this.sb.append("' ry='");
        this.sb.append(ry);
        this.sb.append("' transform='");
        this.sb.append(getMatrixString(this.ctm));
        this.sb.append("' ");
        this.sb.append("fill='");
        this.sb.append(rgbToHex(this.fc));
        this.sb.append("' stroke='");
        this.sb.append(rgbToHex(this.sc));
        this.sb.append("'/>");
    }
}
