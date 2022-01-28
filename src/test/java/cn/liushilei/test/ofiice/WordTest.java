package cn.liushilei.test.ofiice;

import cn.liushilei.util.office.DocxUtils;
import cn.liushilei.util.office.WordService;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

public class WordTest {
    
    @Test
    public void getContent(){
        String fileName = "/office/我是初号字体.docx";

        try {
            InputStream inputStream =   WordTest.class.getResourceAsStream( fileName  );
            WordService wordService = new DocxUtils();
            String content = wordService.getContent(inputStream);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toPdf(){
        String fileName = "/office/我是初号字体.docx";
        try {
            InputStream inputStream =  WordTest.class.getResourceAsStream( fileName  );
            WordService wordService = new DocxUtils();
            wordService.toPdfFile(inputStream,new File(WordTest.class.getResource("/").getPath()+"/office/我是初号字体.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void toPdf2(){
        String fileName = "/office/我爱你中国.docx";
        try {
            InputStream inputStream =  WordTest.class.getResourceAsStream( fileName  );
            WordService wordService = new DocxUtils();
            wordService.toPdfFile(inputStream,new File(WordTest.class.getResource("/").getPath()+"/office/我爱你中国.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toPdf3(){
        String fileName = "/office/告警收敛调研.docx";
        try {
            InputStream inputStream =  WordTest.class.getResourceAsStream( fileName  );
            WordService wordService = new DocxUtils();
            wordService.toPdfFile(inputStream,new File(WordTest.class.getResource("/").getPath()+"/office/告警收敛调研.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toPdf4(){
        String fileName = "/office/cboss维护手册QA问答对.docx";
        try {
            InputStream inputStream =  WordTest.class.getResourceAsStream( fileName  );
            WordService wordService = new DocxUtils();
            wordService.toPdfFile(inputStream,new File(WordTest.class.getResource("/").getPath()+"/office/cboss维护手册QA问答对.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
