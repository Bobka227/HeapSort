/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstrTable;

import Lists.AbstrDoubleList;
import Lists.IAbstrDoubleList;
import java.util.Iterator;

/**
 *
 * @author 38067
 */
public class AbstrFIFO<T> {
    IAbstrDoubleList<T> ADS = new AbstrDoubleList<>();
    
    public void zrus(){
        ADS.zrus();
    }
    
    public boolean jePrazdny(){
        return ADS.jePrazdny();
    }
    
    public void vloz(T data){
        ADS.vlozPosledni(data);
    }
    
    public T odeber(){
        return ADS.odeberPrvni();
    }
    
    public Iterator<T> VytvorIterator(){
        return ADS.iterator();
    }
    
}
