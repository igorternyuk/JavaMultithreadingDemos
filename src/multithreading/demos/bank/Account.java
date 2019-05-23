/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multithreading.demos.bank;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
class Account{
    private long balance;

    public Account(){
        balance = 0L;
    }

    public Account(long balance){
        this.balance = balance;
    }

    public long getBalance(){
        return this.balance;
    }

    public synchronized void deposit(long amount){
        checkIfAmountPositive();
        this.balance += amount;
        notifyAll();
    }

    public synchronized void withdraw(long amount){
        checkIfAmountPositive();
        if(checkIfEnoughMoney(amount)){
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Not enough money: " + this.balance + " < " + amount);
        }
    }

    public synchronized void waitAndWidthDraw(long amount) throws InterruptedException{
        checkIfAmountPositive();
        while(checkIfNotEnoughMoney(amount)){
            System.out.println("Waiting... Balance = " + this.balance);
            wait();
        }
        this.balance -= amount;
    }
    
    private synchronized boolean checkIfNotEnoughMoney(long amount){
        return this.balance < amount;
    }

    private synchronized boolean checkIfEnoughMoney(long amount){
        return this.balance > amount;
    }

    private void checkIfAmountPositive(){
        if(this.balance < 0){
            throw new IllegalArgumentException("Negative balance");
        }
    }

}