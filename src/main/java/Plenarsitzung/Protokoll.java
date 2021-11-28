package Plenarsitzung;

import Plenarsitzung.Beiträge.Rede;
import Plenarsitzung.Beiträge.Redner;
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
    int Legislaturperiode;
    String Tagesordnungspunkt;
    String Sitzungsleitender;
    ArrayList<Redner> Redner_list = new ArrayList();
    List Fraktionen = new ArrayList();
    ArrayList<Plenarsitzung.Tagesordnungspunkt> top_list = new ArrayList();
    ArrayList<Rede> Speaker_list = new ArrayList();
    List redner_id_list = new ArrayList(); // Holds every Speakers id.

    // Unzips the folder and provides the path for every xml file.
    public boolean Open_Zip(String zip) throws IOException {
        //xml_files = list
        try {
            ZipFile zipFile = new ZipFile(zip);
            Enumeration<? extends ZipEntry> xml = zipFile.entries();
            System.out.println("Die Parlamentsprotokolle werden eingelesen. Bitte haben Sie einen Moment Geduld.");

            while (xml.hasMoreElements()) {
                //String xml_path = zip.replace(".zip", "\\" + xml.nextElement());
                try{
                    String xml_path = zip.replace(".zip", "\\" + xml.nextElement());
                    if (xml_path.contains("xml")) {
                        Read_File(xml_path);
                    }
                }
                catch(Exception e){
                    System.out.println(">>>>>>Fehler: Die Datei konnte nicht eingelesen werden.<<<<<<<<" + '\n');
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println(">>>>>>Fehler: Die Datei konnte nicht eingelesen werden.<<<<<<<<" + '\n');
            return false;
        }

        return true;
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

            // Navigate through the xml Structure.
            Node person = (redner_list.item(j));
            if (person.getNodeType()==Node.ELEMENT_NODE) {
                Element per = (Element) person;
                String ID = per.getAttribute("id");
                ID_Liste.set(j, ID);
                NodeList child_list = per.getChildNodes();
                for (int n = 0; n < child_list.getLength(); n++) {
                    Node child = child_list.item(n);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        Element children = (Element) child;

                        NodeList name_list = children.getChildNodes();
                        for (int t = 0; t < name_list.getLength(); t++) {
                            Node name = name_list.item(t);
                            if (name.getNodeType() == Node.ELEMENT_NODE) {
                                Element vorname = (Element) name;
                                if (vorname.getTagName() == "titel") {  // get content from
                                    Titel_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "vorname") {  // get content from
                                    Vornamen_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "namenszusatz") {  // get content from
                                    Namenszusatz_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "nachname") {
                                    Nachnamen_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "fraktion") {
                                    Fraktion_Liste.set(j, vorname.getTextContent());
                                }
                                if (vorname.getTagName() == "rolle") {
                                }
                            }
                        }
                    }
                }
            }
                        // Checks if a Speaker already has an object in the Redner Class.
                        int  counter = 0;
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



        NodeList pl_nr = doc.getElementsByTagName("sitzungsnr");
        String Sitzungsindex = pl_nr.item(0).getTextContent();  // Sitzungsnummer
        NodeList tagesOP = doc.getElementsByTagName("tagesordnungspunkt");
        NodeList date = doc.getElementsByTagName("datum");
        String Datum = date.item(0).getTextContent();
        for (int j = 0;j < tagesOP.getLength(); j++) {

            List Kommentare_Liste = new ArrayList(); // Holds every comment made.
            List rede_id_list = new ArrayList();
            List Inhalt_Liste = new ArrayList();  // Holds every comment + every speech.
            StringBuilder Titel = new StringBuilder();

            Node Node_OP = (tagesOP.item(j));
            if (Node_OP.getNodeType() == Node.ELEMENT_NODE) {
                Element top = (Element) Node_OP;
                String top_id = top.getAttribute("top-id");  // Gibt Tagesordnungspunkt aus.
                NodeList child_list = top.getChildNodes();
                for (int t = 0; t < child_list.getLength(); t++){
                Node child = child_list.item(t);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    Element Rede = (Element) child;
                    if (Rede.getTagName() == "p"){
                        Inhalt_Liste.add(Rede.getTextContent());

                        if (Rede.getAttribute("klasse").equals("T_NaS")) {
                            Titel.append(" " + Rede.getTextContent());
                        }
                        if (Rede.getAttribute("klasse").equals("T_fett")){
                            Titel.append(" " + Rede.getTextContent());
                        }
                    }

                    else if (Rede.getTagName() == "kommentar") {
                        Inhalt_Liste.add(Rede.getTextContent());
                    }

                    if (Rede.getTagName() == "rede") {
                        String redner_id = "0";
                        String Vorname = null;
                        List Kommentare_pro_rede = new ArrayList();
                        List Speech_Liste = new ArrayList();  // Holds every speech.
                        String rede_id = Rede.getAttribute("id");
                        NodeList rede_child_list = Rede.getChildNodes();

                        for (int z = 0; z < rede_child_list.getLength(); z++){
                            Node text_node = rede_child_list.item(z);


                            if (text_node.getNodeType() == Node.ELEMENT_NODE) {
                                Element text = (Element) text_node;
                                if (text.getTagName() == "p"){
                                    if (text.getAttribute("klasse").equals("redner")) {
                                        NodeList redner_node = text.getChildNodes();
                                        for (int k = 0; k < redner_node.getLength(); k++) {
                                            Node r_node = redner_node.item(k);
                                            if (r_node.getNodeType() == Node.ELEMENT_NODE) {
                                                Element redner = (Element) r_node;
                                                if (redner.getTagName() == "redner") {
                                                    redner_id = redner.getAttribute("id"); // Redner_id
                                                    redner_id_list.add(redner_id);

                                                    NodeList child_node = redner.getChildNodes();
                                                    for (int n = 0; n < child_node.getLength(); n++) {
                                                        Node ch = child_node.item(n);
                                                        if (ch.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element ch_element = (Element) ch;

                                                            NodeList ch_node = ch_element.getChildNodes();
                                                            for (int u = 0; u < ch_node.getLength(); u++) {
                                                                Node redner_prop = ch_node.item(u);
                                                                if (redner_prop.getNodeType() == Node.ELEMENT_NODE) {
                                                                    Element redner_prop_elem = (Element) redner_prop;

                                                                    if (redner_prop_elem.getTagName() == "vorname") {  // get content from
                                                                        Vorname = redner_prop_elem.getTextContent();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        Inhalt_Liste.add(text.getTextContent());
                                        Speech_Liste.add(text.getTextContent());
                                    }
                                }
                                else if (text.getTagName() == "kommentar"){
                                    Kommentare_pro_rede.add(text.getTextContent());
                                    Kommentare_Liste.add(text.getTextContent());
                                    Inhalt_Liste.add(text.getTextContent());
                                }
                            }
                        }
                        Rede Speaker = new Rede(Sitzungsindex, top_id, rede_id, Kommentare_pro_rede, Datum, Titel.toString(), Speech_Liste, redner_id, Vorname);
                        Speaker_list.add(Speaker);
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
    public void top_five(){
        //  Initilize Array with values.
        List<Integer> best_five_num =  new ArrayList();
        List best_five_obj = new ArrayList();
        for (int i = 0; i < 5; i++){
            best_five_num.add(0);
            best_five_obj.add(0);
        }
        // find the objects with the most stands.
        for (int i = 0; i < Speaker_list.size(); i++) {
            int ret  = Speaker_list.get(i).find_most_stands();
            loop:
            for (int k = 0; k < best_five_num.size(); k++){
                if (ret > best_five_num.get(k)){
                    best_five_num.set(k, ret);
                    best_five_obj.set(k, i);
                    break loop;
                }
            }
        }
        // Print out these objects.
        for (int i = 0; i < best_five_obj.size(); i++){
            Speaker_list.get((Integer) best_five_obj.get(i)).print_top_five(i, best_five_num.get(i));
        }
    }

    public void count_Speech(){
        int counter = 0;
        for (int i = 0; i < Speaker_list.size(); i++){
            counter += Speaker_list.get(i).average_speech_length();
        }
        int average = counter/Speaker_list.size();
        System.out.println("Die Durchnittliche Redelänge aller Redner beträgt: " + average + " Wörter");
    }

    public void count_Speech_per_speaker(){
        List Redner = new ArrayList(); // Every Speakers id is saved uniquely.
        List count_word = new ArrayList();
        List count_word_redner = new ArrayList(); // Contains the number of words per Speaker.
        List count_entries = new ArrayList(); // Contains the number of times a speaker held a speech.
        for (int i = 0; i < Speaker_list.size(); i++){
            count_word.add(Speaker_list.get(i).average_speech_length());
        }

        for (int i = 0; i < Speaker_list.size(); i++){
            String Speaker_id = Speaker_list.get(i).get_id();
            if (Redner.contains(Speaker_id)){
                for (int k = 0; k < Redner.size(); k++){
                    if (Speaker_id.equals(Redner.get(k))){
                        int num = (Integer) count_word.get(i) + (Integer) count_word_redner.get(k);
                        count_word_redner.set(k, num);
                        count_entries.set(k, (Integer)count_entries.get(k) + 1);
                    }
                }
            }
            else{
                Redner.add(Speaker_id);
                count_word_redner.add(count_word.get(i));
                count_entries.add(1);
            }
        }

        for (int k = 0; k < Redner.size(); k++) {
            for (int i = 0; i < Redner_list.size(); i++) {
                if (Redner.get(k).equals(Redner_list.get(i).get_id())){
                    String Name  = Redner_list.get(i).get_name();
                    int average = (Integer) count_word_redner.get(k)/ (Integer) count_entries.get(k);
                    System.out.println(Name + " : " + average + " Wörter lang");
                }
            }
        }
    }
}
