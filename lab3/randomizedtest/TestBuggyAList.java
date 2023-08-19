package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> A = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        A.addLast(3);B.addLast(3);
        A.addLast(4);B.addLast(4);
        A.addLast(5);B.addLast(5);
        assertEquals(A.size(),B.size());
        for (int i=0; i<3;i++){
            assertEquals(A.removeLast(),B.removeLast());
        }
    }

    @Test
    public void RandomizeTest(){
        AListNoResizing<Integer> L1 = new AListNoResizing<>();
        BuggyAList<Integer> L2 = new BuggyAList<>();
        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L1.addLast(randVal);L2.addLast(randVal);
                assertEquals(L1.size(),L2.size());
            } else if (operationNumber == 1) {
                // getLast
                if (L1.size()>0){
                   assertEquals(L1.getLast(),L2.getLast());
                }
            }else if (operationNumber == 2){
               // removeLast
               if (L1.size()>0){
                   assertEquals(L1.removeLast(),L2.removeLast());
               }
            }
        }
    }
}
