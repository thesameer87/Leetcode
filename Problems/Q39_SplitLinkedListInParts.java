class ListNode39 {
    int val;
    ListNode39 next;
    ListNode39(int val) { this.val = val; }
}

public class Q39_SplitLinkedListInParts {
    public static ListNode39[] splitListToParts(ListNode39 head, int k) {
        ListNode39[] result = new ListNode39[k];
        int len = 0;
        for (ListNode39 node = head; node != null; node = node.next) len++;
        int partSize = len / k, extra = len % k;

        for (int i = 0; i < k; i++) {
            ListNode39 dummy = new ListNode39(0), write = dummy;
            for (int j = 0; j < partSize + (i < extra ? 1 : 0); j++) {
                write.next = new ListNode39(head.val);
                write = write.next;
                head = head.next;
            }
            result[i] = dummy.next;
        }
        return result;
    }

    public static void main(String[] args) {
        ListNode39 head = new ListNode39(1);
        head.next = new ListNode39(2);
        head.next.next = new ListNode39(3);
        head.next.next.next = new ListNode39(4);
        head.next.next.next.next = new ListNode39(5);

        ListNode39[] parts = splitListToParts(head, 3);
        for (ListNode39 part : parts) {
            while (part != null) {
                System.out.print(part.val + " ");
                part = part.next;
            }
            System.out.println();
        }
    }
}
