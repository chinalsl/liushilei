package cn.liushilei.util.office;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.liushilei.util.office.xwordreport.PdfConverter;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * docx实现类
 */
public class DocxUtils extends WordService {


    public String getContent(InputStream inputStream) {
        List<String> contextList = new ArrayList<>();
        try {
            XWPFDocument document = new XWPFDocument(inputStream);
            // 获取文本块
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            paragraphs.forEach(paragraph -> {
                contextList.add(paragraph.getParagraphText());
            });

            // 获取表格内容
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        contextList.add(cell.getText());
                    }
                }
            }

            CTDocument1 document1 = document.getDocument();

            List<PackagePart> allEmbeddedParts = document.getAllEmbeddedParts();
            for (PackagePart packagePart : allEmbeddedParts) {
                OPCPackage pkg = packagePart.getPackage();

//                packagePart.save(new FileOutputStream(new File("d:\\"+UUID.fastUUID()+".zip")));


                FileUtil.writeFromStream(packagePart.getInputStream(), new File("d:\\" + UUID.fastUUID() + ".xlsx"));
            }

            List<POIXMLDocumentPart> relations = document.getRelations();

            List<POIXMLDocumentPart.RelationPart> relationParts = document.getRelationParts();

            System.out.println("aaa");

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        contextList.forEach(s -> {
            sb.append(s);
        });
        return sb.toString();
    }

    @Override
    public OutputStream toPdf(InputStream inputStream) {

        XWPFDocument document = null;
        OutputStream out = null;
        try {
            document = new XWPFDocument(inputStream);
            out = new ByteArrayOutputStream();
            PdfOptions options = PdfOptions.create();
            options.fontProvider(new IFontProvider() {
                @Override
                public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                    // 配置字体
                    Font font = null;
                    try {
                        // 方案一：使用本地字体(本地需要有字体)
//                        BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//                                BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/seguisym.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                        // 方案二：使用jar包：iTextAsian，这样只需一个jar包就可以了
//                    BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

                        BaseFont bf = BaseFont.createFont(DocxUtils.class.getResource("/font").getPath()+"/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                        Font fontChinese = new Font(bf, size, style, color);
                        if (familyName != null){
                            fontChinese.setFamily(familyName);
                        }
                        return fontChinese;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return font;
                }
            });
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }


}
