import java.lang.annotation.Target;
import java.util.Arrays;

public class Q10_Find_Start_and_End_of_Targetin_Sorted__Array {
    public static int[] searchRange(int[] nums, int target) {
        return new int[] {find(nums, target, true), find(nums, target, false)};
    }

    private static int find(int[] nums, int target, boolean leftBias) {
        int left = 0, right = nums.length - 1, index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                index = mid;
                if (leftBias) right = mid - 1;
                else left = mid + 1;
            } else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return index;
    }

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        System.out.println("Start and End: " + Arrays.toString(searchRange(nums, 8)));
    }
}
