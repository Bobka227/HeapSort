package AgendaHeap;

import AbstrTable.eTypProhl;
import Obec.Obec;
import java.util.Iterator;

/**
 *
 * @author 38067
 */
public interface IAgendaHeap {
  
     void vloz(Obec obec);
    Obec odeberMax();
    void Vybuduj(Obec[] arr);
    Iterator<Obec> VytvorIterator(eTypProhl typ);
    Obec zpristupniMax();
    void reorganizace();
    Obec Generuj();
    void zrus();
}
