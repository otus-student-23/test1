import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4};
        //int[] array = new int[]{-1,1,0,-3,3};
        System.out.println(Arrays.toString(array));

        int[] result = new int[array.length];
        result[0] = 1;
        for (int i = 1; i < array.length; i++) {
            result[i] = result[i-1] * array[i-1];
        }
        System.out.println(Arrays.toString(result));

        int prev = 1;
        for(int i = result.length-1; i >= 0; i--) {
            result[i] *= prev;
            prev *= array[i];
        }
        System.out.println(Arrays.toString(result));
    }
}