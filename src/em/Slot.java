package em;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Ron
 * @date 2020/8/18 下午 03:09
 */
public class Slot {
    public static void main(String[] args) {
//        int[] intacters1 = {'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B'};
//        int[] intacters2 = {'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B'};
//        int[] intacters3 = {'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B'};
//        int[] intacters4 = {'A', 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B', 'B'};

//        int[] intacters1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//        int[] intacters2 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//        int[] intacters3 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//        int[] intacters4 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//        int[] intacters5 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        int[] intacters1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] intacters2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] intacters3 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] intacters4 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] intacters5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

//        int[] intacters1 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
//        int[] intacters2 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
//        int[] intacters3 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
//        int[] intacters4 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
//        int[] intacters5 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
//        int[] intacters5 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


        List<int[]> wheelList = new ArrayList<>(5);
        wheelList.add(intacters1);
        wheelList.add(intacters2);
        wheelList.add(intacters3);
        wheelList.add(intacters4);
        wheelList.add(intacters5);

        List<List<MatrixLocation>> listMatrixLocation = buildListMatrixLocation();


        //BigDecimal 格式化工具    保留两位小数
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(4);

        // init end
        System.out.println("=================================");
        // 第幾個獎  有幾條連線
        List<MatrixLocation> rewardList = new ArrayList<>(50);
        String[][] statisticsRewardMatrix = new String[10][3];
        // 10W   => Total time =   1  秒
        // 100W  => Total time =   8  秒
        // 1000W => Total time =  71  秒
        // 1E    => Total time =  秒
        final int loop = 100;
        long loopStart = System.currentTimeMillis();
        for (int z = 0; z < loop; z++) {
            rewardList.clear();

            Map<String, Object> buildRewardMatrixMap = buildRewardMatrix(wheelList);
            int[][] rewardArray = (int[][]) buildRewardMatrixMap.get("matrix");
            int[] firstArray = (int[]) buildRewardMatrixMap.get("firstArray");
            /*      index
             *   A    0
             *   B    1
             *   C    2
             *
             * */
            Map<String, Integer> firstArrayMap = new HashMap<>(3);
            for (int i = 0; i < firstArray.length; i++) {
                firstArrayMap.put(String.valueOf(firstArray[i]), i);
            }

            // 印出本次 Matrix
//            printMatrix(rewardArray);

            // 取得奖项
            for (int i = 0; i < listMatrixLocation.size(); i++) {
                MatrixLocation firstNode = listMatrixLocation.get(i).get(1);
                int lineLong = 0;
                for (int j = 0; j < listMatrixLocation.get(i).size(); j++) {
                    if (rewardArray[firstNode.getY()][firstNode.getX()] !=
                            rewardArray[listMatrixLocation.get(i).get(j).getY()][listMatrixLocation.get(i).get(j).getX()]) {
                        break;
                    }
                    lineLong++;
                }
                if (lineLong >= 3) {
                    rewardList.add(new MatrixLocation(rewardArray[firstNode.getY()][firstNode.getX()], lineLong));
                }
            }

            System.out.println(rewardList);

            /*       3   4   5
             *   A
             *   B
             *   C
             *
             * */
            int[][] statisticsReward = new int[3][3];
            for (MatrixLocation node : rewardList) {
                int lineLong = node.getY() - 3;
                statisticsReward[firstArrayMap.get(String.valueOf(node.getX()))][lineLong] = ++statisticsReward[firstArrayMap.get(String.valueOf(node.getX()))][lineLong];

                String count = statisticsRewardMatrix[Integer.parseInt(String.valueOf(node.getX()))][lineLong];
                if (count == null) {
                    count = "0";
                }
                int countInt = Integer.parseInt(count);
                statisticsRewardMatrix[Integer.parseInt(String.valueOf(node.getX()))][lineLong] = String.valueOf(++countInt);
            }
//            printMatrix(statisticsReward);
        }
        long loopEnd = System.currentTimeMillis();
        System.out.println("Total time = " + (loopEnd - loopStart) / 1000 + " 秒");


        for (int i = 0; i < statisticsRewardMatrix.length; i++) {
            for (int j = 0; j < statisticsRewardMatrix[i].length; j++) {

                String times = statisticsRewardMatrix[i][j];

//                if (times == null) {
////                    times = "0";
////                }
////
////                //除法结果保留4位小数，
////                double per = new BigDecimal(Float.parseFloat(times) / loop).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
////                //格式化为百分比字符串（自带百分号）
////                String Ratio = percent.format(per);
////                statisticsRewardMatrix[i][j] = Ratio;
                statisticsRewardMatrix[i][j] = times;
            }
        }

        printMatrix(statisticsRewardMatrix);
//        System.out.println(sta);


    }

