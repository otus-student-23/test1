import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,3,12};
        System.out.println(Arrays.toString(nums));

        int offset = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                offset++;
            } else {
                nums[i-offset] = nums[i];
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOf(nums, nums.length - offset)));
    }
}