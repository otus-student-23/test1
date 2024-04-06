import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sum2(new int[]{2, 4, 3}, new int[]{5, 6, 4})));
    }

    private static int[] sum(int[] a, int[] b) {
        int[] c = new int[Math.max(a.length, b.length) + 1];
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            c[i] += (a[i] + b[i]) % 10;
            if (a[i] + b[i] >= 10) c[i+1]++;
        }
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            c[i] += (a[i] + b[i]) % 10;
            if (a[i] + b[i] >= 10) c[i+1]++;
        }
        return c;
    }

    private static int[] sum2(int[] a, int[] b) {
        int[] result;
        int[] c;
        if (a.length > b.length) {
            result = Arrays.copyOf(a, a.length+1);
            c = b;
        } else {
            result = Arrays.copyOf(b, b.length+1);
            c = a;
        }

        for (int i = 0; i < c.length; i++) {
            if (result[i] + c[i] >= 10) result[i+1]++;
            result[i] = (result[i] + c[i]) % 10;
        }
        return result;
    }
}