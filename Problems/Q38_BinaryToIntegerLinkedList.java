class ListNode38 {
    int val;
    ListNode38 next;
    ListNode38(int val) { this.val = val; }
}

public class Q38_BinaryToIntegerLinkedList {
    public static int getDecimalValue(ListNode38 head) {
        int result = 0;
        while (head != null) {
            result = result * 2 + head.val;
            head = head.next;
        }
        return result;
    }

    public static void main(String[] args) {
        ListNode38 head = new ListNode38(1);
        head.next = new ListNode38(0);
        head.next.next = new ListNode38(1);
        System.out.println(getDecimalValue(head)); // Output: 5
    }
}
