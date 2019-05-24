package multithreading.demos.calculations;

/**
 * 
 * @author Igor Ternyuk <xmonad100 at gmail.com>
 */
public class Commons {
    
    public static int[] createArray(int limit){
        int[] array = new int[limit];
        for(int i = 0; i < array.length; ++i){
            array[i] = i;
        }
        return array;
    }
    
    public static double calculate(int[] array){
        return calculate(array, 0, array.length);
    }
    
    public static double calculate(int[] array, int start, int end){
        double sum = 0;
        for(int i = start; i < end; ++i){
            sum += Commons.function(array[i]);
        } 
        return sum;
    }
    
    public static double function(int x){
        return Math.sin(x);
    }
}
