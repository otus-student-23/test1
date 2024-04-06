import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Double> array = new ArrayList<>(1000000);
        List<Double> linked = new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            double value = Math.random();
            array.add(value);
            linked.add(value);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            array.get((int)(Math.random() * 999999));
        }
        System.out.println(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            linked.get((int)(Math.random() * 999999));
        }
        System.out.println(System.currentTimeMillis() - startTime);

    }
}