package cn.liushilei.util.office;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtils {

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
        int x = 2;
        int y = buffImg.getWidth()/10*5;// 60位置开始写

        List<LineText> lineList = new ArrayList<>();
        int lineWidth = 0;
        StringBuffer lineText = new StringBuffer();
        char[] chars = text.toCharArray();
        for(int i = 0 ; i < chars.length; i++){
            if(lineWidth>=80){
                lineList.add(new LineText(lineWidth,lineText.toString()));
                lineText = new StringBuffer();
                lineWidth = 0;
            }
            if(checkChinese(String.valueOf(chars[i]))){
                lineWidth += 10;
            }else if (chars[i]>='A'&&chars[i]<='Z'){
                lineWidth += 8;
            }else{
                lineWidth += 5;
            }
            lineText.append(String.valueOf(chars[i]));
        }

        if(lineWidth!=0){
            lineList.add(new LineText(lineWidth,lineText.toString()));
        }

        for(int i = 0 ; i < lineList.size() ;i++){
            y += 12;
            LineText lt = lineList.get(i);
            //左右留白再居中
            if(lt.getWidth()>=80) {
                x = 2;
            }else{
                x = (100 - lt.getWidth()) / 2;
            }
            g2d.drawString(lt.getText(),x,y);
            //还原默认
            x =2;
        }

        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    /**
     * 判断是否为汉字
     * @param chartStr
     * @return
     */
    public static boolean checkChinese(String chartStr) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(chartStr);
        if (m.find()) {
            return true;
        }
        return false;
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

    public static byte[] createIco(String icoPath,String text){
        try {
            BufferedImage bufferImage1 = getBufferedImage(icoPath);
            // 构建叠加层
            BufferedImage buffImg = overlyingImage(bufferImage1,text);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(buffImg,"png",byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
            // 输出水印图片
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        // 测试图片的叠加
        overlyingImageTest();
    }


    static class LineText{
        private int width;
        private String text;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public LineText(int width,String text){
            this.width = width;
            this.text = text;
        }
    }


}