class Solution {
    public boolean checkString(String s) {
        char[] chars = s.toCharArray();
        int ans = s.length();
        for(int i = 0; i<s.length();i++){
            if(chars[i] == 'b'){
                ans = i;
                break;
            }
        }
        for(int i = ans;i<s.length();i++){
            if(chars[i] == 'a'){
                return false;
            }
        }
        return true;

    }
}