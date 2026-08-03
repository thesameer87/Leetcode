class Solution {
    int n;
    Integer dp[];
    int solve(int nums [], int i){
        if(i>=n){
            return 0;
        }
        if(dp[i] != null){
            return dp[i];
        }
        int result = nums[i] - solve(nums,i+1);

        if(i+1<n){
            result = Math.max(result,nums[i]+nums[i+1] - solve(nums,i+2));
        }

        if(i+2<n){
            result = Math.max(result,nums[i]+nums[i+1]+nums[i+2] - solve(nums,i+3));
        }
        return dp[i] = result;
    }
    public String stoneGameIII(int[] stoneValue) {
        n = stoneValue.length;
        dp = new Integer[n];
        int diff = solve(stoneValue,0); //Alice - Bob
        if(diff>0){
            return "Alice";
        
        } else if(diff<0){
            return "Bob";
        }else{
            return "Tie";
        }
    }
}