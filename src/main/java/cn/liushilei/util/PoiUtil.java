package cn.liushilei.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class PoiUtil {


    public static void docxToPdf(String wordPath, String pdfPath)
            throws IOException {
        ZipSecureFile.setMinInflateRatio(-1.0d);
        File pdfFile = new File(pdfPath);
        // word文件
        File wordFile = new File(wordPath);

        // 1) 加载word文档生成 XWPFDocument对象
        InputStream in = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(in);

        PdfOptions options = PdfOptions.create();

        OutputStream out = new FileOutputStream(pdfFile);
        PdfConverter.getInstance().convert(document, out, options);

    }


    public static void main(String[] args) {
        try {
            docxToPdf("C:\\Users\\houyibing\\Desktop\\我是初号字体.docx","C:\\Users\\houyibing\\Desktop\\我是初号字体.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
