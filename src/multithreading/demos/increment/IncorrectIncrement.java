package multithreading.demos.increment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class IncorrectIncrement {
    
    private static volatile int counter = 0;
    
    public static int nextInt() {
        return counter++;
    }
    
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>(10);
        for(int i = 0; i < 10; ++i){
            Thread thread = new Thread(() -> {
                for(int j = 0; j < 1000; ++j){
                    nextInt();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(IncorrectIncrement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        System.out.println("Final result = " + counter);
    }
}
