public class Main {
    public static void main(String[] args) {
        //System.out.println(subarray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        //System.out.println(subarray(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}));
        System.out.println(subarray(new int[]{1, 1, 1, -10, 1, 1, 1, 1}));
    }

    private static int subarray(int[] array) {
        int maxSum = array[0], currentSum = maxSum;
        for(int i = 1; i < array.length; i++) {
            maxSum = Math.max(maxSum, currentSum = Math.max(array[i], currentSum + array[i]));
        }
        return maxSum;
    }
}