    private static List<List<MatrixLocation>> buildListMatrixLocation() {

        List<MatrixLocation> matrixLocations1 = new ArrayList<>(5);
        matrixLocations1.add(new MatrixLocation(0, 1));
        matrixLocations1.add(new MatrixLocation(1, 1));
        matrixLocations1.add(new MatrixLocation(2, 1));
        matrixLocations1.add(new MatrixLocation(3, 1));
        matrixLocations1.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations2 = new ArrayList<>(5);
        matrixLocations2.add(new MatrixLocation(0, 0));
        matrixLocations2.add(new MatrixLocation(1, 0));
        matrixLocations2.add(new MatrixLocation(2, 0));
        matrixLocations2.add(new MatrixLocation(3, 0));
        matrixLocations2.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations3 = new ArrayList<>(5);
        matrixLocations3.add(new MatrixLocation(0, 2));
        matrixLocations3.add(new MatrixLocation(1, 2));
        matrixLocations3.add(new MatrixLocation(2, 2));
        matrixLocations3.add(new MatrixLocation(3, 2));
        matrixLocations3.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations4 = new ArrayList<>(5);
        matrixLocations4.add(new MatrixLocation(0, 0));
        matrixLocations4.add(new MatrixLocation(1, 1));
        matrixLocations4.add(new MatrixLocation(2, 2));
        matrixLocations4.add(new MatrixLocation(3, 1));
        matrixLocations4.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations5 = new ArrayList<>(5);
        matrixLocations5.add(new MatrixLocation(0, 2));
        matrixLocations5.add(new MatrixLocation(1, 1));
        matrixLocations5.add(new MatrixLocation(2, 0));
        matrixLocations5.add(new MatrixLocation(3, 1));
        matrixLocations5.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations6 = new ArrayList<>(5);
        matrixLocations6.add(new MatrixLocation(0, 1));
        matrixLocations6.add(new MatrixLocation(1, 0));
        matrixLocations6.add(new MatrixLocation(2, 0));
        matrixLocations6.add(new MatrixLocation(3, 0));
        matrixLocations6.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations7 = new ArrayList<>(5);
        matrixLocations7.add(new MatrixLocation(0, 1));
        matrixLocations7.add(new MatrixLocation(1, 0));
        matrixLocations7.add(new MatrixLocation(2, 0));
        matrixLocations7.add(new MatrixLocation(3, 0));
        matrixLocations7.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations8 = new ArrayList<>(5);
        matrixLocations8.add(new MatrixLocation(0, 1));
        matrixLocations8.add(new MatrixLocation(1, 0));
        matrixLocations8.add(new MatrixLocation(2, 0));
        matrixLocations8.add(new MatrixLocation(3, 0));
        matrixLocations8.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations9 = new ArrayList<>(5);
        matrixLocations9.add(new MatrixLocation(0, 1));
        matrixLocations9.add(new MatrixLocation(1, 0));
        matrixLocations9.add(new MatrixLocation(2, 0));
        matrixLocations9.add(new MatrixLocation(3, 0));
        matrixLocations9.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations10 = new ArrayList<>(5);
        matrixLocations10.add(new MatrixLocation(0, 1));
        matrixLocations10.add(new MatrixLocation(1, 0));
        matrixLocations10.add(new MatrixLocation(2, 0));
        matrixLocations10.add(new MatrixLocation(3, 0));
        matrixLocations10.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations11 = new ArrayList<>(5);
        matrixLocations11.add(new MatrixLocation(0, 1));
        matrixLocations11.add(new MatrixLocation(1, 0));
        matrixLocations11.add(new MatrixLocation(2, 1));
        matrixLocations11.add(new MatrixLocation(3, 2));
        matrixLocations11.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations12 = new ArrayList<>(5);
        matrixLocations12.add(new MatrixLocation(0, 0));
        matrixLocations12.add(new MatrixLocation(1, 1));
        matrixLocations12.add(new MatrixLocation(2, 1));
        matrixLocations12.add(new MatrixLocation(3, 1));
        matrixLocations12.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations13 = new ArrayList<>(5);
        matrixLocations13.add(new MatrixLocation(0, 2));
        matrixLocations13.add(new MatrixLocation(1, 1));
        matrixLocations13.add(new MatrixLocation(2, 1));
        matrixLocations13.add(new MatrixLocation(3, 1));
        matrixLocations13.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations14 = new ArrayList<>(5);
        matrixLocations14.add(new MatrixLocation(0, 0));
        matrixLocations14.add(new MatrixLocation(1, 1));
        matrixLocations14.add(new MatrixLocation(2, 0));
        matrixLocations14.add(new MatrixLocation(3, 1));
        matrixLocations14.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations15 = new ArrayList<>(5);
        matrixLocations15.add(new MatrixLocation(0, 2));
        matrixLocations15.add(new MatrixLocation(1, 1));
        matrixLocations15.add(new MatrixLocation(2, 2));
        matrixLocations15.add(new MatrixLocation(3, 1));
        matrixLocations15.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations16 = new ArrayList<>(5);
        matrixLocations16.add(new MatrixLocation(0, 1));
        matrixLocations16.add(new MatrixLocation(1, 1));
        matrixLocations16.add(new MatrixLocation(2, 0));
        matrixLocations16.add(new MatrixLocation(3, 1));
        matrixLocations16.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations17 = new ArrayList<>(5);
        matrixLocations17.add(new MatrixLocation(0, 1));
        matrixLocations17.add(new MatrixLocation(1, 1));
        matrixLocations17.add(new MatrixLocation(2, 2));
        matrixLocations17.add(new MatrixLocation(3, 1));
        matrixLocations17.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations18 = new ArrayList<>(5);
        matrixLocations18.add(new MatrixLocation(0, 0));
        matrixLocations18.add(new MatrixLocation(1, 0));
        matrixLocations18.add(new MatrixLocation(2, 2));
        matrixLocations18.add(new MatrixLocation(3, 0));
        matrixLocations18.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations19 = new ArrayList<>(5);
        matrixLocations19.add(new MatrixLocation(0, 2));
        matrixLocations19.add(new MatrixLocation(1, 2));
        matrixLocations19.add(new MatrixLocation(2, 0));
        matrixLocations19.add(new MatrixLocation(3, 2));
        matrixLocations19.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations20 = new ArrayList<>(5);
        matrixLocations20.add(new MatrixLocation(0, 0));
        matrixLocations20.add(new MatrixLocation(1, 2));
        matrixLocations20.add(new MatrixLocation(2, 2));
        matrixLocations20.add(new MatrixLocation(3, 2));
        matrixLocations20.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations21 = new ArrayList<>(5);
        matrixLocations21.add(new MatrixLocation(0, 2));
        matrixLocations21.add(new MatrixLocation(1, 0));
        matrixLocations21.add(new MatrixLocation(2, 0));
        matrixLocations21.add(new MatrixLocation(3, 0));
        matrixLocations21.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations22 = new ArrayList<>(5);
        matrixLocations22.add(new MatrixLocation(0, 1));
        matrixLocations22.add(new MatrixLocation(1, 2));
        matrixLocations22.add(new MatrixLocation(2, 0));
        matrixLocations22.add(new MatrixLocation(3, 2));
        matrixLocations22.add(new MatrixLocation(4, 1));

        List<MatrixLocation> matrixLocations23 = new ArrayList<>(5);
        matrixLocations23.add(new MatrixLocation(0, 1));
        matrixLocations23.add(new MatrixLocation(1, 0));
        matrixLocations23.add(new MatrixLocation(2, 2));
        matrixLocations23.add(new MatrixLocation(3, 1));
        matrixLocations23.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations24 = new ArrayList<>(5);
        matrixLocations24.add(new MatrixLocation(0, 0));
        matrixLocations24.add(new MatrixLocation(1, 2));
        matrixLocations24.add(new MatrixLocation(2, 0));
        matrixLocations24.add(new MatrixLocation(3, 2));
        matrixLocations24.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations25 = new ArrayList<>(5);
        matrixLocations25.add(new MatrixLocation(0, 2));
        matrixLocations25.add(new MatrixLocation(1, 0));
        matrixLocations25.add(new MatrixLocation(2, 2));
        matrixLocations25.add(new MatrixLocation(3, 0));
        matrixLocations25.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations26 = new ArrayList<>(5);
        matrixLocations26.add(new MatrixLocation(0, 2));
        matrixLocations26.add(new MatrixLocation(1, 0));
        matrixLocations26.add(new MatrixLocation(2, 1));
        matrixLocations26.add(new MatrixLocation(3, 2));
        matrixLocations26.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations27 = new ArrayList<>(5);
        matrixLocations27.add(new MatrixLocation(0, 0));
        matrixLocations27.add(new MatrixLocation(1, 2));
        matrixLocations27.add(new MatrixLocation(2, 1));
        matrixLocations27.add(new MatrixLocation(3, 0));
        matrixLocations27.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations28 = new ArrayList<>(5);
        matrixLocations28.add(new MatrixLocation(0, 0));
        matrixLocations28.add(new MatrixLocation(1, 2));
        matrixLocations28.add(new MatrixLocation(2, 1));
        matrixLocations28.add(new MatrixLocation(3, 2));
        matrixLocations28.add(new MatrixLocation(4, 0));

        List<MatrixLocation> matrixLocations29 = new ArrayList<>(5);
        matrixLocations29.add(new MatrixLocation(0, 2));
        matrixLocations29.add(new MatrixLocation(1, 0));
        matrixLocations29.add(new MatrixLocation(2, 1));
        matrixLocations29.add(new MatrixLocation(3, 0));
        matrixLocations29.add(new MatrixLocation(4, 2));

        List<MatrixLocation> matrixLocations30 = new ArrayList<>(5);
        matrixLocations30.add(new MatrixLocation(0, 2));
        matrixLocations30.add(new MatrixLocation(1, 1));
        matrixLocations30.add(new MatrixLocation(2, 0));
        matrixLocations30.add(new MatrixLocation(3, 0));
        matrixLocations30.add(new MatrixLocation(4, 1));


        List<List<MatrixLocation>> listMatrixLocation = new ArrayList<>(50);
        listMatrixLocation.add(matrixLocations1);
        listMatrixLocation.add(matrixLocations2);
        listMatrixLocation.add(matrixLocations3);
        listMatrixLocation.add(matrixLocations5);
        listMatrixLocation.add(matrixLocations6);
        listMatrixLocation.add(matrixLocations7);
        listMatrixLocation.add(matrixLocations8);
        listMatrixLocation.add(matrixLocations9);
        listMatrixLocation.add(matrixLocations10);
        listMatrixLocation.add(matrixLocations11);
        listMatrixLocation.add(matrixLocations12);
        listMatrixLocation.add(matrixLocations13);
        listMatrixLocation.add(matrixLocations14);
        listMatrixLocation.add(matrixLocations15);
        listMatrixLocation.add(matrixLocations16);
        listMatrixLocation.add(matrixLocations17);
        listMatrixLocation.add(matrixLocations18);
        listMatrixLocation.add(matrixLocations19);
        listMatrixLocation.add(matrixLocations20);
        listMatrixLocation.add(matrixLocations21);
        listMatrixLocation.add(matrixLocations22);
        listMatrixLocation.add(matrixLocations23);
        listMatrixLocation.add(matrixLocations24);
        listMatrixLocation.add(matrixLocations25);
        listMatrixLocation.add(matrixLocations26);
        listMatrixLocation.add(matrixLocations27);
        listMatrixLocation.add(matrixLocations28);
        listMatrixLocation.add(matrixLocations29);
        listMatrixLocation.add(matrixLocations30);

        return listMatrixLocation;
    }

//    public static Map<String, Integer> sta = new HashMap<>();

