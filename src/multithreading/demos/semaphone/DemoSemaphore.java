package multithreading.demos.semaphone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class DemoSemaphore {
    
    private static final int THREAD_MAX_COUNT = 2;
    
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(THREAD_MAX_COUNT, true);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            Thread thread = new MyThread(semaphore);
            thread.start();
            threads.add(thread);
        }
        
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(DemoSemaphore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        threads.forEach(Thread::interrupt);
        
    }
    
    static class MyThread extends Thread {
        
        private final Semaphore semaphore;
        
        public MyThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        
        @Override
        public void run(){
            try {
                unsafe();
            } catch (InterruptedException ex) {
                System.out.println(getName() + " was interrupted");
                Logger.getLogger(DemoSemaphore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void unsafe() throws InterruptedException{
            for(;;){
                try{
                    semaphore.acquire();
                    System.out.println(getName() + " aquires the semaphore");
                    Thread.sleep(5000L);
                }
                finally{
                    semaphore.release();
                    System.out.println(getName() + " releases the semaphore");
                }
            }
        }
        
    }
}
