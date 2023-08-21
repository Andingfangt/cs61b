package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    Comparator<Integer> Max1 = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1,o2);
        }
    };

    Comparator<Integer> Max_x2 = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1*o1,o2*o2);
        }
    };

    Comparator<String> Max_length = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(o1.length(),o2.length());
        }
    };

    @Test
    public void Test1(){
        MaxArrayDeque<Integer> M1 = new MaxArrayDeque<Integer>(Max1);
        System.out.println(M1.max());
        for (int i = 0; i<20;i++){
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0){
                M1.addFirst(i);
            }else {
                M1.addLast(-i);
            }
        }
        M1.printDeque();
        System.out.println(M1.max());// test max()
        System.out.println(M1.max(Max_x2));// test max(C)
    }

    @Test
    public void Test2(){
        MaxArrayDeque<String> M2 = new MaxArrayDeque<>(Max_length);
        System.out.println(M2.max());
        M2.addLast("hello");
        M2.addFirst("woo");
        M2.addLast("friend");
        M2.printDeque();;
        System.out.println(M2.max());
    }
}
