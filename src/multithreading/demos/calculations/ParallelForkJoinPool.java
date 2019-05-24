package multithreading.demos.calculations;

import java.util.concurrent.ForkJoinPool;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class ParallelForkJoinPool {
    
    
    public static void main(String[] args) {
        int[] array = Commons.createArray(20_000_000);
    
        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        
        double sum = pool.invoke(new RecursiveCalculation(array, 0, array.length));
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result : " + sum);
        
        
        System.out.println("Execution time : " + ((endTime - startTime) / 1000.f));
    }
}
