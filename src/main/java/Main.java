import Plenarsitzung.Protokoll;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException, ParserConfigurationException, SAXException {

        boolean Readable = true;
        Protokoll prot = new Protokoll();
        while (Readable){
            Scanner Xml = new Scanner(System.in);
            System.out.println("Enter filepath:");
            String file = Xml.nextLine();
            boolean read = prot.Open_Zip(file);
            if (read){
                Readable = false;
            }
        }

        Scanner User_Input = new Scanner(System.in);
        List valid_input = new ArrayList();
        for (int i = 0; i < 6; i++){
            valid_input.add(i);
        }
        boolean Valid = true;
        // Ask User for a valid Input.
        while (Valid) {
            try {

                System.out.println('\n' + "Geben Sie die Nummer der Aufgabe ein (1-5):" + '\n' +
                        "[1]: Auflistung aller Redner" + '\n' +
                        "[2]: Nach dem Namen bestimmter Redner Filtern" + '\n' +
                        "[3]: Auflistung aller Redner nach Partei/Fraktion" + '\n' +
                        "[4]: Ausgabe des Textes eines bestimmten Tagesordnungspunktes" + '\n' +
                        "[5]: Ausgabe der Top 5 Tagesordnungspunkte mit den meisten Zwischenrufen" + '\n' +
                        "[0]: Exit");

                String Input = User_Input.nextLine();
                // Checks if the Input is a valid number.
                if (valid_input.contains(Integer.parseInt(Input))) {
                } else {
                    System.out.println(">>>>>>>>>>Das hat nicht geklappt! Bitte geben Sie eine Nummer zwischen 1 und 4 ein.<<<<<<<<<<" + '\n');
                }

                if (Input.equals("1")) {
                    prot.print_Redner();
                    System.out.println("--------------------------------------------------------------------------");
                }
                else if (Input.equals("2")) {
                    Scanner filter = new Scanner(System.in);
                    System.out.println("Geben Sie einen Suchbegriff ein, um nach einen/mehreren bestimmten Rednern zu suchen (auf Case sensitivity achten):");
                    String substring = filter.nextLine();
                    System.out.println("--------------------------------gefundene Redner:-----------------------------");
                    prot.filter_Redner(substring);
                    System.out.println("------------------------------------------------------------------------------");
                }
                else if (Input.equals("3")) {
                    prot.sort_Fraction();
                    System.out.println("--------------------------------------------------------------------------");
                }
                else if (Input.equals("4")) {
                    Scanner Sitz = new Scanner(System.in);
                    System.out.println("Geben Sie einen Sitzung-Index ein:(1-239)");
                    String Sitzung = Sitz.nextLine();
                    Scanner Num = new Scanner(System.in);
                    System.out.println("Geben Sie einen Nummern-Index ein:");
                    String Nummer = Num.nextLine();
                    prot.find_top(Nummer, Sitzung);
                }

                else if (Input.equals("5")) {
                    System.out.println("---------------------- Die Top 5 Reden mit den meisten Zwischenrufen ---------------------");
                    prot.top_five();
                    System.out.println("------------------------------------------------------------------------------------------");
                }

                else if (Input.equals("0")){
                    System.out.println("Das Programm wurde erfolgreich beendet.");
                    Valid = false;
                }
            } catch (Exception e) {
                System.out.println(">>>>>>>>>>Das hat nicht geklappt! Bitte geben Sie eine Nummer zwischen 1 und 5 ein.<<<<<<<<<<" + '\n');
            }
        }
    }
}
