package Plenarsitzung;

import java.util.ArrayList;
import java.util.List;

public class Tagesordnungspunkt {
    String top;
    String Sitzungsindex;
    String Nummernindex;
    List<String> Inhalt = new ArrayList();
    List<String> ID_Redner = new ArrayList();

    public Tagesordnungspunkt(String topunkt, String Sindex, List Text){
        top = topunkt;
        Nummernindex = topunkt.replaceAll("\\D+",""); // Source: https://stackoverflow.com/questions/4030928/extract-digits-from-a-string-in-java/4030936
        Sitzungsindex = Sindex;
        Inhalt = Text;

    }

    public int print_top(String Nummer, String Sitzung){
        int Found = 0;
        StringBuilder tp = new StringBuilder();
        tp.append("Tagesordnungspunkt ");
        tp.append(Nummer);
        if (Sitzung.equals(Sitzungsindex)){
            if (tp.toString().equals(top)){
                Found += 1;
                System.out.println(">>>>>>>>>>>>>Ausgabe des Textes der Sitzung Nr." +  Sitzungsindex + " " + top + "<<<<<<<<<<<<<<<<<<<<" + '\n');
                for (int j = 0; j < Inhalt.size(); j++){
                    System.out.println(Inhalt.get(j));
                }
            }
        }
        return Found;
    }
}
