import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int[] a = new int[]{1,2,2,1},
                b = new int[]{2,2};
        System.out.println(Arrays.toString(intersec(a, b)));//[2]

        a = new int[]{4,9,5};
        b = new int[]{9,4,9,8,4};
        System.out.println(Arrays.toString(intersec(a, b)));//[9,4]
    }

    private static int[] intersec(int[] a, int[] b) {
        Set<Integer> aSet = new HashSet<>();
        for (int k : a) {
            aSet.add(k);
        }

        Set<Integer> bSet = new HashSet<>();
        for (int k : b) {
            if (aSet.contains(k)) bSet.add(k);
        }

        int[] result = new int[bSet.size()];
        int i = 0;
        for (int k : bSet) {
            result[i++] = k;
        }
        return result;
    }
}