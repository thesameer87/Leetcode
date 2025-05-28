public class Q14_MajorityElement {
    public static int majorityElement(int[] nums) {
        int count = 0, candidate = 0;
        for (int num : nums) {
            if (count == 0) candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }

    public static void main(String[] args) {
        int[] arr = {3,2,3};
        System.out.println(majorityElement(arr)); // Output: 3
    }
}
