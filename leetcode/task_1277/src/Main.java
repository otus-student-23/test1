public class Main {
    public static void main(String[] args) {
        int[][] array = new int[][]{
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1}
        };
        System.out.println(countSquare(array));

        array = new int[][]{
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 0}
        };
        System.out.println(countSquare(array));
    }

    private static int countSquare(int[][] array) {
        int result = 0;
        for (int i = 1; i < array.length; i++) {
            result += array[i][0];
            for (int j = 1; j < array[i].length; j++) {
                result += array[i][j] *= 1 + Math.min(Math.min(array[i-1][j], array[i][j-1]), array[i-1][j-1]);
            }
        }
        for (int j = 0; j < array[0].length; j++) {
            result += array[0][j];
        }
        return result;
    }
}