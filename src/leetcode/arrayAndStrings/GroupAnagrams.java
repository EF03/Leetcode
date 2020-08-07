package leetcode.arrayAndStrings;

import java.util.*;

/**
 * @author Ron
 * @date 2020/8/7 上午 10:53
 */
public class GroupAnagrams {
    public static void main(String[] args) {

        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(input);
        System.out.println(result);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<>(map.values());


//        List<List<String>> result = new ArrayList<>();
//        List<Set<Character>> setList = new ArrayList<>();
//        for (String string : strs) {
//            Set<Character> charSet = new HashSet<>();
//            for (int i = 0; i < string.length(); i++) {
//                char c = string.charAt(i);
//                charSet.add(c);
//            }
//            if (!setList.contains(charSet)) {
//                setList.add(charSet);
//            }
//            for (int j = 0; j < setList.size(); j++) {
//                if (result.size() < setList.size()) {
//                    result.add(new ArrayList<>());
//                }
//                if (setList.get(j).equals(charSet)) {
//                    result.get(j).add(string);
//                }
//            }
//        }
//        return result;
    }
}
