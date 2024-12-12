package Lists;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private class Element implements Serializable {

        Element prev;
        Element next;
        T data;

        public Element(Element prev, Element next, T data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }

        public Element(T data) {
            this.data = data;
        }
    }

    private Element actualEl;
    private Element firstEl;
    private Element lastEl;
    private int rozmer;

    public AbstrDoubleList() {
        this.actualEl = null;
        this.firstEl = null;
        this.lastEl = null;
        this.rozmer = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Element aktualniIterator = null;

            @Override
            public boolean hasNext() {
                if (aktualniIterator == null) {
                    return firstEl != null;
                }
                return aktualniIterator.next != firstEl;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    if (aktualniIterator == null) {
                        aktualniIterator = firstEl;
                    } else {
                        aktualniIterator = aktualniIterator.next;
                    }
                    return aktualniIterator.data;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public void zrus() {
        firstEl = null;
        actualEl = null;
        lastEl = null;
        rozmer = 0;
    }

    @Override
    public boolean jePrazdny() {
        return rozmer == 0 && firstEl == null;
    }

    @Override
    public void vlozPrvni(T data) {
        Element el = new Element(data);
        if (jePrazdny()) {
            firstEl = lastEl = actualEl = el;

            firstEl.next = firstEl.prev = firstEl; 
        } else {
            el.next = firstEl;
            el.prev = lastEl;
            firstEl.prev = el;
            lastEl.next = el;
            firstEl = actualEl = el; 
        }
        rozmer++;
    }

    @Override
    public void vlozPosledni(T data) {
        Element el = new Element(data);
        if (jePrazdny()) {
            el.next = el.prev = el;
            firstEl = lastEl = actualEl = el;
        }
        if (lastEl == null) {
            firstEl = el;
            lastEl= actualEl = el;
        } else {
            lastEl.next = el;
            el.prev = lastEl;
            lastEl = actualEl= el;
        }
        rozmer++;

    }

    @Override
    public void vlozNaslednika(T data) {
        if (actualEl == lastEl) {
            vlozPosledni(data);
        } else {
            Element el = new Element(data);
            el.next = actualEl.next;
            actualEl.next.prev = el;
            actualEl.next = el;
            el.prev = actualEl;
            rozmer++;
        }
    }

    @Override
    public void vlozPredchudce(T data) {
        if (actualEl == firstEl) {
            vlozPrvni(data);
        } else {
            Element el = new Element(data);
            el.prev = actualEl.prev;
            actualEl.prev.next = el;
            actualEl.prev = el;
            el.next = actualEl;
            rozmer++;
        }
    }

    @Override
    public T zpristuniAktualni() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        return actualEl.data;
    }

    @Override
    public T zpristupniPrvni() {
        if (firstEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = firstEl;
        return firstEl.data;
    }

    @Override
    public T zpristuniPosledni() {
        if (lastEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = lastEl;
        return lastEl.data;
    }

    @Override
    public T zpristupniNaslednika() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = actualEl.next;
        return actualEl.data;
    }

    @Override
    public T zpristuniPredchudce() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        actualEl = actualEl.prev;
        return actualEl.data;
    }

    @Override
    public T odeberAktualni() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        Element actualData = actualEl;
        if (actualEl == firstEl && actualEl == lastEl) {
            zrus();
            return actualData.data;

        } else if (actualEl == firstEl) {
            odeberPrvni();
        } else if (actualEl == lastEl) {
            odeberPosledni();
        } else {
            actualEl.prev.next = actualEl.next;
            actualEl.next.prev = actualEl.prev;
            rozmer--;
        }
        return actualData.data;
    }

    @Override
    public T odeberPrvni() {
        if (jePrazdny()) {
        throw new NoSuchElementException("Aktuální prvek neexistuje.");
    }
        Element firstData = firstEl;
        if (firstEl != null) {
            if (firstEl == lastEl) {
                zrus();
            } else {
                firstEl = firstEl.next;
                firstEl.prev = lastEl;
                lastEl.next = firstEl.next;
                actualEl = firstEl;
                 rozmer--;
            }
           
            
        }
        return firstData.data;
    }

    @Override
    public T odeberPosledni() {
        if (jePrazdny()) {
            throw new NoSuchElementException("List is empty, cannot remove the element");
        }

        T lastData = lastEl.data;

        if (firstEl == lastEl) {
            zrus();
        } else {
            actualEl = lastEl = lastEl.prev;
            lastEl.next = firstEl;
            firstEl.prev = lastEl;
        }
        return lastData;

    }

    @Override
    public T odeberNaslednika() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        if (actualEl.next == firstEl) {
            return odeberPrvni();
        }
        Element childData = actualEl.next;
        actualEl.next = childData.next;
        childData.next.prev = actualEl;
        rozmer--;
        return childData.data;
    }

    @Override
    public T odeberPredchudce() {
        if (actualEl == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        if (actualEl.prev == null) {
            throw new NoSuchElementException("Aktuální prvek neexistuje.");
        }
        Element oldData = actualEl.prev;
        actualEl.prev = oldData.prev;
        oldData.prev.next = actualEl;
        rozmer--;
        return oldData.data;

    }

    public int rozmer() {
        return rozmer;
    }
}
