package multithreading.demos;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class ExecutorServiceDemo {
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        Future<String> future1 = executorService.submit(new Worker("worker1"));
        Future<String> future2 = executorService.submit(new Worker("worker2"));
        
        try {
            String result1 = future1.get();
            System.out.println("Result from worker1 : " + result1);
            String result2 = future2.get();
            System.out.println("Result from worker2 : " + result2);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ExecutorServiceDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("---------------------------------");
        
        System.out.println("Submit workers using invokeAll()");
        
        try {
            List<Future<String>> futures = executorService.invokeAll(Arrays.asList(new Worker("worker1"),
                    new Worker("worker2"), new Worker("worker3")));
            System.out.println("Results of invokeAll() : ");
            futures.forEach(f -> {
                try {
                    System.out.println("Result from worker: " + f.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ExecutorServiceDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutorServiceDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        executorService.shutdown();
        try {
            executorService.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutorServiceDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
    }
    
    static class Worker implements Callable<String>{
        
        private final String name;
        
        public Worker(String name){
            this.name = name;
        }
        
        @Override
        public String call() throws Exception {
            long sleepTime = (long)(Math.random() * 10000L);
            System.out.println(name + " started. Sleeping for " + sleepTime + "ms");
            Thread.sleep(sleepTime);
            System.out.println(name + " finished.");
            return this.name;
        }
        
    }
}
