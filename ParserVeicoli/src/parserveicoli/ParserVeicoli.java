/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parserveicoli;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserVeicoli {

    private double threshold;
    private String xmlFile;
    
    public ParserVeicoli(String xmlFile, double threshold) {
        this.xmlFile = xmlFile;
        this.threshold = threshold;
    }

    public void parse() {
        try {
            // Apertura e parsing del file XML
            File file = new File(xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            // Creazione della lista dei veicoli
            NodeList nList = doc.getElementsByTagName("veicolo");
            for (int i = 0; i < nList.getLength(); i++) {
                Element veicolo = (Element) nList.item(i);
                String id = veicolo.getElementsByTagName("ID").item(0).getTextContent();
                boolean isBelowThreshold = true;
                List<Long> timestamps = new ArrayList<Long>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                
                // Creazione della lista delle misure
                NodeList misureList = veicolo.getElementsByTagName("misura");
                for (int j = 0; j < misureList.getLength(); j++) {
                    Element misura = (Element) misureList.item(j);
                    double temp = Double.parseDouble(misura.getElementsByTagName("temperatura").item(0).getTextContent());
                    Date data_ora = dateFormat.parse(misura.getElementsByTagName("data_ora").item(0).getTextContent());
                    if (temp > threshold) {
                        isBelowThreshold = false;
                        timestamps.add(data_ora.getTime() / 1000);
                    }
                }
                System.out.println("Veicolo " + id + ": Tutti i valori misurati sono inferiori al valore di soglia? " + isBelowThreshold);
                if (!isBelowThreshold) {
                    System.out.println("Timestamp in cui la temperatura è superiore al valore di soglia:");
                    for (Long timestamp : timestamps) {
                        System.out.println(timestamp);
                    }
                }
            }
                    } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String xmlFile = "veicoli.xml";
        double threshold = -30.0;
        ParserVeicoli parser = new ParserVeicoli(xmlFile, threshold);
        parser.parse();
    }
}

