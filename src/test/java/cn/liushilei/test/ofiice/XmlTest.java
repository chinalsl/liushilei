package cn.liushilei.test.ofiice;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlTest {

    public static void main(String[] args) {

//        StringBuilder sb = new StringBuilder();
//        sb.append("<svg>");
//        sb.append("你好");
//        sb.append("</svg>");
//
        StringBuilder sb = new StringBuilder();
        sb.append("<svg>");
        sb.append("<text>附件一.xlsx</text>");
        sb.append("</svg>");

        Document document = XmlUtil.readXML("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + UnicodeUtil.toString(sb.toString()));
        NodeList svg = document.getElementsByTagName("text");
        if (svg != null && svg.getLength() > 0) {
            String nodeValue = svg.item(0).getFirstChild().getNodeValue();
            System.out.println(nodeValue);
            System.out.println(nodeValue.substring(nodeValue.indexOf(".")+1));
        }

    }

}
