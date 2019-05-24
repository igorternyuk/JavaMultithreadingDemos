package multithreading.demos.bank;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class WaitNotifyDemo {
    
    static class DepositThread extends Thread{
        private final Account account;
        
        public DepositThread(){
            this.account = new Account(0L);
        }
        
        public DepositThread(Account account){
            this.account = account;
        }
        
        @Override
        public void run(){
            for(int i = 0; i < 100; ++i){
                this.account.deposit(1L);
                try {
                    Thread.sleep(100);
                    System.out.println("Despositing... Current balance = $" + account.getBalance());
                } catch (InterruptedException ex) {
                    Logger.getLogger(WaitNotifyDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    static class WithdrawThread extends Thread{
        private final Account account;
        
        public WithdrawThread(){
            this.account = new Account(0L);
        }
        
        public WithdrawThread(Account account){
            this.account = account;
        }
        
        @Override
        public void run(){
            for(int i = 0; i < 100_000_000; ++i){
                this.account.withdraw(1L);
            }
        }
    }
    
    public static void testSynchronized(){
        try {
            Account account = new Account(20_000L);
            System.out.println("Initital balance = $" + account.getBalance());
            
            Thread depositThread = new DepositThread(account);
            Thread withdrawThread = new WithdrawThread(account);
            
            depositThread.start();
            withdrawThread.start();
            
            depositThread.join();
            withdrawThread.join();
            
            System.out.println("End balance = $" + account.getBalance());
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitNotifyDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testWaitNotify(){
        try {
            Account account = new Account();
            Thread depositThread = new DepositThread(account);
            depositThread.start();

            System.out.println("Calling withdraw of $100");
            account.waitAndWidthDraw(100);
            //depositThread.join();
            System.out.println("Widthraw finished. End balance: $" + account.getBalance());

        } catch (InterruptedException ex) {
            Logger.getLogger(WaitNotifyDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        testWaitNotify();
    }
}
