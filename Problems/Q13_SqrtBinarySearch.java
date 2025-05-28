public class Q13_SqrtBinarySearch {
    public static int mySqrt(int x) {
        int left = 0, right = x, ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long)mid * mid <= x) {
                ans = mid;
                left = mid + 1;
            } else right = mid - 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(8)); // Output: 2
    }
}
