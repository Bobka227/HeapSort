/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obec;

/**
 *
 * @author 38067
 */
public class Obec implements Comparable<Obec>{
    
    public enum ZMENA { NAZEV, CELKEM } 
    private static ZMENA zmena = ZMENA.CELKEM; 
    
    private String nazevObce;
    private int PSC;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;
    private enumKraje kraj;

    public Obec(String nazevObce, enumKraje kraj, int PSC, int pocetMuzu, int pocetZen) {
        this.kraj = kraj;
        this.nazevObce = nazevObce;
        this.PSC = PSC;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = pocetMuzu + pocetZen;
    }

    public Obec(String nazevObce) {
        this.nazevObce = nazevObce;
    }
    
    public static void setZMENU(ZMENA novapriorita) {
        zmena = novapriorita;
    }
    
    

    public int getPSC() {
        return PSC;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    public String getNazev() {
        return nazevObce;
    }

    public enumKraje getKraj() {
        return kraj;
    }
    
    @Override
     public int compareTo(Obec o) {
        switch(zmena) {
            case NAZEV:
                return this.nazevObce.compareTo(o.nazevObce);
            case CELKEM:
            default:
                int thisPop = this.pocetMuzu + this.pocetZen;
                int oPop = o.pocetMuzu + o.pocetZen;
                return Integer.compare(thisPop, oPop);
        }
    }

    @Override
    public String toString() {
        return "Obec: " + nazevObce + "; Kraj: " + kraj  + "; PSC- " + PSC + "; pocetMuzu- " + pocetMuzu + "; pocetZen- " + pocetZen + "; celkem- " + celkem + '.';
    }

   
}
