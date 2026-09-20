class Solution {
    public int[] searchRange(int[] nums, int target) {
        int [] arr = {-1,-1};
        int l = 0;
        int h = nums.length-1;
        while(l<=h){
            int mid = l+ (h-l)/2;

            if(nums[mid]>= target){
                if(nums[mid] == target){
                    arr[0] = mid;
                }
                
                h = mid-1;
            }else{
                
                l = mid+1;
            }
        }
        int left = 0;
        int r = nums.length-1;
        while(left<=r){
            int m = left+ (r-left)/2;

            if(nums[m]<= target){
                if(nums[m] == target){
                    arr[1] = m;
                }
                
                left = m+1;
            }else{
                r = m-1;
                
            }
        }
        return arr;
    }
}