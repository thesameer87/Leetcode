class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        Arrays.sort(nums);
        HashSet<Integer> set = new HashSet<>();

        for(int i = 0; i< nums.length;i++){
            set.add(nums[i]);
        }
        int s = nums[0];
        for(int i = s; i<nums[nums.length-1];i++ ){
            if(!set.contains(i)){
                ans.add(i);
            }
        }
        
        Collections.sort(ans);
        return ans;
    }
}