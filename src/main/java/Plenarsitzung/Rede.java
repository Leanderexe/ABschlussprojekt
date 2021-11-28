package Plenarsitzung;

import Plenarsitzung.Impl.Rede_File_Impl;

import java.util.Arrays;
import java.util.List;

public class Rede implements Rede_File_Impl {
    String Sitzungsindex;
    String Nummernindex;
    String Datum;
    String rede_id;
    List Kommentare;
    String Titel;
    List Speech;
    String Redner_id;
    String Vorname;

    public Rede(String Sitz, String Nummer, String rede, List Kommentar, String Date, String Tit, List Spee, String Speaker_id, String Vname){
        Nummernindex = Nummer.replaceAll("\\D+",""); // Source: https://stackoverflow.com/questions/4030928/extract-digits-from-a-string-in-java/4030936
        Sitzungsindex = Sitz;
        Kommentare = Kommentar;
        rede_id = rede;
        Datum  = Date;
        Titel = Tit;
        Speech = Spee;
        Redner_id = Speaker_id;
        Vorname = Vname;
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

    public int average_speech_length(){
        int counter = 0;
        for (int i = 0; i < Speech.size(); i++){
            List length = Arrays.asList(Speech.get(i).toString().split("\\s+"));
            counter += length.size();
        }
        return counter;
    }

    public String get_id(){
        return Redner_id;
    }

    public String get_name() {
        return Vorname;
    }


}
