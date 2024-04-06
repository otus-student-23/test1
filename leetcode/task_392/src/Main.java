public class Main {
    public static void main(String[] args) {
        String s = "abc", t = "ahbgdc";

        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j++)) i++;
        }

        System.out.println(i == s.length());
    }
}