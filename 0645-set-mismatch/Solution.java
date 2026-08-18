class Solution {
    public int[] findErrorNums(int[] nums) {
        int duplicate = -1;
        for(int i = 0; i < nums.length; i++){
            int idx = Math.abs(nums[i]) -1;
            
            if(nums[idx] < 0){
                duplicate = Math.abs(nums[i]);
            }else{
                nums[idx] *= -1;
            }
        }
        for(int i = 0; i< nums.length;i++){
            if(nums[i] >0){
                return new int[]{duplicate,i+1};
            }
        }
        return new int[]{-1,-1};
    }
}