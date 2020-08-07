package leetcode.arrayAndStrings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ron
 * @date 2020/8/7 上午 11:32
 */
public class LongestSubstring {
    public static void main(String[] args) {
//        String param = "abcabcbb";
//        String param = "bbbbb";
//        String param = "pwwkew";
//        String param = " ";
//        String param = "au";
        String param = "aab";


        System.out.println(lengthOfLongestSubstring(param));
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);// 记录每个字母的起始位置
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);//下标 + 1 代表 i 要移动的下个位置
        }
        return ans;
    }
}
