/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AgendaHeap;

import AbstrTable.AbstrTable;
import AbstrTable.eTypProhl;
import Heap.AbstrHeap;
import Obec.Obec;
import Obec.enumKraje;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 38067
 */
public class AgendaHeap implements IAgendaHeap {

    private AbstrHeap<Obec> heap = new AbstrHeap<>(10);

    private Random ran = new Random();

    @Override
    public void vloz(Obec obec) {
        heap.vloz(obec);
    }

    @Override
    public Obec odeberMax() {
        return heap.odeberMax();
    }

    @Override
    public void Vybuduj(Obec[] arr) {
        heap.Vybuduj(arr);
    }

    @Override
    public Iterator<Obec> VytvorIterator(eTypProhl typ) {
        return heap.vypis(typ);
    }

    @Override
    public Obec zpristupniMax() {
        return heap.zpistupniMax();
    }

    @Override
    public void reorganizace() {
        heap.reorganizce();
    }

    @Override
    public Obec Generuj() {
        String name = generateRandomString(5);
        int psc = ran.nextInt(99999);
        int pocetMuzu = ran.nextInt(1000);
        int pocetZen = ran.nextInt(1000);
        enumKraje kraj = enumKraje.values()[ran.nextInt(enumKraje.values().length)];
        return new Obec(name, kraj, psc, pocetMuzu, pocetZen);
    }

    private String generateRandomString(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(ran.nextInt(letters.length())));
        }
        return sb.toString();
    }

    public int importData(String soubor) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 7) {
                    System.out.println("neni spravny line: " + line);
                    continue;
                }

                try {
                    int krajIndex = Integer.parseInt(parts[0]) - 1;
                    enumKraje kraj = enumKraje.values()[krajIndex];

                    String obecName = parts[3].trim();
                    int PSC = Integer.parseInt(parts[2].trim());
                    int pocetMuzu = Integer.parseInt(parts[4].trim());
                    int pocetZen = Integer.parseInt(parts[5].trim());

                    Obec novaObec = new Obec(obecName, kraj, PSC, pocetMuzu, pocetZen);

                    heap.vloz(novaObec);
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Chyba: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Нneni spravny index: " + parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void zmenKriteriumNaNazev() {
        Obec.setZMENU(Obec.ZMENA.NAZEV);
        reorganizace();
    }

    public void zmenKriteriumNaObyvatel() {
        Obec.setZMENU(Obec.ZMENA.CELKEM);
        reorganizace();
    }

    public Obec[] getAllObce() {
        List<Obec> list = new ArrayList<>();
        Iterator<Obec> itr = VytvorIterator(eTypProhl.SIRKA);
        while (itr.hasNext()) {
            list.add(itr.next());
        }
        return list.toArray(new Obec[0]);
    }

    public void VybudujZeStavajicichDat() {
        Obec[] vsechnyObce = getAllObce();
        zrus();
        Vybuduj(vsechnyObce);
    }

    @Override
    public void zrus() {
        heap.zrus();
    }

}
