import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[]{3, 2, 2, 3};
        System.out.println(removeElement(array, 3));
        System.out.println(Arrays.toString(array));
    }

    private static int removeElement(int[] array, int value) {
        int result = array.length;
        int i = 0;
        while (i < result) {
            if (array[i] == value) {
                array[i] = array[--result];
            } else {
                i++;
            }
        }
        return result;
    }
}