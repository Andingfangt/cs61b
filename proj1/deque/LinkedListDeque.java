package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;
        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private int size;
    private Node sentinel;
    @Override
    public void addFirst(T item) {
        Node n = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = n;
        sentinel.next = n;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node n = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = n;
        sentinel.prev = n;
        size++;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T i :this) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if  (o instanceof Deque D2){
            if (this.size() != D2.size()) return false;
            for (int i=0;i<size;i++) {
                if (get(i) != D2.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
        /*if (this == o) {
            return true;
        }

        if (o != null && getClass() == o.getClass()) {
            LinkedListDeque L2 = (LinkedListDeque) o;  // Cast o to ArrayDeque

            if (this.size() != L2.size()) {
                return false;
            }

            for (int i = 0; i < size; i++) {
                if (this.get(i) != L2.get(i)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }   */

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T returnitem = sentinel.next.item;
        Node removeNode = sentinel.next;
        sentinel.next = removeNode.next;
        removeNode.next.prev = sentinel;
        removeNode.prev = null;
        removeNode.next = null;
        size--;
        return returnitem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T retrunitem = sentinel.prev.item;
        Node removeNode = sentinel.prev;
        sentinel.prev = removeNode.prev;
        removeNode.prev.next = sentinel;
        removeNode.prev = null;
        removeNode.next = null;
        size--;
        return retrunitem;
    }

    @Override
    public T get(int index) {
        int i = 0;
        Node n = sentinel.next;
        while (i < index) {
            n = n.next;
            i++;
        }
        return n.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkeListDequeIterator();
    }
    private class LinkeListDequeIterator implements Iterator<T> {
        private int position;

        private LinkeListDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            Node n = sentinel.next;
            for (int i = 0; i < position; i++) {
                n = n.next;
            }
            position++;
            return n.item;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    public T getRecursive(int index) {
        Node n = sentinel.next;
        return helpRec(n, index);
    }

    private T helpRec(Node n, int index) {
        if (index == 0) {
            return n.item;
        } else {
            return helpRec(n.next, index - 1);
        }
    }
}
