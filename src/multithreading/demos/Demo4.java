package multithreading.demos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Demo4 {
    static class Foo {
        int x = 0;
        volatile int y = 0;
    }
    
    public static void main(String[] args) {
        
        for(int i = 0; i < 1000; ++i){
            Foo foo = new Foo();
            Thread thread1 = new Thread(() -> { 
                foo.x = 1;
                foo.y = 1;
            });
            Thread thread2 = new Thread(() -> {
                while(foo.y != 1) {};
                System.out.println("foo.x = " + foo.x);
            });
            thread1.start();
            thread2.start();
            
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Demo4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