    private static Map<String, Object> buildRewardMatrix(List<int[]> wheelList) {
        Map<String, Object> result = new HashMap<>(2);
        Random randomGenerator = new Random();
        int[][] matrix = new int[3][5];
        int[][] randomMatrix = new int[5][3];

        // 纪录第一列
        int[] firstArray = new int[3];

        // 塞值
        for (int i = 0; i < randomMatrix.length; i++) {
            for (int j = 0; j < randomMatrix[i].length; j++) {
                int[] intacters = wheelList.get(i);
                int index = randomGenerator.nextInt(intacters.length);
                if (i == 0) {
                    firstArray[j] = intacters[index];
                }
                randomMatrix[i][j] = intacters[index];
//                if (sta.get(String.valueOf(intacters[index])) == null) {
//                    sta.put(String.valueOf(intacters[index]), 1);
//                } else {
//                    int num = sta.get(String.valueOf(intacters[index]));
//                    sta.put(String.valueOf(intacters[index]), ++num);
//                }
            }
        }

        // 陣列轉換
        for (int i = 0; i < randomMatrix.length; i++) {
            for (int j = 0; j < randomMatrix[i].length; j++) {
                matrix[j][i] = randomMatrix[i][j];
            }
        }

        result.put("firstArray", firstArray);
        result.put("matrix", matrix);

        return result;
    }

    private static void printMatrix(int[][] buildTransMatrix) {

        for (int[] array : buildTransMatrix) {
            for (int i : array) {

                System.out.printf("%4d", i);

            }
            System.out.println();
        }
    }

    private static void printMatrix(String[][] buildTransMatrix) {

        for (String[] array : buildTransMatrix) {
            for (String i : array) {

                System.out.printf("%10s", i);

            }
            System.out.println();
        }
    }


    private static int[] intArrayStrToArray(String arrayStr) {
        return Stream.of(arrayStr.replaceAll("[\\[\\]\\, ]", "").split("")).mapToInt(Integer::parseInt).toArray();
    }
}
