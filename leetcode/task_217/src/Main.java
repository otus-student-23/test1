import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println(hasDublicateBySet(new int[]{1,2,3,1}));
        System.out.println(hasDublicateBySet(new int[]{1,2,3,4}));

        System.out.println(hasDublicateBySort(new int[]{1,2,3,1}));
        System.out.println(hasDublicateBySort(new int[]{1,2,3,4}));
    }

    private static boolean hasDublicateBySet(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
            if (i >= set.size()) return true;
        }
        return false;
    }

    private static boolean hasDublicateBySort(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] == array[i+1]) return true;
        }
        return false;
    }
}