class Solution {
    public int smallestNumber(int n, int t) {

        int i = n;
        while (true) {
            int num = i;
            int product = 1;
            while (num > 0) {
                int digit = num % 10;
                product = product * digit;
                num = num / 10;
            }
            if (product % t == 0) {
                return i;
            }
            i++;
        }

    }
}