package cn.liushilei.test.ofiice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**

 * 该类实现了图片的合并功能，可以选择水平合并或者垂直合并。
 * 当然此例只是针对两个图片的合并，如果想要实现多个图片的合并，只需要自己实现方法 BufferedImage
 * mergeImage(BufferedImage[] imgs, boolean isHorizontal)即可；
 * 而且这个方法更加具有通用性，但是时间原因不实现了，方法和两张图片实现是一样的
 */

public class ImageMerge {

    /**
     * @param fileUrl
     *            文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException
     *             路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl)
            throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }


    /**
     * 输出图片
     *
     * @param buffImg
     *            图像拼接叠加之后的BufferedImage对象
     * @param savePath
     *            图像拼接叠加之后的保存路径
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            File outFile = new File(savePath);
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            ImageIO.write(buffImg, savePath.substring(temp), outFile);
            System.out.println("ImageIO write...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage overlyingImage(BufferedImage buffImg, String text) throws IOException {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        // 画笔为黑色
        g2d.setColor(new Color(0));
        int x = 8;
        int y = buffImg.getWidth()/10*5;// 60位置开始写

        int line = text.length()/8+1;
        for(int i = 1 ; i <= line ;i++){
            int start = (i-1)*7;
            int end = i*7;
            System.out.println("start="+start);
            System.out.println("end="+end);
            String lineText  = "";
            if(end>text.length()){
                lineText = text.substring(start);
            }else {
                 lineText  = text.substring(start,end);
            }
            y += 12;
            g2d.drawString(lineText,x,y);
        }

        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }


    /**
     * Java 测试图片叠加方法
     */
    public static void overlyingImageTest() {

        String sourceFilePath = "C:\\Users\\houyibing\\Desktop\\png\\xls.png";
        String saveFilePath = "C:\\Users\\houyibing\\Desktop\\png\\new1.png";
        try {
            BufferedImage bufferImage1 = getBufferedImage(sourceFilePath);
            // 构建叠加层
            BufferedImage buffImg = overlyingImage(bufferImage1,"你好这是一份神奇的测试文档这是一份神奇的测试文档这是一份神奇的测试文档");
            // 输出水印图片
            generateSaveFile(buffImg, saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        // 测试图片的叠加
        overlyingImageTest();
    }

}