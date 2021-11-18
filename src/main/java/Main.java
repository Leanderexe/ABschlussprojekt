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
        Scanner Xml = new Scanner(System.in);
        System.out.println("Enter filepath:");
        String file = Xml.nextLine();
        Protokoll prot = new Protokoll();
        prot.Open_Zip(file);
        //prot.Read_File(file);


        Scanner User_Input = new Scanner(System.in);
        List valid_input = new ArrayList();
        for (int i = 1; i < 4; i++){
            valid_input.add(i);
        }
        boolean Valid = true;
        // Ask User for a valid Input.
        while (Valid) {
            try {
                System.out.println("Geben Sie die Nummer der Aufgabe ein (1-3):" + '\n' +
                        "[1]: Auflistung aller Redner" + '\n' +
                        "[2]: Auflistung aller Redner" + '\n' +
                        "[3]: Auflistung aller Redner");
                String Input = User_Input.nextLine();
                // Checks if the Input is a valid number.
                if(valid_input.contains(Integer.parseInt(Input))){
                    Valid = false;
                }
                else{
                    System.out.println(">>>>>>>>>>Das hat nicht geklappt! Bitte geben Sie eine Nummer zwischen 1 und 3 ein.<<<<<<<<<<" + '\n');
                }

                if (Input.equals("1")) {
                    prot.print_Redner();
                }
            } catch (Exception e) {
                System.out.println(">>>>>>>>>>Das hat nicht geklappt! Bitte geben Sie eine Nummer zwischen 1 und 3 ein.<<<<<<<<<<" + '\n');
            }
        }

    }
}
