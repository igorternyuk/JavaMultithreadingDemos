package multithreading.demos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Demo1 {
    
    public static void main(String[] args) {
        final List<Thread> threads = new ArrayList<>();
        
        for(int i = 0; i < 10; ++i){
            Thread thread = new Thread(() -> {
                System.out.println("Hello from thread: " + Thread.currentThread().getName());
            }, "Thread" + (i + 1));

            threads.add(thread);
            thread.start();
        }
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Demo1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
    }
}
