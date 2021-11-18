package Plenarsitzung;

import java.util.ArrayList;
import java.util.List;

public class Redner {
    String Fraktion;
    String Nachname;
    String Vorname;
    String Titel;
    String Namenszusatz;



    public Redner(Object Ti, Object Vor, Object NZ, Object Nach, Object Frak) {
        if (Ti.toString().equals("0")) {
        }
        else{
            Titel = Ti.toString();
        }
        if (NZ.toString().equals("0")) {
        }
        else {
            Namenszusatz = NZ.toString();
        }
        Vorname  = Vor.toString();
        Nachname = Nach.toString();
        if (Frak.toString().equals("0")) {
            Fraktion = "(Fraktionslos)";
        }
        else {
            Fraktion = Frak.toString();
        }
    }

    public void print(){
        if (Titel == null) {
            if (Namenszusatz == null){
            System.out.println( Vorname + " " + Nachname + " " + Fraktion);
            }
            else{
                System.out.println( Vorname + " " + Namenszusatz+ " " + Nachname + " " + Fraktion);
            }
        }
        else {
            if (Namenszusatz == null){
                System.out.println(Titel + " " + Vorname + " " + Nachname + " " + Fraktion);
            }
            else {
                System.out.println(Titel + " " + Vorname + " " + Namenszusatz + " " + Nachname + " " + Fraktion);
            }
        }
    }
    public Boolean Check_for_duplicate(Object Vor, Object Nach){
        if (Vorname.equals(Vor) && Nachname.equals(Nach)){
            // System.out.println(Vorname + " " + Namenszusatz + " " + Nachname);
            return false;
        }
        return true;
    }
}
