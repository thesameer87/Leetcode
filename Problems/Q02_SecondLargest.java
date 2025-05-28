// 2.	Write a Program to find Second Largest Element in an array.
public class Q2_SecondLargest {
    public static int findSecondLargest(int[] nums) {
        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > first) {
                second = first;
                first = num;
            } else if (num > second && num != first) {
                second = num;
            }
        }
        return second;
    }

    public static void main(String[] args) {
        int[] arr = {12, 35, 1, 10, 34, 1};
        System.out.println("Second Largest Element: " + findSecondLargest(arr));
    }
}
