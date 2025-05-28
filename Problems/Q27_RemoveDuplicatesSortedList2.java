public class Q27_RemoveDuplicatesSortedList2 {
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0), prev = dummy;
        dummy.next = head;
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                while (head.next != null && head.val == head.next.val) head = head.next;
                prev.next = head.next;
            } else prev = prev.next;
            head = head.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1); head.next = new ListNode(1); head.next.next = new ListNode(2);
        ListNode result = deleteDuplicates(head);
        while (result != null) {
            System.out.print(result.val + " "); result = result.next;
        }
    }
}
