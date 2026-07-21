class Solution {
    public boolean canReach(int[] start, int[] target) {
        int x1 = start[0],y1 = start[1];
        int x2 = target[0], y2 = target[1];
        int ans = Math.abs(x1-x2) + Math.abs(y1-y2);
        if(ans%2 == 0){
            return true;
        }else{
            return false;
        }
    }
}