import Plenarsitzung.Protokoll;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException, ParserConfigurationException, SAXException {
        Scanner Xml = new Scanner(System.in);
        System.out.println("Enter filepath:");
        String file = Xml.nextLine();
        System.out.println(file);
        Protokoll prot = new Protokoll();
        prot.Read_File(file);
    }
}
