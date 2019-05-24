package multithreading.demos.calculations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import multithreading.demos.ExecutorServiceDemo;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class ParallelExecutorService {
    
    public static void main(String[] args) {
        final int[] array = Commons.createArray(20_000_000);
        final int threadCount = 4;
        
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        List<Future<Double>> futures = new ArrayList<>(threadCount);
        final int step = array.length / threadCount;
        
        System.out.println("step = " + step);
        for(int i = 0; i < threadCount; ++i){
            
            final int start = step * i;
            final int end = start + step;
            System.out.println("Segment " + (i + 1) + " start = " + start + " end = " + end);
            Future<Double> segment = executor.submit(new PartialCalculation(array, start, end));
            futures.add(segment);
        }
        int reminder = array.length % threadCount;
        if(reminder != 0){
            final int start = step * threadCount;
            final int end = start + reminder - 1;
            System.out.println("Last segment start = " + start + " end = " + end);
            Future<Double> segment = executor.submit(new PartialCalculation(array, start, end));
            futures.add(segment);
        }
        
        try {
            double result = 0;
            for(Future<Double> f: futures){
                result += f.get();
            }
            System.out.println("Executor service with " + threadCount + " threads result: " + result);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ParallelExecutorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + ((endTime - startTime) / 1000.f));
        
        executor.shutdown();
        try {
            executor.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutorServiceDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
