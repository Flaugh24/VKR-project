package org.barmaley.vkr.ldapAuth;

/**
 * Created by impolun on 17.06.17.
 */

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by impolun on 08.06.17.
 */
public class ParsingABISXml {
    protected static Logger logger = Logger.getLogger("controller");

    private static Document getDocument(String path) throws Exception {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            return builder.parse(new File(path));
        } catch (Exception exception) {
            String message = "XML parsing error!";
            throw new Exception(message);
        }
    }

    static public void normreadAllABIS(String path){
        Document doc = null;
        try {
            //Получение документа по его пути
            doc = getDocument(path);
        }  catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        Node root = doc.getChildNodes().item(0);
        //2
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); ++i) {
            logger.debug("NodeName readAllABIS= "+nodeList.item(i).getNodeName());
            if (nodeList.item(i).getNodeName().compareTo("ns2:records") == 0){
                logger.debug("NodeName in if readAllABIS"+nodeList.item(i).getNodeName());

            }
        }
    }
    //Считывание из файла данных о всех сотрудниках.
    static public List<String> readAllABIS(Document doc) {

        List<String> list = new ArrayList<>();
//        Document doc = null;
//        try {
//            //Получение документа по его пути
//            doc = getDocument("/home");
//        }  catch (Exception exception) {
//            logger.debug(exception.getMessage());
//        }
        //1
        Node root = doc.getChildNodes().item(0);
        //2
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            logger.debug("NodeName readAllABIS= "+nodeList.item(i).getNodeName());
            if (nodeList.item(i).getNodeName().compareTo("ns2:records") == 0){
                logger.debug("NodeName in if readAllABIS"+nodeList.item(i).getNodeName());
                list = readParsing(nodeList.item(i));
            }
        }
        logger.debug("size nodeList readAllABIS= "+nodeList.getLength());
        return list;
    }



    static public List<String> readParsing(Node node) {
        List<String> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        logger.debug("----size nodeList in readParsing= "+nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            logger.debug("----NodeName readParsing= "+nodeList.item(i).getNodeName());
            if (nodeList.item(i).getNodeName().compareTo("ns2:record") == 0){
                logger.debug("----NodeName in if readParsing= "+nodeList.item(i).getNodeName());
                list = readParsing1(nodeList.item(i));
            }
        }
        return list;
    }

    static public List<String> readParsing1(Node node) {
        List<String> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        logger.debug("--------size nodeList in readParsing1= "+nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            logger.debug("--------NodeName readParsing1= "+nodeList.item(i).getNodeName());
            if (nodeList.item(i).getNodeName().compareTo("ns2:recordData") == 0){
                logger.debug("--------NodeName in if readParsing1= "+nodeList.item(i).getNodeName());
                list = readParsing2(nodeList.item(i));
            }
        }
        return list;
    }

    static public List<String> readParsing2(Node node) {
        List<String> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        logger.debug("--------size nodeList in readParsing2= "+nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            logger.debug("--------NodeName readParsing2= "+nodeList.item(i).getNodeName());
            if (nodeList.item(i).getNodeName().startsWith("record")){
                logger.debug("--------NodeName in if readParsing2= "+nodeList.item(i).getNodeName());
                list = readPerson(nodeList.item(i));
            }
        }
        return list;
    }

    static public List<String> readPerson(Node node) {
        List<String> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        logger.debug("size nodeList in readPerson= "+nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                logger.debug("NodeName= "+item.getNodeName());
                logger.debug("TextContent= "+item.getTextContent());
                list.add(item.getTextContent());
            }
        }
        return list;
    }
}

