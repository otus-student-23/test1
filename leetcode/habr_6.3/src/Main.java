import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

interface Filter {
    boolean apply(Object o);
}


public class Main {

    private static Object[] filter(Object[] o, Filter f) {
        int newLength = o.length;
        int i = 0;
        while(i < newLength) {
            if (f.apply(o[i])) {
                i++;
            } else {
                newLength--;
                o[i] = o[newLength];
            }
        }
        return Arrays.copyOf(o, newLength);
    }

    private static <T> T[] filter2(T[] o, Predicate<T> f) {
        int newLength = o.length;
        int i = 0;
        while(i < newLength) {
            if (f.test(o[i])) {
                i++;
            } else {
                newLength--;
                o[i] = o[newLength];
            }
        }
        return Arrays.copyOf(o, newLength);
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, null, 3, 4, 3, 5};
        System.out.println(Arrays.toString(
                filter(array, o -> !Integer.valueOf(3).equals(o))
        ));

        String[] array2 = new String[]{"1rewf ", "feefewf", "a", null, "1"};
        System.out.println(Arrays.toString(
                filter(array2, new Filter() {
                    @Override
                    public boolean apply(Object o) {
                        return o != null;
                    }
                })
        ));

        Integer[] array3 = new Integer[]{1, null, 3, 4, 3, 5};
        System.out.println(Arrays.toString(
                filter(array3, o -> !Integer.valueOf(3).equals(o))
        ));
    }
}