package em;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Ron
 * @date 2020/8/18 下午 03:09
 */
public class Slot {
    public static void main(String[] args) {
        Integer[] integers = new Integer[10000];
        Random randomGenerator = new Random();

        Character[] characters1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        Character[] characters2 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        Character[] characters3 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        Character[] characters4 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//        Character[] characters5 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        Character[] characters5 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};





//        String characters1Str = Arrays.toString(characters5);
//
//        System.out.println("characters1 = " + characters1Str);
//
//        int[] arr = intArrayStrToArray(characters1Str);
//
//        for (int i : arr) {
//            System.out.print(i);
//        }
//        System.out.println();

//        Character[] characters2 = {'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};
//        Character[] characters3 = {'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@', '#', '$'};
//        Character[] characters4 = {'%', '^', '&', '*', '(', ')', '_', '+', '=', '-'};
//        Character[] characters5 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        List<Character[]> wheelList = new ArrayList<>(5);
        wheelList.add(characters1);
        wheelList.add(characters2);
        wheelList.add(characters3);
        wheelList.add(characters4);
        wheelList.add(characters5);




        Character[][] rewardArray = buildReward(wheelList);


        for (Character[] array : rewardArray) {
            for (Character character : array) {
                System.out.print(character);
            }
            System.out.println();
        }



    }

    private static Character[][] buildReward(List<Character[]> wheelList) {
        Random randomGenerator = new Random();
        Character[][] flatArray = new Character[3][5];
        Character[][] flatRandomArray = new Character[5][3];

        // 塞值
        for (int i = 0; i < flatRandomArray.length; i++) {
            for (int j = 0; j < flatRandomArray[i].length; j++) {
                Character[] characters = wheelList.get(i);
                int index = randomGenerator.nextInt(characters.length);
                flatRandomArray[i][j] = characters[index];
            }
        }

        // 陣列轉換

        for (int i = 0; i < flatRandomArray.length; i++) {
            for (int j = 0; j < flatRandomArray[i].length; j++) {
                flatArray[j][i] = flatRandomArray[i][j];
            }
        }

        return flatArray;
    }

    private static int[] intArrayStrToArray(String arrayStr) {
        return Stream.of(arrayStr.replaceAll("[\\[\\]\\, ]", "").split("")).mapToInt(Integer::parseInt).toArray();
    }
}
