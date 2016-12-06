package org.lightfw.utilx.ext;

import org.dom4j.*;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.lightfw.util.io.common.FileUtil;
import org.lightfw.util.lang.ExceptionUtil;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class XmlUtil {

    /**
     * 功能: 从Document对象中获取String
     *
     * @param document
     * @return
     */
    public static String load(Document document, String encoding) {
        String returnStr;
        ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
        BufferedOutputStream outer = new BufferedOutputStream(bytesStream);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tFactory.newTransformer();
            transformer.setOutputProperty("encoding", encoding);
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DocumentSource(document), new StreamResult(outer));
            returnStr = bytesStream.toString(encoding);
        } catch (Exception e) {
            throw ExceptionUtil.transform(e);
        }
        return returnStr;
    }

    /**
     * 转化xml字符串为Document对象
     *
     * @param xmlStr
     * @return
     */
    public static Document load(String xmlStr) {
        try {
            return new SAXReader().read(new StringReader(xmlStr));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从xml文件读取Document对象
     *
     * @param xmlFile
     * @return
     */
    public static Document loadFromFile(String xmlFile) {
        try {
            return new SAXReader().read(xmlFile);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从xml文件读取Document对象
     *
     * @param xmlFile
     * @return
     */
    public static Document loadFromClasspathFile(String xmlFile) {
        File file = FileUtil.getClasspathFile(xmlFile);
        try {
            return new SAXReader().read(file);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得一个RSS2.0格式的Document对象
     *
     * @param channelTitle
     * @param channelLink
     * @param channelDescription
     * @return
     */
    public static Document loadRss20Document(String channelTitle, String channelLink, String channelDescription) {
        Document doc = DocumentHelper.createDocument();
        Element rootEle = doc.addElement("rss").addAttribute("version", "2.0");
        Element channelEle = rootEle.addElement("channel");
        channelEle.addElement("title").setText(channelTitle);
        channelEle.addElement("link").setText(channelLink);
        channelEle.addElement("description").setText(channelDescription);
        return doc;
    }

    /**
     * 获得一个Rss2.0格式的Document对象--item
     *
     * @param itemTitle
     * @param itemLink
     * @param itemDescription
     * @param pubDate
     * @return
     */
    public static Document getRss20Item(String itemTitle, String itemLink, String itemDescription, String pubDate) {
        Document doc = DocumentHelper.createDocument();
        Element itemEle = doc.addElement("item");
        itemEle.addElement("title").setText(itemTitle);
        itemEle.addElement("link").setText(itemLink);
        itemEle.addElement("description").setText(itemDescription);
        itemEle.addElement("pubDate").setText(pubDate);
        return doc;
    }


    /**
     * 功能: 从xmlPath的资源转化成Document对象，带命名空间
     *
     * @param ruleXml
     * @param mNamespaceURIs
     * @return
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static Document parse(String ruleXml, Map mNamespaceURIs) throws MalformedURLException, DocumentException {
        if (ruleXml == null || ruleXml.length() == 0) {
            throw new NullPointerException("xml路径是空!");
        }
        SAXReader reader = new SAXReader();
        reader.getDocumentFactory().setXPathNamespaceURIs(mNamespaceURIs);
        Document document = null;
        if (ruleXml.startsWith("file:")) {
            document = reader.read(new URL(ruleXml));
        } else {
            document = reader.read(new File(ruleXml));
        }

        return document;
    }


    /**
     * 复制from下的所有节点(包括Attribute, Element, Text)到to
     *
     * @param from
     * @param to
     */
    public static void deepCopyElement(Element from, Element to) {
        if (from == null || to == null) {
            return;
        }
        List<Node> lNode = from.selectNodes("@*|node()");
        for (Node node : lNode) {
            if (node instanceof Attribute) {
                Attribute attr = (Attribute) node;
                to.addAttribute(attr.getName(), attr.getText());
            } else if (node instanceof Element) {
                Element ele = (Element) node;
                to.add(ele.createCopy());
            } else if (node instanceof Text) {
                to.setText(node.getText());
            }
        }
    }

    /**
     * 先清理to的所有node()和Attribute，在复制from下的所有节点(包括Attribute, Element, Text)到to
     *
     * @param from
     * @param to
     */
    public static void deepCopyElementWithClear(Element from, Element to) {
        if (from == null || to == null) {
            return;
        }
        List<Node> lNode = to.selectNodes("@*|node()");
        for (Node node : lNode) {
            to.remove(node);
        }
        deepCopyElement(from, to);
    }
}