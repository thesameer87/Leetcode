//1.	Write a Program to find Largest Element in an array.
public class Q1_LargestElement {
    public static int findLargest(int[] nums) {
        int max = nums[0];
        for (int num : nums) {
            if (num > max) max = num;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {10, 5, 20, 8};
        System.out.println("Largest Element: " + findLargest(arr));
    }
}

