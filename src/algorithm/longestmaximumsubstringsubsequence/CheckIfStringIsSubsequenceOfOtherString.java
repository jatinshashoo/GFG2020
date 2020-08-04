package algorithm.longestmaximumsubstringsubsequence;

public class CheckIfStringIsSubsequenceOfOtherString {
    public static void main(String[] args) {
        String word = "abcde";
        String pat = "ace";
        System.out.println(checkIfStringIsSubsequenceOfOtherString(word, pat));
    }

    private static boolean checkIfStringIsSubsequenceOfOtherString(String input, String patttern) {
        char[] word = input.toCharArray();
        char[] pat = patttern.toCharArray();

        int i = 0; int n = pat.length;
        int j = 0;
        while(i < input.length()){
            if(word[i] == pat[j]){
                j++;
            }
            i++;
        }
        return j == patttern.length();
    }
}