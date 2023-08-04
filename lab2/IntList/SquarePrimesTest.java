package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple1() {
        IntList lst1 = IntList.of(14, 15, 16, 17, 18);
        boolean changed1 = IntListExercises.squarePrimes(lst1);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst1.toString());
        assertTrue(changed1);
    }

    @Test
    public void testSquarePrimesSimple2() {
        IntList lst2 = IntList.of(3, 4, 5, 6, 7);
        boolean changed2 = IntListExercises.squarePrimes(lst2);
        assertEquals("9 -> 4 -> 25 -> 6 -> 49", lst2.toString());
        assertTrue(changed2);
    }

    @Test
    public void testSquarePrimesSimple3() {
        IntList lst3 = IntList.of(10, 23, 12, 27, 71);
        boolean changed3 = IntListExercises.squarePrimes(lst3);
        assertEquals("10 -> 529 -> 12 -> 27 -> 5041", lst3.toString());
        assertTrue(changed3);
    }
}




