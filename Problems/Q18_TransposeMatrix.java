public class Q18_TransposeMatrix {
    public static int[][] transpose(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res[j][i] = matrix[i][j];
        return res;
    }

    public static void main(String[] args) {
        int[][] mat = {{1,2,3},{4,5,6}};
        int[][] result = transpose(mat);
        for (int[] row : result) {
            for (int v : row) System.out.print(v + " ");
            System.out.println();
        }
    }
}
