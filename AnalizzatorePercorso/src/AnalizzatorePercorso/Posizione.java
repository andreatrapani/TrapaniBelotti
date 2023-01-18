/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AnalizzatorePercorso;

import java.util.Date;

/**
 *
 * @author AndreaDomenicoTrapan
 */
public class Posizione {
    private double latitudine;
    private double longitudine;
    private double altitudine;
    private Date dataOra;

    public Posizione(double latitudine, double longitudine, double altitudine, Date dataOra) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.altitudine = altitudine;
        this.dataOra = dataOra;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getAltitudine() {
        return altitudine;
    }

    public Date getDataOra() {
        return dataOra;
    }
}

