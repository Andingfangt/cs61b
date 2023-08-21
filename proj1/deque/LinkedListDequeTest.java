package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void AddandGetTest(){
        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>();
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);

        int n1 = L.get(0);
        int n2 = L.get(3);
        int n3 = L.getRecursive(0);
        int n4 = L.getRecursive(3);

        assertEquals(1,n1);
        assertEquals(4,n2);
        assertEquals(n1,n3);
        assertEquals(n2,n4);
    }

    @Test
    public void RemoveTest(){
        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>();
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);

        int n1 = L.removeFirst();
        int n2 = L.removeFirst();
        int n3 = L.removeLast();
        int n4 = L.removeLast();
        L.removeFirst();
        L.removeLast();
        L.removeFirst();
        assertEquals(1,n1);
        assertEquals(2,n2);
        assertEquals(6,n3);
        assertEquals(5,n4);
    }

    @Test
    public void IteratorandPrintTest(){
        LinkedListDeque<Integer> L = new LinkedListDeque<Integer>();
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);

        L.printDeque();
    }

    @Test
    public void equalsTest(){
        Deque<Integer> L = new LinkedListDeque<Integer>();
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        Deque<Integer> L2 = new LinkedListDeque<Integer>();
        L2.addLast(1);
        L2.addLast(2);
        L2.addLast(3);
        assertTrue(L.equals(L2));
        Deque<Integer> A = new ArrayDeque<>();
        A.addFirst(3);
        A.addFirst(2);
        A.addFirst(1);
        assertTrue(A.equals(L));
    }
}
