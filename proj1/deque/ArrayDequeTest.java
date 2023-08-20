package deque;

import org.junit.Test;

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
        L1.addLast(10);
        L1.printDeque();
    }
}
