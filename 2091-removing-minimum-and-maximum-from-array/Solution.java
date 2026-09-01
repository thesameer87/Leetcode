class Solution {
    public int smallest(int nums[]) {
        int small = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < small) {
                small = nums[i];
                idx = i;
            }
        }
        return idx;
    }

    public int largest(int nums[]) {
        int small = Integer.MIN_VALUE;
        int idx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > small) {
                small = nums[i];
                idx = i;
            }
        }
        return idx;
    }

    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int ans = -1;
        int max = 1 + Math.max(smallest(nums), largest(nums));

        int left = max;
        int right = n - Math.min(smallest(nums), largest(nums));
        int both = 1 + Math.min(smallest(nums), largest(nums))
                + n - Math.max(smallest(nums), largest(nums));

        ans = Math.min(left, (Math.min(right, both)));
        return ans;

    }
}