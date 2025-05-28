public class Q34_ReverseVowelsOfString {
    public static String reverseVowels(String s) {
        char[] arr = s.toCharArray();
        String vowels = "aeiouAEIOU";
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && vowels.indexOf(arr[i]) == -1) i++;
            while (i < j && vowels.indexOf(arr[j]) == -1) j--;
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++; j--;
        }
        return new String(arr);
    }

    public static void main(String[] args) {
        System.out.println(reverseVowels("hello")); // Output: holle
    }
}
