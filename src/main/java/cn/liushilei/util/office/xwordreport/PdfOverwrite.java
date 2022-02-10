package cn.liushilei.util.office.xwordreport;

import cn.hutool.core.io.FileUtil;
import cn.liushilei.util.office.DocxUtils;
import cn.liushilei.util.office.ImageUtils;
import com.lowagie.text.Chunk;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.microsoft.schemas.vml.impl.CTShapeImpl;
import fr.opensagres.poi.xwpf.converter.core.ImageShapeStyle;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.poi.xwpf.converter.pdf.internal.PdfMapper;
import fr.opensagres.poi.xwpf.converter.pdf.utils.EmfUtils;
import fr.opensagres.xdocreport.itext.extension.IITextContainer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.impl.CTTextImpl;
import org.openxmlformats.schemas.officeDocument.x2006.relationships.impl.STRelationshipIdImpl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

public class PdfOverwrite extends PdfMapper {
    public PdfOverwrite(XWPFDocument document, OutputStream out, PdfOptions options, Integer expectedPageCount) throws Exception {
        super(document, out, options, expectedPageCount);
    }


/*    @Override
    protected void visitRun( XWPFRun run, boolean pageNumber, String url,IITextContainer  paragraphContainer )
            throws Exception{
        CTR ctr = run.getCTR();
        CTRPr rPr = ctr.getRPr();
        boolean hasTexStyles = hasTextStyles( rPr );
        StringBuilder text = new StringBuilder();

        // Loop for each element of <w:run text, tab, image etc
        // to keep the order of thoses elements.
        XmlCursor c = ctr.newCursor();
        c.selectPath( "./*" );
        while ( c.toNextSelection() )
        {
            XmlObject o = c.getObject();

            if ( o instanceof CTText)
            {
                CTText ctText = (CTText) o;
                String tagName = o.getDomNode().getNodeName();
                // Field Codes (w:instrText, defined in spec sec. 17.16.23)
                // come up as instances of CTText, but we don't want them
                // in the normal text output
                if ( "w:instrText".equals( tagName ) )
                {

                }
                else
                {
                    if ( hasTexStyles )
                    {
                        text.append( ctText.getStringValue() );
                    }
                    else
                    {
                        visitText( ctText, pageNumber, paragraphContainer );
                    }
                }
            }
            else if ( o instanceof CTPTab)
            {
                visitTab( (CTPTab) o, paragraphContainer );
            }
            else if ( o instanceof CTBr)
            {
                visitBR( (CTBr) o, paragraphContainer );
            }
            else if ( o instanceof CTEmpty )
            {
                // Some inline text elements get returned not as
                // themselves, but as CTEmpty, owing to some odd
                // definitions around line 5642 of the XSDs
                // This bit works around it, and replicates the above
                // rules for that case
                String tagName = o.getDomNode().getNodeName();
                if ( "w:tab".equals( tagName ) )
                {
                    CTTabs tabs = stylesDocument.getParagraphTabs( run.getParagraph() );
                    visitTabs( tabs, paragraphContainer );
                }
                if ( "w:br".equals( tagName ) )
                {
                    visitBR( null, paragraphContainer );
                }
                if ( "w:cr".equals( tagName ) )
                {
                    visitBR( null, paragraphContainer );
                }
            }
            else if ( o instanceof CTDrawing )
            {
                //Picture - DrawingML Compatible
                visitDrawing( (CTDrawing) o, paragraphContainer );
            }
            else if (o instanceof org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture)
            {
                //Picture - VML Compatible
                org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture picture = (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture) o;
                visitVmlPicture(picture, paragraphContainer);
            }
            else if (o instanceof org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTObjectImpl)
            {
                org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTObjectImpl object = (org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTObjectImpl) o;
                visitVmlObjectPicture(object,paragraphContainer);
            }


        }
        if ( hasTexStyles && StringUtils.isNotEmpty( text.toString() ) )
        {
            visitStyleText( run, text.toString(), paragraphContainer, pageNumber );
        }
        c.dispose();
    }*/

