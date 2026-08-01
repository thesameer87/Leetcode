class Solution {
    int solve(int nums [], int left, int right){
        if(left>right){
            return 0;
        }
        if(left == right){
            return nums[left];
        }
        int pickL = nums[left] - solve(nums,left+1,right);
        int pickR = nums[right] - solve(nums,left,right-1);
        return Math.max(pickL,pickR);
    }
    public boolean predictTheWinner(int[] nums) {
        return solve(nums,0,nums.length-1) >=0;
    }
}