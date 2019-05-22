package multithreading.demos;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Demo5 {
    public static void main(String[] args) throws Exception{
        Thread workThread = new WorkThread();
        Thread sleepThread = new SleepThread();
        
        System.out.println("Starting threads...");
        workThread.start();
        sleepThread.start();
        
        Thread.sleep(100L);
            
        System.out.println("Interrupting threads..");
        workThread.interrupt();
        sleepThread.interrupt();

        System.out.println("Joining threads...");
        workThread.join();
        sleepThread.join();            

        System.out.println("All done");       
    }
    
    static class WorkThread extends Thread{
        
        @Override
        public void run(){
            long sum = 0;
            for(int i = 0; i < 1000_000_000; ++i){
                sum += i;
                if(i % 100 == 0 && isInterrupted()){
                    System.out.println("Work thread was interruped at i = " + i + ". Current sum = " + sum);
                    break;
                }
            }
            System.out.println("Work thread finished. Current sum = " + sum);
        }
    }
    
    static class SleepThread extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException ex) {
                System.out.println("Sleep interrupted");
            }
        }
    }
}
