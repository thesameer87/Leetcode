public class Q32_MinimumOperationsToMakeArrayIncreasing {
    public static int minOperations(int[] nums) {
        int operations = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                operations += nums[i - 1] + 1 - nums[i];
                nums[i] = nums[i - 1] + 1;
            }
        }
        return operations;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1};
        System.out.println(minOperations(nums)); // Output: 3
    }
}
