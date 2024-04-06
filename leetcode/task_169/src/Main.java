public class Main {
    public static void main(String[] args) {
        //System.out.println(maxTimes(new int[]{2, 2, 1, 1, 1, 2, 2}));
        //System.out.println(maxTimes(new int[]{1, 1, 2, 2, 1, 2, 3, 1}));
        System.out.println(getMajor(new int[]{1, 2, 1, 2, 3, 3, 3, 2, 2, 2, 2}));
    }

    private static int getMajor(int[] array) {
        int count = 0;
        int element = 0;

        for (int i = 0; i < array.length && count < array.length / 2; i++) {
            if (count == 0) element = array[i];
            count += (element == array[i]) ? 1 : -1;
        }
        return element;
    }
}