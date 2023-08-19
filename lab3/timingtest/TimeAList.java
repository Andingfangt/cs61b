package timingtest;
import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

import javax.naming.InsufficientResourcesException;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static AList<Integer> getNAList(){
        AList<Integer> Ns = new AList<>();
        for (int i =1000;i<=128000;i*=2){
            Ns.addLast(i);
        }
        return Ns;
    }
    public static void timeAListConstruction() {
        AList<Integer> Ns =  getNAList();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = getNAList();
        AList<Integer> test = new AList<>();
        for (int i =0; i < Ns.size(); i++){
            Stopwatch sw = new Stopwatch();
            for (int j =0 ; j < Ns.get(i); j++){
                test.addLast(1);
            }
            double time = sw.elapsedTime();
            times.addLast(time);
        }
        printTimingTable(Ns, times,opCounts);
    }
}
