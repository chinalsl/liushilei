package cn.liushilei.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageProperties;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordUtil {

    /**
     * 提取文档内容
     * 1、提取所有文本文字
     * 2、提取表格中的文字
     * 3、提取附件的名称
     * @param inputStream
     * @return
     */
    public static String getContent(InputStream inputStream){
        List<String> contextList = new ArrayList<>();
        try {
            XWPFDocument document = new XWPFDocument(inputStream);
            // 获取文本块
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            paragraphs.forEach(paragraph ->{contextList.add(paragraph.getParagraphText());});

            // 获取表格内容
            List<XWPFTable> tables = document.getTables();
            for(XWPFTable table : tables){
                List<XWPFTableRow> rows = table.getRows();
                for(XWPFTableRow row : rows){
                    List<XWPFTableCell> cells = row.getTableCells();
                    for(XWPFTableCell cell : cells){
                        contextList.add(cell.getText());
                    }
                }
            }

            CTDocument1 document1 = document.getDocument();

            List<PackagePart> allEmbeddedParts = document.getAllEmbeddedParts();
            for(PackagePart packagePart : allEmbeddedParts){
                OPCPackage pkg = packagePart.getPackage();

//                packagePart.save(new FileOutputStream(new File("d:\\"+UUID.fastUUID()+".zip")));


                FileUtil.writeFromStream(packagePart.getInputStream(),new File("d:\\"+UUID.fastUUID()+".xlsx"));
            }

            List<POIXMLDocumentPart> relations = document.getRelations();

            List<POIXMLDocumentPart.RelationPart> relationParts = document.getRelationParts();

            System.out.println("aaa");

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        contextList.forEach(s->{
            sb.append(s);
        });
        return sb.toString();
    }

    public static void main(String[] args) {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\houyibing\\Desktop\\我是初号字体.docx"));
            String content = getContent(fileInputStream);
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



}
