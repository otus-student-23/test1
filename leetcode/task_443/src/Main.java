import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        char[] arr = new char[]{'d','a','a','b','b','b','b','b','b','b','b','b','b','b','b','b','c','c'};

        char[] result = new char[arr.length];
        int pos = 0;
        int len = 1;
        result[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (result[pos] == arr[i]) {
                len++;
            } else {
                if (len > 1) {
                    String l = String.valueOf(len);
                    for (int j = 0; j < l.length(); j++) {
                        result[++pos] = l.charAt(j);
                    }
                }
                result[++pos] = arr[i];
                len = 1;
            }
        }
        if (len > 1) {
            String l = String.valueOf(len);
            for (int j = 0; j < l.length(); j++) {
                result[++pos] = l.charAt(j);
            }
        }
        System.out.println(Arrays.toString(result));
    }
}