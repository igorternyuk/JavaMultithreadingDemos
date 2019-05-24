package multithreading.demos.calculations;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Sequential {
    public static void main(String[] args) {
        int limit = 20_000_000;
        int[] array = Commons.createArray(limit);
        System.out.println("Limit: " + limit);
        long startTime = System.currentTimeMillis();
        double sum = Commons.calculate(array);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Result: " + sum);
        System.out.println("Execution time: " + ((endTime - startTime) / 1000.f));
        
        
    }
}
