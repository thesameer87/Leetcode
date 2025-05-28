import java.util.HashMap;

public class Q17_DivideArrayEqualPairs {
    public static boolean divideArray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        for (int freq : map.values())
            if (freq % 2 != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,3,2,2,2};
        System.out.println(divideArray(nums)); // Output: true
    }
}
