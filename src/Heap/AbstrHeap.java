package Heap;

import AbstrTable.AbstrFIFO;
import AbstrTable.AbstrLIFO;
import AbstrTable.eTypProhl;
import Heap.IAbstrHeap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrHeap<D extends Comparable<D>> implements IAbstrHeap<D> {

    public class ElementHeap<T> {

        T data;

        public ElementHeap(T data) {
            this.data = data;
        }
    }

    private ElementHeap<D>[] HeapPrvek;
    private int capacity;
    private int size;

    public AbstrHeap(int capacity) {
        this.HeapPrvek = new ElementHeap[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    private int getParent(int index) {
        return index / 2;
    }

    private int getLeftChild(int index) {
        return 2 * index;
    }

    private int getRightChild(int index) {
        return 2 * index + 1;
    }

    @Override
    public boolean jePrazdny() {
        return size == 0;
    }

    @Override
    public void Vybuduj(D[] data) {
        if (data.length + 1 > capacity) {
            capacity = data.length + 1;
            HeapPrvek = Arrays.copyOf(HeapPrvek, capacity);
        }
        
        size = data.length;

        for (int i = 0; i < data.length; i++) {
            HeapPrvek[i + 1] = new ElementHeap<>(data[i]);
        }

        reorganizce();
    }

    @Override
    public void reorganizce() {
        for (int i = size / 2; i >= 1; i--) {
            PosunDole(i);
        }
    }

    @Override
    public void zrus() {
        size = 0;
    }

    @Override
    public void vloz(D data) {
        if (size + 1 >= capacity) {
            resize();
        }
        size++;
        HeapPrvek[size] = new ElementHeap<>(data);
        PosunNahoru(size);
    }

    private void PosunNahoru(int index) {
        while (index > 1) {
            int parentIndex = getParent(index);
            if (HeapPrvek[index].data.compareTo(HeapPrvek[parentIndex].data) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else { 
                break;
            }
        }
    }

    private void swap(int i, int j) {
        ElementHeap<D> swap = HeapPrvek[i];
        HeapPrvek[i] = HeapPrvek[j];
        HeapPrvek[j] = swap;
    }

    private void resize() {
        capacity *= 2;
        HeapPrvek = Arrays.copyOf(HeapPrvek, capacity);
    }

    @Override
    public D odeberMax() {
        if (jePrazdny()) {
            throw new NoSuchElementException("Halda je prázdná");
        }
        D maxElement = HeapPrvek[1].data;
        HeapPrvek[1] = HeapPrvek[size];
        HeapPrvek[size] = null;
        size--;

        PosunDole(1);
        return maxElement;
    }

    private void PosunDole(int index) {
        while (true) {
            int leftChild = getLeftChild(index);
            int rightChild = getRightChild(index);
            int largest = index;

            if (leftChild <= size && HeapPrvek[leftChild].data.compareTo(HeapPrvek[largest].data) > 0) {
                largest = leftChild;
            }

            if (rightChild <= size && HeapPrvek[rightChild].data.compareTo(HeapPrvek[largest].data) > 0) {
                largest = rightChild;
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    @Override
    public D zpistupniMax() {
        if (jePrazdny()) {
            System.err.println("chyba");
        }
        return HeapPrvek[1].data;
    }

    @Override
    public Iterator vypis(eTypProhl typ) {
        switch (typ) {
            case HLOUBKA:
                return new Hloubka();
            case SIRKA:
                return new Sirka();
            default:
                throw new IllegalArgumentException("Neznámý typ prohlídky: " + typ);
        }
    }

    private class Hloubka implements Iterator<D> {

        private AbstrLIFO<Integer> stack = new AbstrLIFO<>();

        public Hloubka() {
            dejDoLeva(1);
        }

        private void dejDoLeva(int index) {
            while (index <= size) {
                stack.vloz(index);
                index = getLeftChild(index);
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.jePrazdny();
        }

        @Override
        public D next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = stack.odeber();
            D result = HeapPrvek[index].data;

            int rightChild = getRightChild(index);
            if (rightChild <= size) {
                dejDoLeva(rightChild);
            }

            return result;
        }
    }

    private class Sirka implements Iterator<D> {

        private AbstrFIFO<Integer> queue = new AbstrFIFO<>();

        public Sirka() {
            if (!jePrazdny()) {
                queue.vloz(1);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.jePrazdny();
        }

        @Override
        public D next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = queue.odeber();
            D result = HeapPrvek[index].data;

            int leftChild = getLeftChild(index);
            int rightChild = getRightChild(index);

            if (leftChild <= size) {
                queue.vloz(leftChild);
            }
            if (rightChild <= size) {
                queue.vloz(rightChild);
            }
            return result;
        }

    }
}
