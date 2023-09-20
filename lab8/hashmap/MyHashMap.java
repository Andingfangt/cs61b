package hashmap;

import org.checkerframework.checker.units.qual.C;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int bucketsNumber = 16;
    private int elementNumber = 0;
    private double loadFactor = 0.75;
    private HashSet<K> keysets = new HashSet<>();
    // You should probably define some more!
    /** Constructors */
    public MyHashMap() {
        buckets = createTable(bucketsNumber);
    }

    public MyHashMap(int initialSize) {
        bucketsNumber = initialSize;
        buckets = createTable(bucketsNumber);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.bucketsNumber = initialSize;
        this.elementNumber = 0;
        this.loadFactor = maxLoad;
        buckets = createTable(bucketsNumber);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // Your code won't compile until you do so!
    @Override
    /**
     * clear() will set all variables to default
     */
    public void clear() {
        bucketsNumber = 16;
        elementNumber = 0;
        loadFactor = 0.75;
        buckets = createTable(bucketsNumber);
        keysets = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        return keysets.contains(key);
    }

    @Override
    public V get(K key) {
        int hash = key.hashCode();
        int index = Math.floorMod(hash, bucketsNumber);
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return elementNumber;
    }

    @Override
    public void put(K key, V value) {
        if ((double) elementNumber / bucketsNumber > loadFactor) {
            resize();
        }
        Node n = createNode(key, value);
        int hash = n.key.hashCode();
        int index = Math.floorMod(hash, bucketsNumber);
        if (containsKey(key)) { // if key already in map, replace its value;
            for (Node i : buckets[index]) {
                if (i.key.equals(key)) {
                    i.value = value;
                }
            }
        }
        else { // else just add it
            elementNumber ++;
            buckets[index].add(n);
            keysets.add(key);
        }
    }


    private void resize() {
        bucketsNumber *= 2;
        Collection<Node>[] newBuckets = new Collection[bucketsNumber];
        for (int i = 0; i < bucketsNumber; i++) {
            newBuckets[i] = createBucket();
        }

        for (Collection<Node> b : buckets) {
            for (Node n : b) {
                int h = n.hashCode();
                int index = Math.floorMod(h, bucketsNumber);
                newBuckets[index].add(n);
            }
        }
        buckets = newBuckets;
    }


    @Override
    public Set<K> keySet() {
        return keysets;
    }

    @Override
    public V remove(K key) {
        int hash = key.hashCode();
        int index = Math.floorMod(hash, bucketsNumber);
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                buckets[index].remove(n);
                keysets.remove(key);
                elementNumber --;
                return n.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keysets.iterator();

    }

}
