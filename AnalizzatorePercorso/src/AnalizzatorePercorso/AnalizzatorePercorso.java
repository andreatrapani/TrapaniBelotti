/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package AnalizzatorePercorso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AnalizzatorePercorso {

    private ArrayList<Posizione> posizioni;

    public AnalizzatorePercorso() {
        posizioni = new ArrayList<Posizione>();
    }
    
    public static void main(String[] args) {
        try {
            AnalizzatorePercorso analizzatore = new AnalizzatorePercorso();
            analizzatore.caricaDatiDaFileXML("percorso.xml");
            System.out.println("Latitudine massima: " + analizzatore.getLatitudineMassima());
            System.out.println("Latitudine minima: " + analizzatore.getLatitudineMinima());
            System.out.println("Longitudine massima: " + analizzatore.getLongitudineMassima());
            System.out.println("Longitudine minima: " + analizzatore.getLongitudineMinima());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caricaDatiDaFileXML(String nomeFile) throws ParserConfigurationException, SAXException, IOException, ParseException {
        File fileXML = new File(nomeFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fileXML);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("posizione");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                double latitudine = Double.parseDouble(eElement.getElementsByTagName("latitudine").item(0).getTextContent());
                double longitudine = Double.parseDouble(eElement.getElementsByTagName("longitudine").item(0).getTextContent());
                double altitudine = Double.parseDouble(eElement.getElementsByTagName("altitudine").item(0).getTextContent());
                Date dataOra = sdf.parse(eElement.getElementsByTagName("dataOra").item(0).getTextContent());
                Posizione posizione = new Posizione(latitudine, longitudine, altitudine, dataOra);
                posizioni.add(posizione);
            }
        }
    }

    public double getLatitudineMassima() {
        double latitudineMassima = Double.MIN_VALUE;
        for (Posizione posizione : posizioni) {
            if (posizione.getLatitudine() > latitudineMassima) {
                latitudineMassima = posizione.getLatitudine();
            }
        }
        return latitudineMassima;
    }

    public double getLatitudineMinima() {
        double latitudineMinima = Double.MAX_VALUE;
        for (Posizione posizione : posizioni) {
            if (posizione.getLatitudine() < latitudineMinima) {
                latitudineMinima = posizione.getLatitudine();
            }
        }
        return latitudineMinima;
    }

    public double getLongitudineMassima() {
        double longitudineMassima = Double.MIN_VALUE;
        for (Posizione posizione : posizioni) {
            if (posizione.getLongitudine() > longitudineMassima) {
                longitudineMassima = posizione.getLongitudine();
            }
        }
        return longitudineMassima;
    }

    public double getLongitudineMinima() {
        double longitudineMinima = Double.MAX_VALUE;
        for (Posizione posizione : posizioni) {
            if (posizione.getLongitudine() < longitudineMinima) {
                longitudineMinima = posizione.getLongitudine();
            }
        }
        return longitudineMinima;
    }
}
