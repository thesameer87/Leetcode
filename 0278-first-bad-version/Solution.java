/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int l = 1;
        int h = n;
        while(l<h){
            int mid = l +(h-l)/2;
            boolean isBadVersion = isBadVersion(mid);
            
            if(isBadVersion(mid) == false) l = mid+1;
            else h = mid;
        }
        return l;
    }
}