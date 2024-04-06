import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        int[] arr = new int[]{3, 2, 4};
        int val = 6;

        System.out.println(Arrays.toString(hashMap(arr, val)));
    }

    private static int[] fullScan(int[] arr, int val) {
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] + arr[j] == val) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private static int[] hashMap(int[] arr, int val) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(val - arr[i])) {
                return new int[]{i, map.get(val - arr[i])};
            }
            map.put(arr[i], i);
        }
        return null;
    }
}