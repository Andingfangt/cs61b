package deque;

import afu.org.checkerframework.checker.oigj.qual.O;
import org.checkerframework.checker.units.qual.A;

import java.util.Iterator;

public class ArrayDeque<T> implements deque<T>, Iterable<T>{
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;


    public ArrayDeque(){
        size = 0 ;
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    private int startpoint(){
        if (nextFirst == items.length -1 ) return 0;
        else return nextFirst + 1;
    }

    private int endpoint(){
        if (nextLast == 0) return items.length - 1;
        else return nextLast - 1;
    }
    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        int start = startpoint();
        int end = endpoint();
        if (start<end){
            System.arraycopy(items,start,a,0,end-start+1);
        }else {
            System.arraycopy(items,start,a,0,items.length - start);
            System.arraycopy(items,0,a,items.length- start,end+1);
        }
        items = a;
        nextFirst = items.length - 1 ;
        nextLast = size;
    }
    @Override
    public void addFirst(T item) {
        if (size == items.length){
            resize(size * 2);
        }
        items[nextFirst] = item;
        size++;
        if (nextFirst == 0) nextFirst = items.length - 1;
        else nextFirst--;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast] = item;
        size++;
        if (nextLast == items.length - 1) nextLast = 0;
        else nextLast++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T i : this){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        T returnitem = get(startpoint());
        items[startpoint()] = null;
        size--;
        nextFirst = startpoint();
        return returnitem;
    }

    @Override
    public T removeLast() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        T returnitem = get(endpoint());
        items[endpoint()] = null;
        size--;
        nextLast = endpoint();
        return returnitem;

    }

    @Override
    public T get(int index) {
        return items[index];
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T>{
        int position;

        public ArrayDequeIterator(){
            position = startpoint();
        }
        @Override
        public boolean hasNext() {
            return items[position] != null;
        }

        @Override
        public T next() {
            T item = items[position];
            if (position == items.length -1) position = 0;
            else position++;
            return item;
        }
    }
}
