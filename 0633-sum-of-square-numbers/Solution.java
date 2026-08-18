class Solution {
    public boolean judgeSquareSum(int c) {
        long j = (long) Math.abs(Math.sqrt(c));
        long i = 0;
        
        while(i<=j){
            if(i*i + j*j > c){
                j--;
            }else if(i*i + j*j < c){
                i++;
            }else{
                return true;
            }
        }
        return false;
    }
}