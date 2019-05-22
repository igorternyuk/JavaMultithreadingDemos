package multithreading.demos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Demo3 {
    
    static class Counter{
        private int x = 0;
        
        public synchronized void increment(){
            ++x;
        }
        
        public int getX(){
            return x;
        }
    }
    
    public static void main(String[] args) {
        Counter counter = new Counter();
        
        Set<Integer> hs = new HashSet<>();
        
        Runnable r = () -> {
            for(int i = 1; i <= 1000000; ++i){
                counter.increment();
            }
        };
        
        List<Thread> threads = Stream.generate(() -> new Thread(r)).limit(10).peek(Thread::start)
                .collect(Collectors.toList());
        
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Demo3.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        System.out.println("Result = " + counter.getX());
    }
}
