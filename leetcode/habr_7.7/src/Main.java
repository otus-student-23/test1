import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String[] array = new String[]{"a", "b", "a", "b", "b"};
        System.out.println(arrayToMap(array));
    }

    private static <K> Map<K, Integer> arrayToMap(K[] ks) {
        Map<K, Integer> result = new HashMap<>();
        for (K i: ks) {
            result.put(i, result.containsKey(i) ? result.get(i) + 1 : 1);
        }
        return result;
    }
}