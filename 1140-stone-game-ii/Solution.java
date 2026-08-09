class Solution {
    int n;
    int dp[][][] = new int[2][101][101];

    int solve(int piles[], int person, int i, int m) {
        if (i >= n)
            return 0;

        if (dp[person][i][m] != -1) {
            return dp[person][i][m];
        }
        int result;
        int stone = 0;

        if (person == 1) {
            result = -1;
        } else {
            result = Integer.MAX_VALUE;
        }

        for (int x = 1; x <= Math.min(2 * m, n - i); x++) {
            stone += piles[i + x - 1];

            if (person == 1) {
                result = Math.max(result, stone + solve(piles, 0, i + x, Math.max(m, x)));
            } else {
                result = Math.min(result, solve(piles, 1, i + x, Math.max(m, x)));
            }
        }
        return dp[person][i][m] = result;
    }

    public int stoneGameII(int[] piles) {
        n = piles.length;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 101; j++)
                Arrays.fill(dp[i][j], -1);

        return solve(piles, 1, 0, 1);
    }
}