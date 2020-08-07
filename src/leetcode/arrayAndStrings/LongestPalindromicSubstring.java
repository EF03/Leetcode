package leetcode.arrayAndStrings;

/**
 * @author Ron
 * @date 2020/8/7 下午 02:23
 */
public class LongestPalindromicSubstring {
    public static void main(String[] args) {
//        String param = "babad";
        String param = "";


        String result = longestPalindrome(param);
        System.out.println(result);
    }


    public static String longestPalindrome(String s) {

        if (s.length() == 0) return "";
        /* solution 1 */
//        int n = s.length();
//        String res = null;
//
//        boolean[][] dp = new boolean[n][n];
//
//        for (int i = n - 1; i >= 0; i--) {
//            for (int j = i; j < n; j++) {
//                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]);
//
//                if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
//                    res = s.substring(i, j + 1);
//                }
//            }
//        }
//
//        return res;


        /* solution 1 with desc */
        int n = s.length();
//        String res = null;
        int palindromeStartsAt = 0, maxLen = 0;

        boolean[][] dp = new boolean[n][n];
        // dp[i][j] indicates whether substring s starting at index i and ending at j is palindrome

        /*     i            "babad"  aba
      j  * [ "true" , false  , "true" , false  , false   ]
         * [ false  , "true" , false  , "true" , false   ]
         * [ false  , false  , "true" , false  , false   ]
         * [ false  , false  , false  , "true" , false   ]
         * [ false  , false  , false  , false  , "true"  ]
         *
         * */

        for (int i = n - 1; i >= 0; i--) { // keep increasing the possible palindrome string
            for (int j = i; j < n; j++) { // find the max palindrome within this window of (i,j)

                //check if substring between (i,j) is palindrome
                dp[i][j] = (s.charAt(i) == s.charAt(j)) // chars at i and j should match
                        &&
                        (j - i < 3  // if window is less than or equal to 3, just end chars should match
                                || dp[i + 1][j - 1]); // if window is > 3, substring (i+1, j-1) should be palindrome too

                //update max palindrome string
                if (dp[i][j] && (j - i + 1 > maxLen)) {
                    palindromeStartsAt = i;
                    maxLen = j - i + 1;
                }
            }
        }

        for (int i = 0; i < dp[0].length; i++) {
            for (int j = 0; j < dp.length; j++) {
                System.out.print(dp[i][j] + ",");
            }
            System.out.println();
        }
        return s.substring(palindromeStartsAt, palindromeStartsAt + maxLen);

        /* solution 2 */

//        int len = s.length();
//        if (len < 2)
//            return s;
//
//        for (int i = 0; i < len - 1; i++) {
//            extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
//            extendPalindrome(s, i, i + 1); //assume even length.
//        }
//        return s.substring(lo, lo + maxLen);
    }

//    private static int lo, maxLen;
//
//    private static void extendPalindrome(String s, int j, int k) {
//
//
//        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
//            j--;
//            k++;
//        }
//        if (maxLen < k - j - 1) {
//            lo = j + 1;
//            maxLen = k - j - 1;
//        }
//    }


}


