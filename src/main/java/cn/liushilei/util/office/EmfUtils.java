package cn.liushilei.util.office;

import cn.liushilei.util.office.emf.emf.EmfDecoder;
import org.apache.poi.hemf.usermodel.HemfPicture;
import org.apache.poi.util.Units;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * poi 转png
 * 效果  只有字 没有图
 */
public class EmfUtils {


    public static void main(String[] args) throws IOException {
        String emf = "C:\\Users\\houyibing\\Desktop\\c.emf";
        String png = "C:\\Users\\houyibing\\Desktop\\c.png";
        for(int i = 1 ; i <= 13 ;i++) {
            png = "C:\\Users\\houyibing\\Desktop\\png\\c"+i+".png";
            emf2png(emf, png, i);
        }
    }

    /**
     * emf 转png
     *
     * @param emfFilePath emf文件路径
     * @param pngFilePath png文件路径
     * @throws IOException {@link IOException}
     */
    private static void emf2png(String emfFilePath, String pngFilePath,int imageType) throws IOException {
        File emfFile = new File(emfFilePath);
        try (FileInputStream fis = new FileInputStream(emfFile)) {
            // for EMF / EMF+
            HemfPicture emf = new HemfPicture(fis);
            Dimension2D dim = emf.getSize();
            int width = Units.pointsToPixel(dim.getWidth());
            // keep aspect ratio for height
            int height = Units.pointsToPixel(dim.getHeight());
            double max = Math.max(width, height);
            if (max > 1500) {
                width *= 1500 / max;
                height *= 1500 / max;
            }
            BufferedImage bufImg = new BufferedImage(width, height, imageType);
            bufImg.coerceData(true);
            Graphics2D g = bufImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
            emf.draw(g, new Rectangle2D.Double(0, 0, width, height));
            g.dispose();

            ImageIO.write(bufImg, "png", new File(pngFilePath));
        }
    }


    public static void emf2svg() {

        String emf = "C:\\Users\\houyibing\\Desktop\\a.emf";
        String png = "C:\\Users\\houyibing\\Desktop\\png\\a.svg";

        File emfFile = new File(emf);
        File svgFile = new File(png);
        try {
            EmfDecoder.toSVG(emfFile,svgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
