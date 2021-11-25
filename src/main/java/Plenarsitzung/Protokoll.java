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
    List Fraktionen = new ArrayList();
    ArrayList<Tagesordnungspunkt> top_list = new ArrayList();

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
        List ID_Liste = new ArrayList();

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
            ID_Liste.add("0");


            Node person = (redner_list.item(j));
            if (person.getNodeType()==Node.ELEMENT_NODE){
                Element per = (Element) person;
                String ID = per.getAttribute("id");
                // System.out.println(ID);
                ID_Liste.set(j, ID);
                //System.out.println(doc.getElementsByTagName("redner").item(j).getTextContent());
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
                                //System.out.println(doc.getElementsByTagName("vorname").item(j).getTextContent());
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
                        // Creats a new instance of the Redner Class provided that it is not already an object of Redner.
                        if (counter < 1) {
                            Redner redner = new Redner(Titel_Liste.get(j), Vornamen_Liste.get(j), Namenszusatz_Liste.get(j), Nachnamen_Liste.get(j), Fraktion_Liste.get(j), ID_Liste.get(j));
                            Redner_list.add(redner);

                            // Add all fraction into a list, so we can use it to iterate over all Fractions.
                            if (Fraktionen.contains(Fraktion_Liste.get(j))){
                            }
                            else {
                                Fraktionen.add(Fraktion_Liste.get(j));
                            }
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


        NodeList pl_nr = doc.getElementsByTagName("sitzungsnr");
        String Sitzungsindex = pl_nr.item(0).getTextContent();  // Sitzungsnummer
        NodeList tagesOP = doc.getElementsByTagName("tagesordnungspunkt");
        for (int j = 0;j < tagesOP.getLength(); j++) {

            List Kommentare_Liste = new ArrayList();
            List id_Liste = new ArrayList();
            List Inhalt_Liste = new ArrayList();
            //System.out.println(tagesOP.getTextContent());
            Node Node_OP = (tagesOP.item(j));
            //System.out.println(Node_OP.getTextContent());
            if (Node_OP.getNodeType() == Node.ELEMENT_NODE) {
                Element top = (Element) Node_OP;
                String top_id = top.getAttribute("top-id");  // Gibt Tagesordnungspunkt aus.
                NodeList child_list = top.getChildNodes();
                for (int t = 0; t < child_list.getLength(); t++){
                Node child = child_list.item(t);
                //System.out.println(child.getTextContent());
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    Element Rede = (Element) child;
                    if (Rede.getTagName() == "p"){
                        //System.out.println(Rede.getTextContent());
                        Inhalt_Liste.add(Rede.getTextContent());
                    }
                    else if (Rede.getTagName() == "kommentar") {
                        //System.out.println(Rede.getTextContent());
                        Inhalt_Liste.add(Rede.getTextContent());
                    }
                    if (Rede.getTagName() == "rede") {
                        //System.out.println(Rede.getAttribute("id"));
                        String rede_id = Rede.getAttribute("id");
                        //Element p = (Element) Rede.getElementsByTagName("p");
                        //System.out.println(p.getTextContent());
                        NodeList rede_child_list = Rede.getChildNodes();
                        //System.out.println(rede_child_list.getLength());
                        for (int z = 0; z < rede_child_list.getLength(); z++){
                            Node text_node = rede_child_list.item(z);
                            // System.out.println(text_node.getTextContent());
                            //System.out.println(text_node.getTextContent());
                            if (text_node.getNodeType() == Node.ELEMENT_NODE) {
                                Element text = (Element) text_node;
                                if (text.getTagName() == "p"){
                                    // System.out.println(text.getTextContent());
                                    if (text.getAttribute("klasse").equals("redner")) {
                                        NodeList redner_node = text.getChildNodes();
                                        for (int k = 0; k < redner_node.getLength(); k++) {
                                            Node r_node = redner_node.item(k);
                                            if (r_node.getNodeType() == Node.ELEMENT_NODE) {
                                                Element redner = (Element) r_node;
                                                if (redner.getTagName() == "redner") {
                                                    String redner_id = redner.getAttribute("id"); // Redner_id
                                                    id_Liste.add(redner_id);
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        //System.out.println(text.getTextContent());
                                        Inhalt_Liste.add(text.getTextContent());
                                    }
                                }
                                else if (text.getTagName() == "kommentar"){
                                    //System.out.println("Das ist ein Kommentar:"  + text.getTextContent());
                                    Kommentare_Liste.add(text.getTextContent());
                                    Inhalt_Liste.add(text.getTextContent());
                                }
                                //System.out.println(text.getTextContent());
                            }
                        }
                        }

                    }
                }
                Tagesordnungspunkt top_obj = new Tagesordnungspunkt(top_id, Sitzungsindex, Inhalt_Liste);
                top_list.add(top_obj);
            }
        }

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
    // List all Speaker after the party they are part of.
    public void sort_Fraction(){
        for (int f = 0; f < Fraktionen.size(); f++) {
            if (Fraktionen.get(f).equals("0")) {
            }
            else {
                System.out.println('\n' + ">>>>>>>>>>>>>>Auflistung aller " + Fraktionen.get(f) + " Redner:<<<<<<<<<<<<");
                for (int i = 0; i < Redner_list.size(); i++) {
                    Redner_list.get(i).Sort_For_Fraction((String) Fraktionen.get(f));
                }
            }
        }
    }
    //
    public void find_top(String Nummernidex, String Sitzungsindex) {
        int found = 0;
        for (int i = 0; i < top_list.size(); i++) {
            found += top_list.get(i).print_top(Nummernidex, Sitzungsindex);
        }
        if (found < 1){
            System.out.println("-------------Es konnte kein Tagesordnungspunkt gefunden werden--------------");
        }
    }

}
