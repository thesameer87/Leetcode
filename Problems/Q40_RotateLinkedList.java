class ListNode40 {
    int val;
    ListNode40 next;
    ListNode40(int val) { this.val = val; }
}

public class Q40_RotateLinkedList {
    public static ListNode40 rotateRight(ListNode40 head, int k) {
        if (head == null || k == 0) return head;
        int len = 1;
        ListNode40 tail = head;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        k %= len;
        if (k == 0) return head;
        tail.next = head;

        for (int i = 0; i < len - k; i++) {
            tail = tail.next;
        }
        head = tail.next;
        tail.next = null;
        return head;
    }

    public static void main(String[] args) {
        ListNode40 head = new ListNode40(1);
        head.next = new ListNode40(2);
        head.next.next = new ListNode40(3);
        head.next.next.next = new ListNode40(4);
        head.next.next.next.next = new ListNode40(5);

        ListNode40 result = rotateRight(head, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
        // Output: 4 5 1 2 3
    }
}
