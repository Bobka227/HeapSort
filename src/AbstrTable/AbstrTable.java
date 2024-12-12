/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AbstrTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author 38067
 */
/**
 *
 * @author 38067
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    public class Node<K, V> {

        public K key;
        public V value;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> koren;

    @Override
    public void zrus() {
        koren = null;
    }

    @Override
    public boolean jePrazdny() {
        return koren == null;
    }

    @Override
    public V najdi(K key) {
        return najdiRekurse(koren, key);
    }

    private V najdiRekurse(Node<K, V> actual, K key) {
        if (actual == null) {
            return null;
        } else if (key.equals(actual.key)) {
            return actual.value;
        }
        int porovnani = key.compareTo(actual.key);

        if (porovnani < 0) {
            return najdiRekurse(actual.left, key);
        } else {
            return najdiRekurse(actual.right, key);
        }
    }

    @Override
    public void vloz(K key, V value) {
        koren = pridejRekurse(koren, key, value);
    }

    private Node<K, V> pridejRekurse(Node<K, V> actual, K key, V value) {
        if (actual == null) {
            return new Node(key, value);
        }
        if (key.equals(actual.key)) {
            actual.value = value;
            return actual;
        }
        int porovnani = key.compareTo(actual.key);
        if (porovnani < 0) {
            actual.left = pridejRekurse(actual.left, key, value);
        } else if (porovnani > 0) {
            actual.right = pridejRekurse(actual.right, key, value);
        }

        return actual;

    }

    @Override 
    public V odeber(K key) { 
        Node<K, V> deletedNode = new Node<>(null, null); 
        koren = odeberRekurse(koren, key, deletedNode); 
        return deletedNode.value; 
    } 
 
    private Node<K, V> odeberRekurse(Node<K, V> actual, K key, Node<K, V> deletedNode) { 
        if (actual == null) { 
            return null; 
        } 
 
        int porovnani = key.compareTo(actual.key); 
        if (porovnani < 0) { 
            actual.left = odeberRekurse(actual.left, key, deletedNode); 
        } else if (porovnani > 0) { 
            actual.right = odeberRekurse(actual.right, key, deletedNode); 
        } else { 
            deletedNode.key = actual.key; 
            deletedNode.value = actual.value; 
 
            if (actual.left == null) { 
                return actual.right; 
            } else if (actual.right == null) { 
                return actual.left; 
            } 
 
            Node<K, V> min = najdiMinimalniUzel(actual.right); 
            actual.key = min.key; 
            actual.value = min.value; 
            actual.right = odeberRekurse(actual.right, min.key, new Node<>(null, null)); 
        } 
        return actual; 
    } 
 
    private Node<K, V> najdiMinimalniUzel(Node<K, V> actual) { 
        return actual.left == null ? actual : najdiMinimalniUzel(actual.left); 
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case HLOUBKA:
                return new Hloubka();
            case SIRKA:
                return new Sirka();
            default:
                throw new IllegalArgumentException("Neznámý typ prohlídky: " + typ);
        }
    }

    private class Hloubka implements Iterator<V> {

        private AbstrLIFO<Node<K, V>> stack = new AbstrLIFO<>();

        public Hloubka() {
            dejDoLeva(koren);
        }

        public void dejDoLeva(Node<K, V> actual) {
            Node<K, V> node = actual;
            while (node != null) {
                stack.vloz(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.jePrazdny();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<K, V> node = stack.odeber();
            V result = node.value;

            Node<K, V> nodeRight = node.right;
            while (nodeRight != null) {
                stack.vloz(nodeRight);
                nodeRight = nodeRight.left;
            }
            return result;

        }
    }

    private class Sirka implements Iterator<V> {

        private AbstrFIFO<Node<K, V>> queue = new AbstrFIFO<>();

        public Sirka() {
            if (koren != null) {
                queue.vloz(koren);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.jePrazdny();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<K, V> actual = queue.odeber();

            if (actual.left != null) {
                queue.vloz(actual.left);
            }
            if (actual.right != null) {
                queue.vloz(actual.right);
            }
            return actual.value;
        }

    }

}
