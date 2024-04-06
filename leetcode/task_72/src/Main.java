public class Main {
    public static void main(String[] args) {
        //String text1 = "horse", text2 = "ros";
        String text1 = "intention", text2 = "execution";

        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                dp[i + 1][j + 1] = (text1.charAt(i) == text2.charAt(j))
                        ? dp[i][j] + 1
                        : Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }
        System.out.println(dp[text1.length()][text2.length()]);
        System.out.println(text1.length() - dp[text1.length()][text2.length()]);
    }
}