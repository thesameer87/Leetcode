public class Q26_RemoveDuplicatesSortedList {
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) curr.next = curr.next.next;
            else curr = curr.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1); head.next = new ListNode(1); head.next.next = new ListNode(2);
        ListNode result = deleteDuplicates(head);
        while (result != null) {
            System.out.print(result.val + " "); result = result.next;
        }
    }
}
