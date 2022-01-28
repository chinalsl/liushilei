//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static BufferedImage fixSubBufferedImage(BufferedImage image) {
        boolean needFix = image.getSampleModel().getWidth() != image.getWidth() || image.getSampleModel().getHeight() != image.getHeight();
        if (!needFix) {
            return image;
        } else {
            BufferedImage result;
            int p;
            int y;
            int x;
            if (image.getType() == 10) {
                result = new BufferedImage(image.getWidth(), image.getHeight(), 10);
                byte[] pixelBytes = ((DataBufferByte)result.getRaster().getDataBuffer()).getData();
                p = 0;

                for(y = 0; y < image.getHeight(); ++y) {
                    for(x = 0; x < image.getWidth(); ++x) {
                        pixelBytes[p++] = (byte)(image.getRGB(x, y) & 255);
                    }
                }

                return result;
            } else {
                result = image.getColorModel().hasAlpha() ? new BufferedImage(image.getWidth(), image.getHeight(), 2) : new BufferedImage(image.getWidth(), image.getHeight(), 1);
                int[] pixelInts = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();
                p = 0;

                for(y = 0; y < image.getHeight(); ++y) {
                    for(x = 0; x < image.getWidth(); ++x) {
                        pixelInts[p++] = image.getRGB(x, y);
                    }
                }

                return result;
            }
        }
    }
}
