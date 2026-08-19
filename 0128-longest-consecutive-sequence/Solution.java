class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
       
        int max = 0;
        for(int num: nums){
            set.add(num);
        }
        for(int num :set){
            if(!set.contains(num-1)){
                int current = num;
                int length = 1;

                while(set.contains(current+1)){
                    current++;
                    length++;
                }
                max= Math.max(max,length);
            }
        }
        return max;
    }
}