    @Override
    public void visitVmlObjectPicture(org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTObjectImpl picture,
                                         IITextContainer pdfParentContainer)
            throws Exception
    {
        XmlCursor pictureCur = picture.newCursor();
        QName qname = new QName("urn:schemas-microsoft-com:vml", "shape");
        XmlObject[] xmlObjects = picture.selectChildren(qname);

        com.microsoft.schemas.vml.impl.CTShapeImpl shape = (CTShapeImpl) xmlObjects[0];
        String style = shape.getStyle();

        XmlObject[] imagedata = xmlObjects[0].selectChildren(new QName("urn:schemas-microsoft-com:vml", "imagedata"));
        XmlObject id = imagedata[0].selectAttribute(new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id"));
        {
            String rId6 = ((STRelationshipIdImpl) id).getStringValue();
            XWPFPictureData pictureData = getPictureDataByID(rId6);
//   将emf图写出来，用于临时测试
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\houyibing\\Desktop\\a.emf"));
//            fileOutputStream.write(pictureData.getData());
//            fileOutputStream.close();

            visitVmlObjectPicture(pictureData, style, pdfParentContainer);
        }

        pictureCur.dispose();
    }

    @Override
    public void visitVmlObjectPicture(
            XWPFPictureData pictureData, String style, IITextContainer pdfParentContainer)
    {
        if (pictureData == null) {
            return;
        }

        try {
            String emfText = cn.liushilei.util.office.EmfUtils.emfText(new ByteArrayInputStream(pictureData.getData()));
            //获取类型
            String type = emfText.substring(emfText.lastIndexOf(".") + 1).toLowerCase();

            byte[] pictureBytes = null;


            String path = PdfOverwrite.class.getResource("/png").getPath();

            System.out.println(type);
            switch (type){
                case "xls":
                    pictureBytes = ImageUtils.createIco(path+"/xls.png",emfText);
                    break;
                case "xlsx":
                    pictureBytes = ImageUtils.createIco(path+"/xls.png",emfText);
                    break;
                case "docx":
                    pictureBytes = ImageUtils.createIco(path+"/doc.png",emfText);
                    break;
                case "doc":
                    pictureBytes = ImageUtils.createIco(path+"/doc.png",emfText);
                    break;
                case "pptx":
                    pictureBytes = ImageUtils.createIco(path+"/ppt.png",emfText);
                    break;
                case "ppt":
                    pictureBytes = ImageUtils.createIco(path+"/ppt.png",emfText);
                    break;
                case "txt":
                    pictureBytes = ImageUtils.createIco(path+"/txt.png",emfText);
                    break;
                case "zip":
                    pictureBytes = ImageUtils.createIco(path+"/zip.png",emfText);
                    break;
                case "rar":
                    pictureBytes = ImageUtils.createIco(path+"/zip.png",emfText);
                    break;
                case "tar":
                    pictureBytes = ImageUtils.createIco(path+"/zip.png",emfText);
                    break;
                default:
                    pictureBytes = EmfUtils.emf2Image(new ByteArrayInputStream(pictureData.getData()));
            }

            Image img = Image.getInstance(pictureBytes);

            ImageShapeStyle imageStyle = ImageShapeStyle.parse(style);
            img.scaleAbsolute(imageStyle.getWidth()>100f?100f:imageStyle.getWidth(), imageStyle.getHeight()>100f?100f:imageStyle.getHeight());
//            img.scaleAbsolute(100f, 100f);
            IITextContainer parentOfParentContainer = pdfParentContainer.getITextContainer();
            if (parentOfParentContainer != null && parentOfParentContainer instanceof PdfPCell) {
                pdfParentContainer.addElement(img);
            } else {
                float chunkOffsetX = 0.0F;
                float chunkOffsetY = 0.0F;
                if (pdfParentContainer instanceof Paragraph) {
                    Paragraph paragraph = (Paragraph) pdfParentContainer;
                    paragraph.setSpacingBefore(paragraph.getSpacingBefore() + 5.0F);
                }
                pdfParentContainer.addElement(new Chunk(img, chunkOffsetX, chunkOffsetY, false));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
