package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
   private class BSTNode {
       K key;
       V value;
       BSTNode right, left;
       BSTNode(BSTNode r, BSTNode l, K k, V v) {
           right = r;
           left = l;
           key = k;
           value = v;
       }
   }

   private int size;
   private BSTNode root;

   public BSTMap() {
       size = 0;
       root = null;
   }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private boolean helpContain(K key, BSTNode N) {
       if (N == null) {
           return false;
       }
       if (key.compareTo(N.key) == 0) {
           return true;
       }else {
           if (key.compareTo(N.key) > 0) {
               return helpContain(key, N.right);
           }else {
               return helpContain(key, N.left);
           }
       }
    }
    @Override
    public boolean containsKey(K key) {
        return helpContain(key, root);
    }

    private V helpGet(K key, BSTNode N) {
        if (N == null) {
            return null;
        }
        if (key.compareTo(N.key) == 0) {
            return N.value;
        }else {
            if (key.compareTo(N.key) > 0) {
                return helpGet(key, N.right);
            }else {
                return helpGet(key, N.left);
            }
    }
   }

    @Override
    public V get(K key) {
        return helpGet(key, root);
    }

    @Override
    public int size() {
        return size;
    }


    private BSTNode helpPut(K key, V value, BSTNode N) {
        if (N == null) {
            return new BSTNode(null, null, key, value);
        }
        if (key.compareTo(N.key) < 0) {
            N.left = helpPut(key, value, N.left);
        }else {
            N.right = helpPut(key, value, N.right);
        }
        return N;
    }
    @Override
    public void put(K key, V value) {
        root = helpPut(key, value, root);
        size ++;
    }

    @Override
    public Set<K> keySet() {
       Set<K> set = new HashSet<>();
        for (K k : this) {
            set.add(k);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        BSTNode parent = null;
        BSTNode temp = root;
        BSTNode succ;
        BSTNode succParent;
        while (temp != null) {
            int cr = key.compareTo(temp.key);
            if (cr == 0) {
                size --;
                V value = temp.value;
                if (temp.left == null & temp.right == null) { //Node with No Children (Leaf Node)
                    if (parent == null){ // root is leaf
                        root = null;
                    }else if (parent.left == temp) {
                        parent.left = null;
                    }else {
                        parent.right = null;
                    }
                }else if (temp.right == null) { //Node with one left Children
                    if (parent == null){
                        root = temp.left;
                    }else if (parent.left == temp) {
                        parent.left = temp.left;
                    }else {
                        parent.right = temp.left;
                    }
                }else if (temp.left == null) {  //Node with one right Children
                    if (parent == null){
                        root = temp.right;
                    }else if (parent.left == temp) {
                        parent.left = temp.right;
                    }else {
                        parent.right = temp.right;
                    }
                }else { //Node with Two Children
                    succ = temp.right;
                    succParent = temp;
                    while (succ.left != null) {
                        succ = succ.left;
                        succParent = succ;
                    }
                    temp.key = succ.key;
                    temp.value = succ.value;
                    if (succParent == root) {
                        succParent.right = succ.right;
                    }else {
                        succParent.left = succ.right;
                    }

                }

            return value;
            }else if (cr > 0) {
                parent = temp;
                temp = temp.right;
            }else {
                parent = temp;
                temp = temp.left;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("not support");
    }

    @Override
    public Iterator<K> iterator() {
       return new BSTmapIterator();
    }
    private class BSTmapIterator implements Iterator<K> {
       BSTNode currentNode;
       Stack<BSTNode> s;

       public BSTmapIterator() {
           currentNode = root;
           s = new Stack<>();
           s = stackPushLeft(currentNode, s);
       }

        @Override
        public boolean hasNext() {
            return !s.isEmpty();
        }

        @Override
        public K next() {
           BSTNode n = s.pop();
           K key = n.key;
           BSTNode temp = n.right;
           s = stackPushLeft(temp, s);
           return  key;
        }
    }

/**
 * traverse the tree in an in-order fashion.
 */
    private Stack<BSTNode> stackPushLeft(BSTNode n, Stack<BSTNode> s) {
       while (n != null) {
           s.push(n);
           n = n.left;
       }
       return s;
    }
}
