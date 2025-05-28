public class Q30_ClimbingStairs {
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2, c = 0;
        for (int i = 3; i <= n; i++) {
            c = a + b; a = b; b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(5)); // Output: 8
    }
}
