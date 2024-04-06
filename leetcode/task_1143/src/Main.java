public class Main {
    public static void main(String[] args) {
        //String text1 = "abcde", text2 = "ace";
        //String text1 = "abc", text2 = "abc";
        //String text1 = "abc", text2 = "def";
        String text1 = "adbcabe", text2 = "abaed";

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                dp[i + 1][j + 1] = (text1.charAt(i) == text2.charAt(j))
                        ? dp[i][j] + 1
                        : Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }
        System.out.println(dp[text1.length()][text2.length()]);
    }
}