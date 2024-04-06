import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //String str1 = "ABCABC", str2 = "ABC";
        String str1 = "ABABAB", str2 = "ABAB";
        //String str1 = "LEET", str2 = "CODE";

        String str3 = str1.length() > str2.length() ? str2 : str1;
        String result = null;
        for (int i = 1; i <= str3.length(); i++) {
            String substr = str3.substring(0, i);
            if (str1.split(substr).length == 0 && str2.split(substr).length == 0) {
                result = substr;
            }
        }
        System.out.println(result);
    }
}