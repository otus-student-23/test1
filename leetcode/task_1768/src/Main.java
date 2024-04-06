public class Main {
    public static void main(String[] args) {
        //String word1 = "abc", word2 = "pqr";
        String word1 = "ab", word2 = "pqrs";
        StringBuilder result = new StringBuilder();

        int min = Math.min(word1.length(), word2.length());
        for (int i = 0; i < min; i++) {
            result.append(word1.charAt(i)).append(word2.charAt(i));
        }
        result.append(word1.length() > word2.length() ? word1.substring(min) : word2.substring(min));
        System.out.println(result);
    }
}