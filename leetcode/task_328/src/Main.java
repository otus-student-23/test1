public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{2,1,3,5,6,4,7};
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

        head = oddEvenList(list);
        while (head != null) {
            System.out.print(head.val + ", ");
            head = head.next;
        }
    }

    private static ListNode oddEvenList(ListNode head) {
        ListNode n1 = head;
        ListNode n2 = head.next;
        ListNode n2_head = n2;
        while (n2 != null && n2.next != null) {
            n1.next = n2.next;
            n1 = n1.next;
            n2.next = n1.next;
            n2 = n2.next;
        }
        n1.next = n2_head;
        return head;
    }
}