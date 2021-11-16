package Plenarsitzung;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Protokoll {
    String Datum;
    String Titel;
    int Legislaturperiode;
    String Tagesordnungspunkt;
    String Sitzungsleitender;

    public void Read_File(String filename) throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = fac.newDocumentBuilder();
        System.out.println(filename);
        Document doc = db.parse(filename);
        NodeList redner_list = doc.getElementsByTagName("redner");
        for (int j = 0;j < redner_list.getLength(); j++){
            Node person = (redner_list.item(j));
            if (person.getNodeType()==Node.ELEMENT_NODE){
                Element per = (Element) person;
                String id = per.getAttribute("id");
                System.out.println(id);
            }

        }
        for (int i = 1; i <= 102; i++){
        String single_file = filename;
            single_file = single_file.concat("i");
            // System.out.println(single_file);

        }
        System.out.println(filename);
    }
}
