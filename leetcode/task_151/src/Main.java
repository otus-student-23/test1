public class Main {
    public static void main(String[] args) {
        //String s = "the sky is blue";
        String s = "  hello world  ";

        String[] arr = s.split(" ");
        StringBuilder sb = new StringBuilder(arr[arr.length-1]);
        for (int i = arr.length-2; i >= 0; i--) {
            if (arr[i].length() > 0) sb.append(" ").append(arr[i]);
        }
        System.out.println(sb);
    }
}