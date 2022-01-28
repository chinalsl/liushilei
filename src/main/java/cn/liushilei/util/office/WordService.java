package cn.liushilei.util.office;

import cn.hutool.core.io.FileUtil;

import java.io.*;

public abstract class  WordService {

    /**
     * 提取word内容
     * 1、提取所有文本文字
     * 2、提取表格中的文字
     * 3、提取附件的名称
     * @param inputStream
     * @return
     */
    public abstract String getContent(InputStream inputStream);

    /**
     * word转pdf
     * @param inputStream
     * @return
     */
    public abstract OutputStream toPdf(InputStream inputStream);

    public  OutputStream toPdf(File file){
        try {
            return toPdf(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toPdfFile(File wordFile,File pdfFile){
        try {
            OutputStream outputStream = toPdf(new FileInputStream(wordFile));
            if(outputStream instanceof  ByteArrayOutputStream) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) outputStream;
                FileUtil.writeBytes(out.toByteArray(), pdfFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toPdfFile(InputStream inputStream,File pdfFile){
        try {
            OutputStream outputStream = toPdf(inputStream);
            if(outputStream instanceof  ByteArrayOutputStream) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) outputStream;
                FileUtil.writeBytes(out.toByteArray(), pdfFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
