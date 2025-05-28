class ListNode36 {
    int val;
    ListNode36 next;
    ListNode36(int val) { this.val = val; }
}

public class Q36_AddTwoNumbersInLinkedList {
    public static ListNode36 addTwoNumbers(ListNode36 l1, ListNode36 l2) {
        ListNode36 dummy = new ListNode36(0), curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) { sum += l1.val; l1 = l1.next; }
            if (l2 != null) { sum += l2.val; l2 = l2.next; }
            carry = sum / 10;
            curr.next = new ListNode36(sum % 10);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode36 l1 = new ListNode36(2);
        l1.next = new ListNode36(4);
        l1.next.next = new ListNode36(3);
        ListNode36 l2 = new ListNode36(5);
        l2.next = new ListNode36(6);
        l2.next.next = new ListNode36(4);

        ListNode36 result = addTwoNumbers(l1, l2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
        // Output: 7 0 8
    }
}
