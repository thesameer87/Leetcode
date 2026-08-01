class Solution {
    Integer[][] dp = new Integer[23][23];

    int solve(int nums [], int left, int right){
        if(left>right){
            return 0;
        }
        if (dp[left][right] != null) {
            return dp[left][right];
        }
        if(left == right){
            return dp[left][right] = nums[left];
        }
        int pickL = nums[left] - solve(nums,left+1,right);
        int pickR = nums[right] - solve(nums,left,right-1);
        return dp[left][right] = Math.max(pickL,pickR);
    }
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        dp = new Integer[n][n];
        return solve(nums,0,nums.length-1) >=0;
    }
}