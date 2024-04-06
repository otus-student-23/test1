import java.util.Arrays;
import java.util.function.Function;

public class Main {

    private static <T> void fill(T[] array, Function<Integer, T> function) {
        for (int i = 0; i < array.length; i++) {
            array[i] = function.apply(i);
        }
    }

    public static void main(String[] args) {
        Integer[] squares = new Integer[100];
        fill(squares, integer -> integer * integer); // 0, 1, 4, 9, 16 .....
        System.out.println(Arrays.toString(squares));
    }
}