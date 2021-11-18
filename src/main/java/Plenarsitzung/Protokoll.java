package Plenarsitzung;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Protokoll {
    String Datum;
    String Titel;
    int Legislaturperiode;
    String Tagesordnungspunkt;
    String Sitzungsleitender;
    ArrayList<Redner> Redner_list = new ArrayList();

    // Unzips the folder and provides the path for every xml file.
    public void Open_Zip(String zip) throws IOException {
        //xml_files = list
        ZipFile zipFile = new ZipFile(zip);
        Enumeration<? extends ZipEntry> xml = zipFile.entries();
        System.out.println("Die Parlamentsprotokolle werden eingelesen. Bitte haben Sie einen Moment Geduld.");
        while (xml.hasMoreElements()) {
            String xml_path = zip.replace(".zip", "\\" + xml.nextElement());
            try{
                if (xml_path.contains("xml")) {
                    Read_File(xml_path);
                }
            }
            catch(Exception e){
                System.out.println("das hat nicht geklappt");
            }
        }
    }

    public void Read_File(String filename) throws IOException, SAXException, ParserConfigurationException {
        List Fraktion_Liste = new ArrayList();
        List Nachnamen_Liste = new ArrayList();
        List Vornamen_Liste = new ArrayList();
        List Titel_Liste = new ArrayList();
        List Namenszusatz_Liste = new ArrayList();

        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = fac.newDocumentBuilder();
        //System.out.println(filename);
        Document doc = db.parse(filename);
        NodeList redner_list = doc.getElementsByTagName("redner");
        // System.out.println("anzahl redner:"+ redner_list.getLength());
        for (int j = 0;j < redner_list.getLength(); j++){
            // Fill up all Lists with zeros so that every list has the same size.
            Titel_Liste.add("0");
            Vornamen_Liste.add("0");
            Nachnamen_Liste.add("0");
            Fraktion_Liste.add("0");
            Namenszusatz_Liste.add("0");

            Node person = (redner_list.item(j));
            if (person.getNodeType()==Node.ELEMENT_NODE){
                Element per = (Element) person;
                String id = per.getAttribute("id");
                //System.out.println(id);
                NodeList child_list = per.getChildNodes();
                for (int n = 0; n < child_list.getLength(); n++){
                    Node child = child_list.item(n);
                    if (child.getNodeType()==Node.ELEMENT_NODE){
                        Element children = (Element) child;
                        String ch = children.getAttribute("vorname");

                        //System.out.println(children.getTextContent());

                        NodeList name_list = children.getChildNodes();
                        //System.out.println("hi" + name_list.getLength());
                        for (int t = 0; t < name_list.getLength(); t++){
                            Node name = name_list.item(t);
                            if (name.getNodeType()==Node.ELEMENT_NODE){
                                Element vorname = (Element) name;
                                //System.out.println(vorname.getTextContent());
                                //System.out.println(vorname.getTagName());
                                if (vorname.getTagName() == "titel"){  // get content from
                                    //System.out.println("titel ==== " + vorname.getTextContent());
                                    Titel_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "vorname"){  // get content from
                                    //System.out.println("vorname ==== " + vorname.getTextContent());
                                    Vornamen_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "namenszusatz"){  // get content from
                                    //System.out.println("namenszusatz ==== " + vorname.getTextContent());
                                    Namenszusatz_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "nachname"){
                                    //System.out.println("nachname ==== " + vorname.getTextContent());
                                    Nachnamen_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "fraktion"){
                                    //System.out.println("Fraktion = " + vorname.getTextContent());
                                    Fraktion_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "rolle"){
                                    //System.out.println("Rolle = " + vorname.getTextContent());
                                }
                            }
                        }
                        // Checks if a Speaker already has an object in the Redner Class.
                        int  counter = 0;
                        // System.out.println("wanted: " + Vornamen_Liste.get(j));
                        for (int z = 0; z < Redner_list.size(); z++) {
                            if (Redner_list.get(z).Check_for_duplicate(Vornamen_Liste.get(j), Nachnamen_Liste.get(j))) {
                            }
                            else {
                                counter = 1;
                            }
                        }
                        // Creats an new instance of the Redner Class provided that it is not already an object of Redner.
                        if (counter < 1) {
                            Redner redner = new Redner(Titel_Liste.get(j), Vornamen_Liste.get(j), Namenszusatz_Liste.get(j), Nachnamen_Liste.get(j), Fraktion_Liste.get(j));
                            Redner_list.add(redner);
                        }
                    }
                }
            }
        }
        //System.out.println(Redner_list);
        //System.out.println(Titel_Liste);
        //System.out.println(Vornamen_Liste);
        //System.out.println(Nachnamen_Liste.size());
        //System.out.println(Fraktion_Liste.size());

    }
    // Print out all Speakers.
    public void print_Redner() {
        System.out.println("----------------------------Auflistung aller Redner"+ "(" + Redner_list.size() + "):" +"----------------------------");
        for (int i = 0; i < Redner_list.size(); i++) {
            Redner_list.get(i).print();
        }
    }
    // Filters the names of every Speaker for a certain substring.
    public void filter_Redner(String substring) {
        for (int i = 0; i < Redner_list.size(); i++) {
            Redner_list.get(i).Filter(substring);
        }
    }
}
