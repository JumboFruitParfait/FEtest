package fedex.fedexlocationservice;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLparser {

    public String getLocationID(String campaignID){
        try {
            File xmlFile = new File("/app/src/main/res/id_dictionary.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("campaign");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("campaignID") == campaignID) {
                        return eElement.getElementsByTagName("locationID").item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Location not found!";
    }
}
