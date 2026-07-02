class Solution {
    public int[] twoSum(int[] nums, int target) {
        int arr [] = new int[2];

        HashMap<Integer,Integer> hp = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            int rem = target - nums[i];
            if(hp.containsKey(rem)){
                arr[0] = hp.get(rem);
                arr[1] = i;
                return arr;
            }
            hp.put(nums[i],i) ;
        }
        return arr;
    }
}