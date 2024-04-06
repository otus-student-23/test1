public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,7,1,2,6};
        ListNode list = null;
        for (int i = arr.length-1; i >= 0; i--) {
            list = new ListNode(arr[i], list);
        }

        ListNode head = list;
        while (head != null) {
            System.out.print(head.val + ", ");
            head = head.next;
        }

        System.out.println();

        head = reverseList(list);
        while (head != null) {
            System.out.print(head.val + ", ");
            head = head.next;
        }
    }

    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while(current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}