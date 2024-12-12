/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package AbstrTable;

import java.util.Iterator;

/**
 *
 * @author 38067
 */
public interface IAbstrTable<K ,V> {
    public void zrus();
    
     boolean jePrazdny();
    
     V najdi(K key);
    
     void vloz(K key, V value);
    
     V odeber(K key);
     
     Iterator vytvorIterator(eTypProhl typ);
    
    
}
