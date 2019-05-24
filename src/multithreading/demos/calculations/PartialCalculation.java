package multithreading.demos.calculations;

import java.util.concurrent.Callable;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class PartialCalculation implements Callable<Double>{
    
    private final int[] array;
    private final int start;
    private final int end;
    
    public PartialCalculation(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }
    
    @Override
    public Double call() throws Exception {
        return Commons.calculate(array, start, end);
    }
    

}
