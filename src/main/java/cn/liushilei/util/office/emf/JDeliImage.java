//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.liushilei.util.office.emf;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JDeliImage {
    protected JDeliImage() {
    }

    public static BufferedImage optimiseImage(BufferedImage image) {
        ClassLoader loader = JDeliImage.class.getClassLoader();
        boolean isJPedal = loader.getResource("org/jpedal/PdfDecoderInt.class") != null;
        if (!isJPedal && image != null) {
            Graphics2D g2 = image.createGraphics();
            int crw = image.getWidth();
            int crh = image.getHeight();
            g2.setColor(new Color(0, 65, 130, 128));
            RenderingHints hints = g2.getRenderingHints();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x2 = 0 + crw;
            int y2 = 0 + crh;
            g2.drawLine(0, 0, x2, y2);
            g2.drawLine(0, y2, x2, 0);
            g2.setFont(new Font("Serif", 0, 20));
            byte[] text = new byte[]{73, 67, 100, 107, 104, 31, 83, 113, 104, 96, 107, 31, 111, 113, 110, 117, 104, 99, 100, 99, 31, 97, 120, 31, 72, 67, 81, 82, 110, 107, 116, 115, 104, 110, 109, 114};
            if (text[0] == 73) {
                for(int i = 0; i != text.length; ++i) {
                    ++text[i];
                }
            }

            String s1 = new String(text);
            g2.setFont(scaleFontToFit(s1, crw, g2, g2.getFont()));
            int msgWidth = (int)g2.getFont().getStringBounds(s1, g2.getFontRenderContext()).getWidth();
            g2.setColor(new Color(0, 65, 130, 178));
            g2.drawString(s1, (0 + x2 - 0 - msgWidth) / 2, y2 - g2.getFontMetrics().getDescent());
            g2.setRenderingHints(hints);
            g2.setPaint(Color.RED);
            g2.drawLine(0, 0, crw, crh);
            g2.drawLine(0, crh, crw, 0);
        }

        return image;
    }

    private static Font scaleFontToFit(String text, int width, Graphics g, Font pFont) {
        float fontSize = (float)pFont.getSize();
        float fWidth = (float)g.getFontMetrics(pFont).stringWidth(text);
        fontSize = (float)width / fWidth * fontSize;
        pFont = pFont.deriveFont(fontSize);

        for(fWidth = (float)g.getFontMetrics(pFont).stringWidth(text); fWidth >= (float)width; fWidth = (float)g.getFontMetrics(pFont).stringWidth(text)) {
            --fontSize;
            pFont = pFont.deriveFont(fontSize);
        }

        return pFont;
    }


}
