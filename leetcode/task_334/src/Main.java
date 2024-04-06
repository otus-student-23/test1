public class Main {
    public static void main(String[] args) {
        //int[] arr = new int[]{8,5,6,2,1,7,5,4,2,1,8};
        //int[] arr = new int[]{1,2,3,4,5};//true
        //int[] arr = new int[]{5,4,3,2,1};//false
        int[] arr = new int[]{2,1,5,0,4,6};//true

        int[] result = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int v : arr) {
            int i = 0;
            while (i < result.length && result[i] < v) {
                i++;
            }
            if (i == result.length) {
                System.out.println(true);
                return;
            }
            result[i] = v;
        }
        System.out.println(false);
    }
}