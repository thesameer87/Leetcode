class Solution {
    public double minPrice(int[] prices, int[] discounts) {
        Arrays.sort(prices);
        Arrays.sort(discounts);
        int m = prices.length;
        int n = discounts.length;
        double sum = 0.0;
        int i = m-1;
        int j = n-1;
        while(i>=0 && j>=0){
            double p = prices[i]; double d = discounts[j];
            double cost = (p *(100.0-d)) / 100.0;
            sum = sum+cost;
            i--;
            j--;
        }

        while(i>=0){
            sum += prices[i];
            i--;
        }

       
        return sum;
    }
}