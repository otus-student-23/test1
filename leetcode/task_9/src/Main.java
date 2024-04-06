public class Main {
    public static void main(String[] args) {
        System.out.println(isPalindrom(123321));
    }

    private static boolean isPalindrom(int value) {
        char[] c = String.valueOf(value).toCharArray();
        for (int i = 0; i < c.length / 2; i++) {
            if (c[i] != c[c.length - i - 1]) return false;
        }
        return true;
    }
}