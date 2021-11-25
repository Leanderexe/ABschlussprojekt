package Plenarsitzung;

import java.util.ArrayList;
import java.util.List;

public class Redner {
    String Fraktion;
    String Nachname;
    String Vorname;
    String Titel;
    String Namenszusatz;
    String ID;



    public Redner(Object Ti, Object Vor, Object NZ, Object Nach, Object Frak,  Object id) {
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
        ID = id.toString();
        //System.out.println(ID);
        if (Frak.toString().equals("0")) {
            Fraktion = "fraktionslos";
        }
        else {
            Fraktion = Frak.toString();
        }
    }

    public void print(){
        if (Titel == null) {
            if (Namenszusatz == null){
            System.out.println(Vorname + " " + Nachname + " " + Fraktion);
            }
            else{
                System.out.println(Vorname + " " + Namenszusatz+ " " + Nachname + " " + Fraktion);
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


    // Looks if the intput string is a substring of any part of a name form a Speaker.
    public void Filter(String substring){
        if (Vorname.contains(substring)){
            print();
        }
        else if (Nachname.contains(substring)){
            print();
        }
        else if (Namenszusatz != null){
            if (Namenszusatz.contains(substring)) {
                print();
            }
        }
        else if (Titel != null) {
            if (Titel.contains(substring)) {
                print();
            }
        }
    }


    public void Sort_For_Fraction(String Frak){
        // System.out.println(Fraktion + "     "+ Frak);
        if (Fraktion.equals(Frak)){
            print_for_Frak();
        }
    }

    public void print_for_Frak(){
        if (Titel == null) {
            if (Namenszusatz == null){
                System.out.println(Vorname + " " + Nachname);
            }
            else{
                System.out.println(Vorname + " " + Namenszusatz+ " " + Nachname);
            }
        }
        else {
            if (Namenszusatz == null){
                System.out.println(Titel + " " + Vorname + " " + Nachname);
            }
            else {

                System.out.println(Titel + " " + Vorname + " " + Namenszusatz + " " + Nachname);
            }
        }
    }
}
