import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        double[] d = new double[10];
        for (int i = 0; i < d.length; i++) {
            d[i] = Math.random();
        }

        double min = Double.MIN_VALUE, max = Double.MIN_VALUE, avg = Double.MIN_VALUE;
        for (int i = 0; i < d.length; i++) {
            if (min > d[i]) min = d[i];
            if (max < d[i]) max = d[i];
            avg += d[i] / d.length;
        }

        System.out.println(Arrays.toString(d));
        System.out.println("min: " + min);
        System.out.println("max: " + max);
        System.out.println("avg: " + avg);
    }
}