package multithreading.demos.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class CountDownLatchDemo {
    
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            Thread thread = new MyThread(latch);
            thread.start();
            threads.add(thread);
        }
        
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(CountDownLatchDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    static class MyThread extends Thread{
        private final CountDownLatch latch;
        
        public MyThread(CountDownLatch latch){
            this.latch = latch;
        }
        
        
        @Override
        public void run(){
            try {
                unsafe();
            } catch (InterruptedException ex) {
                Logger.getLogger(CountDownLatchDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void unsafe() throws InterruptedException{
            System.out.println(getName() + " starts initialization");
            Thread.sleep((long)(Math.random() * 10000));
            latch.countDown();
            latch.await();
            System.out.println(getName() + " starts main phase");
            Thread.sleep((long)(Math.random() * 10000));
            System.out.println(getName() + " has finished his work");
        }
        
    }
}
