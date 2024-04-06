import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Main {

    private static <T> Collection<T> distinct(Collection<T> array) {
        return new HashSet<T>(array);
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 1, 5, 6);
        System.out.println(distinct(list));
    }
}