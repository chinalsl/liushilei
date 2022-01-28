//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.emf.data;

import cn.liushilei.util.office.emf.bmp.BmpDecoder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ImageSpace implements Space {
    private final Graphics2D g2;
    private int sc;
    private int ss = 0;
    private int fc;
    private int sw = 1;
    private int fontC = -16777216;
    private String fontName = "arial";
    private int fontSize = 12;
    private int fontWeight = 0;
    private float[][] ctm = FM.getIdentity();
    private float[][] fontTM = FM.getIdentity();
    private Design design;

    public ImageSpace(BufferedImage image) {
        this.g2 = image.createGraphics();
    }

    public void sLine(int x, int y, int x2, int y2) {
    }

    public void sPath(int[] cmds) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.sc, true));
        Shape shape = getShape(cmds);
        this.g2.setStroke(new BasicStroke((float)this.sw));
        this.g2.draw(shape);
    }

    private static Shape getShape(int[] cmds) {
        GeneralPath path = new GeneralPath();
        int i = 0;

        while(i < cmds.length) {
            int x;
            int y;
            switch(cmds[i]) {
                case 0:
                    x = cmds[i + 1];
                    y = cmds[i + 2];
                    i += 3;
                    path.moveTo((float)x, (float)y);
                    break;
                case 1:
                    x = cmds[i + 1];
                    y = cmds[i + 2];
                    path.lineTo((float)x, (float)y);
                    i += 3;
                    break;
                case 2:
                    x = cmds[i + 1];
                    y = cmds[i + 2];
                    int x2 = cmds[i + 3];
                    int y2 = cmds[i + 4];
                    int x3 = cmds[i + 5];
                    int y3 = cmds[i + 6];
                    path.curveTo((float)x, (float)y, (float)x2, (float)y2, (float)x3, (float)y3);
                    i += 7;
                case 3:
                default:
                    break;
                case 4:
                    path.closePath();
                    ++i;
            }
        }

        return path;
    }

    public void fPath(int[] cmds) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.fc, true));
        Shape shape = getShape(cmds);
        this.g2.fill(shape);
    }

    public void sfPath(int[] cmds) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.fc, true));
        Shape shape = getShape(cmds);
        this.g2.fill(shape);
        this.g2.setColor(new Color(this.sc, true));
        this.g2.setStroke(new BasicStroke((float)this.sw));
        this.g2.draw(shape);
    }

    public void rect(int x, int y, int w, int h) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.fc, true));
        this.g2.fillRect(x, y, w, h);
        this.g2.setColor(new Color(this.sc, true));
        this.g2.drawRect(x, y, w, h);
    }

    public void ellipse(int x, int y, int rx, int ry) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.fc, true));
        this.g2.fillOval(x, y, rx, ry);
        this.g2.setColor(new Color(this.sc, true));
        this.g2.setStroke(new BasicStroke((float)this.sw));
        this.g2.drawOval(x, y, rx, ry);
    }

    public void image(int x, int y, BufferedImage image) {
    }

    public void bmpImage(int x, int y, byte[] data) {
        try {
            this.g2.setTransform(FM.toAffine(this.ctm));
            BmpDecoder dec = new BmpDecoder();
            BufferedImage bmp = dec.read(data);
            this.g2.drawImage(bmp, x, y, (ImageObserver)null);
        } catch (Exception var6) {
            System.out.println("Error reading bmp image");
        }

    }

    public void fill() {
    }

    public void stroke() {
    }

    public void setSW(int w) {
        this.sw = w;
    }

    public void sStyle(int s) {
        this.ss = s;
    }

    public void text(int x, int y, String str) {
        this.g2.setTransform(FM.toAffine(this.ctm));
        this.g2.setColor(new Color(this.fontC, true));
        int fw = 0;
        if (this.fontWeight == 1) {
            fw = 1;
        } else if (this.fontWeight == 2) {
            fw = 2;
        }

        Font font = new Font(this.fontName, fw, this.fontSize);
        this.g2.setFont(font);
        this.g2.drawString(str, x, y);
    }

    public void clip(int x, int y, int x2, int y2) {
    }

    public void font(String name, int size, int weight) {
        this.fontName = name;
        this.fontSize = size;
        this.fontWeight = weight;
    }

    public void setFontC(int argb) {
        this.fontC = argb;
    }

    public int getFontC() {
        return this.fontC;
    }

    public void setSC(int argb) {
        this.sc = argb;
    }

    public void setFC(int argb) {
        this.fc = argb;
    }

    public int getSC() {
        return this.sc;
    }

    public int getFC() {
        return this.fc;
    }

    public void setMatrix(float[][] m) {
        this.ctm = m;
    }

    public float[][] getMatrix() {
        return this.ctm;
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

    public void reset() {
    }

    public void close() {
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public Design getDesign() {
        return this.design;
    }
}
