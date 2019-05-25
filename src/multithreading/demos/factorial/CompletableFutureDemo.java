package multithreading.demos.factorial;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class CompletableFutureDemo {
    
    public static void main(String[] args) {
        BigInteger C10_4 = combinationsParallel(100, 17);
        System.out.println("C10_4 = " + C10_4.toString());
    }
    
    public static BigInteger factorial(int i){
        BigInteger result = BigInteger.ONE;
        while(i > 0){
            result = result.multiply(BigInteger.valueOf(i));
            --i;
        }
        return result;
    }
    
    public static BigInteger combinationsSequental(int n, int k){
        return factorial(n).divide(factorial(k)).divide(factorial(n - k));
    }
    
    public static BigInteger combinationsParallel(int n, int k){
        CompletableFuture<BigInteger> factN = CompletableFuture.supplyAsync(() -> factorial(n));
        CompletableFuture<BigInteger> factK = CompletableFuture.supplyAsync(() -> factorial(k));
        CompletableFuture<BigInteger> factNminusK = CompletableFuture.supplyAsync(() -> factorial(n - k));
        return factN.thenCombine(factK, BigInteger::divide)
                .thenCombine(factNminusK, BigInteger::divide).join();
    }
}
