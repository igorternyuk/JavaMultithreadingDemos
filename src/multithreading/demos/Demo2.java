package multithreading.demos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Demo2 {
    
    static class MyRunnable implements Runnable{
        private boolean stopped = false;
        
        public boolean keepRunning(){
            return stopped == false;
        }
        
        public void tryToStop(){
            stopped = true;
        }
        
        @Override
        public void run() {
            while(keepRunning()){
                System.out.println("Running ... ");
                try {
                    Thread.sleep(1L * 1000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Demo2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    public static void main(String[] args) {
        
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        
        try {
            Thread.sleep(10L * 1000L);
            myRunnable.tryToStop();
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Demo2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}
