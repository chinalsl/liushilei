//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.emf.data;

import java.awt.geom.AffineTransform;

public final class FM {
    private FM() {
    }

    public static float[][] multiply(float[][] m1, float[][] m2) {
        float[][] result = new float[3][3];

        for(int col = 0; col < 3; ++col) {
            for(int row = 0; row < 3; ++row) {
                result[row][col] = m1[row][0] * m2[0][col] + m1[row][1] * m2[1][col] + m1[row][2] * m2[2][col];
            }
        }

        return result;
    }

    public static float[][] inverse(float[][] mm) {
        float d = mm[2][0] * mm[0][1] * mm[1][2] - mm[2][0] * mm[0][2] * mm[1][1] - mm[1][0] * mm[0][1] * mm[2][2] + mm[1][0] * mm[0][2] * mm[2][1] + mm[0][0] * mm[1][1] * mm[2][2] - mm[0][0] * mm[1][2] * mm[2][1];
        float t00 = (mm[1][1] * mm[2][2] - mm[1][2] * mm[2][1]) / d;
        float t01 = -(mm[0][1] * mm[2][2] - mm[0][2] * mm[2][1]) / d;
        float t02 = (mm[0][1] * mm[1][2] - mm[0][2] * mm[1][1]) / d;
        float t10 = -(-mm[2][0] * mm[1][2] + mm[1][0] * mm[2][2]) / d;
        float t11 = (-mm[2][0] * mm[0][2] + mm[0][0] * mm[2][2]) / d;
        float t12 = -(-mm[1][0] * mm[0][2] + mm[0][0] * mm[1][2]) / d;
        float t20 = (-mm[2][0] * mm[1][1] + mm[1][0] * mm[2][1]) / d;
        float t21 = -(-mm[2][0] * mm[0][1] + mm[0][0] * mm[2][1]) / d;
        float t22 = (-mm[1][0] * mm[0][1] + mm[0][0] * mm[1][1]) / d;
        float[][] result = new float[3][3];
        result[0][0] = t00;
        result[0][1] = t01;
        result[0][2] = t02;
        result[1][0] = t10;
        result[1][1] = t11;
        result[1][2] = t12;
        result[2][0] = t20;
        result[2][1] = t21;
        result[2][2] = t22;
        return result;
    }

    public static float[][] concatenate(float[][] m1, float[][] m2) {
        return multiply(m2, m1);
    }

    public static float[] transformPoint(float[][] mm, float x, float y) {
        float x_ = mm[0][0] * x + mm[1][0] * y + mm[2][0];
        float y_ = mm[0][1] * x + mm[1][1] * y + mm[2][1];
        return new float[]{x_, y_};
    }

    public static void transform(float[][] mm, float[] xy) {
        float x = xy[0];
        float y = xy[1];
        xy[0] = mm[0][0] * x + mm[1][0] * y + mm[2][0];
        xy[1] = mm[0][1] * x + mm[1][1] * y + mm[2][1];
    }

    public static void print(float[][] mm) {
        for(int row = 0; row < 3; ++row) {
            System.out.println(row + "(" + mm[row][0] + " , " + mm[row][1] + " , " + mm[row][2] + " )");
        }

    }

    public static float[][] getIdentity() {
        return new float[][]{{1.0F, 0.0F, 0.0F}, {0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, 1.0F}};
    }

    public static AffineTransform toAffine(float[][] ctm) {
        return new AffineTransform(ctm[0][0], ctm[0][1], ctm[1][0], ctm[1][1], ctm[2][0], ctm[2][1]);
    }
}
