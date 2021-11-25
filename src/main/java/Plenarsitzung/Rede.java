package Plenarsitzung;

import java.util.List;

public class Rede {
    String Sitzungsindex;
    String Nummernindex;
    String Datum;
    String rede_id;
    List Kommentare;
    String Titel;

    public Rede(String Sitz, String Nummer, String rede, List Kommentar, String Date, String Tit){
        Nummernindex = Nummer.replaceAll("\\D+",""); // Source: https://stackoverflow.com/questions/4030928/extract-digits-from-a-string-in-java/4030936
        Sitzungsindex = Sitz;
        Kommentare = Kommentar;
        rede_id = rede;
        Datum  = Date;
        Titel = Tit;
    }

    public int find_most_stands(){
        int counter = 0;
        for (int i = 0; i < Kommentare.size(); i++){
            String Kom = (String) Kommentare.get(i);
            if (Kom.contains("[")) {
                counter += 1;
        }
        }
        return counter;
    }

    public void print_top_five(int Platz, int stands){
        System.out.println("Platz " + (Platz+1) + ": Die Sitzung Nr. " + Sitzungsindex + " am " + Datum + " mit " + stands + " Zwischenrufen Titel: " + Titel);
    }
}
