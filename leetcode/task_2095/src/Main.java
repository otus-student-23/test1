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

        head = list;
        deleteMiddle(list);
        System.out.println();
        while (head != null) {
            System.out.print(head.val + ", ");
            head = head.next;
        }
    }

    private static void deleteMiddle(ListNode head) {
        ListNode next = new ListNode(0, head), next2x = head;
        while (next2x != null && next2x.next != null) {
            next = next.next;
            next2x = next2x.next.next;
        }
        next.next = next.next.next;
    }
}