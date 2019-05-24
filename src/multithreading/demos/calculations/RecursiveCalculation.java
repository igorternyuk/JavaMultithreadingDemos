package multithreading.demos.calculations;

import java.util.concurrent.RecursiveTask;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class RecursiveCalculation extends RecursiveTask<Double>{
    public static final int SEQUENTIAL_THRESHOLD = 50_000;
    private final int[] array;
    private final int start;
    private final int end;
    
    public RecursiveCalculation(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Double compute() {
        if(end - start < SEQUENTIAL_THRESHOLD){
            return Commons.calculate(array, start, end);
        } else {
            int middle = (start + end) / 2;
            RecursiveCalculation left = new RecursiveCalculation(array, start, middle);
            RecursiveCalculation right = new RecursiveCalculation(array, middle, end);
            invokeAll(left, right);
            return left.join() + right.join();
        }
    }
    
}
