class ListNode37 {
    int val;
    ListNode37 next;
    ListNode37(int val) { this.val = val; }
}

public class Q37_OddEvenLinkedList {
    public static ListNode37 oddEvenList(ListNode37 head) {
        if (head == null) return null;
        ListNode37 odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    public static void main(String[] args) {
        ListNode37 head = new ListNode37(1);
        head.next = new ListNode37(2);
        head.next.next = new ListNode37(3);
        head.next.next.next = new ListNode37(4);

        ListNode37 result = oddEvenList(head);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
        // Output: 1 3 2 4
    }
}
