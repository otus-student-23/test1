public class Main {
    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 3, 5, 7, 9};
        var iterator = new ArrayIterator<>(array);
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + ", ");
        }
    }
}