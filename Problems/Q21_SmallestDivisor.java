public class Q21_SmallestDivisor {
    public static int smallestDivisor(int[] nums, int threshold) {
        int left = 1, right = (int) 1e6, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int sum = 0;
            for (int num : nums) sum += (num + mid - 1) / mid;
            if (sum <= threshold) {
                ans = mid;
                right = mid - 1;
            } else left = mid + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,5,9};
        int threshold = 6;
        System.out.println(smallestDivisor(nums, threshold)); // Output: 5
    }
}
