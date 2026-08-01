class Solution {
    int solve(int nums [], int left, int right){
        if(left>right){
            return 0;
        }
        if(left == right){
            return nums[left];
        }
        int pickL = nums[left] + Math.min(solve(nums,left+2,right),solve(nums,left+1,right-1));
        int pickR = nums[right] + Math.min(solve(nums,left+1,right-1),solve(nums,left,right-2));
        return Math.max(pickL,pickR);
    }
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for(int i = 0; i<=n-1;i++){
            sum+=nums[i];
        }
        int p1 = solve(nums,0,n-1);
        int p2 = sum - p1;
        return p1>=p2;
    }
}