package leetcode.arrayAndStrings;

/**
 * @author Ron
 * @date 2020/8/7 下午 03:17
 */
public class IncreasingTripletSubsequence {

    public static void main(String[] args) {
//        int[] param = {1, 2, 3, 4, 5};
//        int[] param = {5,4,3,2,1};
        int[] param = {2, 1, 5, 0, 4, 6};

        System.out.println(increasingTriplet(param));

    }


    public static boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) { small = n; } // update small if n is smaller than both
            else if (n <= big) { big = n; } // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
            // small < big < (result>>true)
        }
        return false;
    }
}
