package leetcode.arrayAndStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ron
 * @date 2020/8/7 上午 10:00
 */
public class Sum3 {


    public static void main(String[] args) {

        int[] param = {-1, 0, 1, 2, -1, -4};

        List<List<Integer>> result = threeSum(param);
        System.out.println(result);

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // 从小到大
        for (int i = 0; i + 2 < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {              // skip same result  防止重复
                continue;
            }
            int j = i + 1, k = nums.length - 1; // i => 小 , k => 大 , j => i-k之间
            int target = -nums[i];
            while (j < k) {
                if (nums[j] + nums[k] == target) {
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                    while (j < k && nums[j] == nums[j - 1]) j++;  // skip same result
                    while (j < k && nums[k] == nums[k + 1]) k--;  // skip same result
                } else if (nums[j] + nums[k] > target) {
                    k--; // 比目标大 最大 小一号
                } else {
                    j++; // 比目标小 最小 大一号
                }
            }
        }
        return res;

    }
}
