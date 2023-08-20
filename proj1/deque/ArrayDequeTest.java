package deque;

import edu.princeton.cs.algs4.StdRandom;
import jh61b.junit.In;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addTest(){
        ArrayDeque<Integer> L1 = new ArrayDeque<Integer>();
        for (int i =0;i<5;i++) {
            L1.addFirst(3);
            L1.addFirst(2);
            L1.addFirst(1);
            L1.addLast(4);
            L1.addLast(5);
        }
        System.out.println(L1.size());
        L1.printDeque();
    }

    @Test
    public void getTest(){
        ArrayDeque<Integer> L1 = new ArrayDeque<Integer>();
        for (int i =0;i<5;i++) {
            L1.addFirst(3);
            L1.addFirst(2);
            L1.addFirst(1);
            L1.addLast(4);
            L1.addLast(5);
        }
        int n1 = L1.get(0);
        int n2 = L1.get(1);
        assertEquals(n1,1);
        assertEquals(n2,2);
    }
    @Test
    public void removeTest(){
        ArrayDeque<Integer> L1 = new ArrayDeque<Integer>();
        for (int i =0;i<100;i++) {
            L1.addFirst(3);
            L1.addFirst(2);
            L1.addFirst(1);
            L1.addLast(4);
            L1.addLast(5);
        }
        for (int j =0;j<249;j++){
            L1.removeFirst();
            L1.removeLast();
        }
        L1.removeFirst();
        L1.removeLast();
        L1.removeFirst();
        L1.removeLast();
        assertEquals(0,L1.size());
        L1.addLast(10);
        L1.printDeque();
    }

    @Test
    public void RandommizeTesttowDeque(){
        LinkedListDeque<Integer> D1 = new LinkedListDeque<Integer>();
        ArrayDeque<Integer> D2 = new ArrayDeque<Integer>();
        int N = 50000;
        for (int i = 0; i < N; i++){
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal1 = StdRandom.uniform(0, 100);
                D1.addLast(randVal1);D2.addLast(randVal1);
                assertEquals(D1.size(),D2.size());
            } else if (operationNumber == 1) {
                //addFirst
                int randVal2 = StdRandom.uniform(0, 100);
                D1.addFirst(randVal2);
                D2.addFirst(randVal2);
                assertEquals(D1.size(), D2.size());
            } else if (operationNumber == 2) {
                // get
                if (D1.size()>0){
                    int index = StdRandom.uniform(0,D1.size());
                    assertEquals(D1.get(index),D2.get(index));
                }
            }else if (operationNumber == 3){
                // removeLast
                    assertEquals(D1.removeLast(),D2.removeLast());
            }else if (operationNumber == 4){
                // removeFirst
                assertEquals(D1.removeFirst(),D2.removeFirst());
            }
        }
    }
}

