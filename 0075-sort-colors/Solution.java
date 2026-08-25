class Solution {

    public void swap(int[] arr, int i, int j) {
        // Only swap if the indices are different to avoid the XOR 0 bug
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    public void sortColors(int[] nums) {
        int n = nums.length;
        int i = 0;
        int j = 0;
        int k = n - 1;
        while (j <= k) {
            if (nums[j] == 2) {
                swap(nums, j, k);
                k--;
            } else if (nums[j] == 0) {
                swap(nums, j, i);
                i++;
                j++;
            }else{

                j++;
            }
        }

    }
}