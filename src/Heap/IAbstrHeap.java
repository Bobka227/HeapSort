/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Heap;

import AbstrTable.eTypProhl;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author 38067
 */
public interface IAbstrHeap<D> {
    
    boolean jePrazdny();
    void Vybuduj(D[] data);
    
    void reorganizce();
    
    void zrus();
    
    void vloz(D data);
    
    D odeberMax();
    
    D zpistupniMax();
    
    Iterator vypis(eTypProhl typ);
}
