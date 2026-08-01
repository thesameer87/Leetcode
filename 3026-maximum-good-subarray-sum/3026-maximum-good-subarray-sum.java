class Solution {
    public long maximumSubarraySum(int[] nums, int k) {
        HashMap<Integer,Long> map = new HashMap<>();
        map.put(nums[0],0L);
        long prefix = 0;
        int n = nums.length;
        long ans = Long.MIN_VALUE;
        for(int i = 0; i<n; i++) {
            int x = nums[i];
            if(map.containsKey(x - k)){
                ans = Math.max(ans,prefix + x - map.get(x-k));
            }
            if(map.containsKey(x + k)){
                ans = Math.max(ans,prefix + x - map.get(x+k));
            }

            if(!map.containsKey(x)){
                map.put(x,prefix);
            }else{
                map.put(x,Math.min(map.get(x),prefix));
            }
            prefix+= x;
        }
        if(ans == Long.MIN_VALUE ){
            return 0;
        }else{
            return ans;
        }
    }
}