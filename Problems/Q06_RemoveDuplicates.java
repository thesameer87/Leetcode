import java.util.HashSet;

public class Q6_RemoveDuplicates {
    public static int[] removeDuplicates(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        return set.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 4, 4, 5};
        int[] result = removeDuplicates(arr);
        System.out.print("After Removing Duplicates: ");
        for (int num : result) System.out.print(num + " ");
    }
}
