package multithreading.demos.calculations;

import java.util.Arrays;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class ParallelStreamCalculation {
    
    public static void main(String[] args) {
        calcWithDoubleAdder();
    }
    
    public static void calcWithDoubleAdder(){
        int limit = 20_000_000;
        int[] array = Commons.createArray(limit);
        System.out.println("Limit: " + limit);
        long startTime = System.currentTimeMillis();
        
        DoubleAdder sum = new DoubleAdder();
        
        Arrays.stream(array).parallel()
                .mapToDouble(Commons::function)
                .forEach(sum::add);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Result: " + sum);
        System.out.println("Execution time: " + ((endTime - startTime) / 1000.f));
    }
    
    public static void calc(){
        int limit = 20_000_000;
        int[] array = Commons.createArray(limit);
        System.out.println("Limit: " + limit);
        long startTime = System.currentTimeMillis();
        
        double sum = Arrays.stream(array).parallel()
                .mapToDouble(Commons::function)
                .sum();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Result: " + sum);
        System.out.println("Execution time: " + ((endTime - startTime) / 1000.f));
    }
}
