package cn.liushilei.util.office;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.XmlUtil;
import cn.liushilei.util.office.emf.emf.EmfDecoder;
import org.apache.poi.hemf.usermodel.HemfPicture;
import org.apache.poi.util.TempFile;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.draw.SVGUserAgent;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * poi 转png
 * 效果  只有字 没有图
 */
public class EmfUtils {


    public static void main(String[] args) throws IOException {
        String emf = "C:\\Users\\houyibing\\Desktop\\c.emf";
        String png = "C:\\Users\\houyibing\\Desktop\\c.png";
        for (int i = 1; i <= 13; i++) {
            png = "C:\\Users\\houyibing\\Desktop\\png\\c" + i + ".png";
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
    public static void emf2png(String emfFilePath, String pngFilePath, int imageType) throws IOException {
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
            EmfDecoder.toSVG(emfFile, svgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ByteArrayOutputStream emf2svg(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            EmfDecoder.toSVG(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String emfText(InputStream inputStream) {

        byte[] bytes = emf2svg(inputStream).toByteArray();
        String svgStr = new String(bytes);
        System.out.println(UnicodeUtil.toString(svgStr));
        System.out.println(unescape(svgStr));
        Document document = XmlUtil.readXML("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + UnicodeUtil.toString(svgStr));
        NodeList svg = document.getElementsByTagName("svg");
        if (svg != null && svg.getLength() > 0) {
            System.out.println(svg.item(0).getFirstChild().getNodeValue());
        }


        return null;
    }



    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }



}
