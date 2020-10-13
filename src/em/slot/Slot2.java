package em.slot;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Ron
 * @date 2020/8/18 下午 03:09
 * 243路
 */
public class Slot2 {
    public static void main(String[] args) {

        List<int[]> wheelList = gernerateWhellList();

        getProbability(wheelList);

        //BigDecimal 格式化工具    保留两位小数
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(4);

        // init end
        System.out.println("=================================");
        // 第幾個獎  有幾條連線
//        List<MatrixLocation> rewardList = new ArrayList<>(50);
        String[][] statisticsRewardMatrix = new String[10][3];
        /*
         * 10W   => Total time =  0  秒
         * 100W  => Total time =  2  秒
         * 1000W => Total time =  10  秒
         * 1E    => Total time =  105 秒
         *
         * */
        final int loop = 1000000;
        long loopStart = System.currentTimeMillis();
        boolean isPercentage = true;
        for (int z = 0; z < loop; z++) {
//            rewardList.clear();

            Map<String, Object> buildRewardMatrixMap = buildRewardMatrix(wheelList);
            int[][] rewardArray = (int[][]) buildRewardMatrixMap.get("matrix");
            int[] firstArray = (int[]) buildRewardMatrixMap.get("firstArray");

            // 印出本次 Matrix
//            System.out.println("====================rewardArray =========================");
//            printMatrix(rewardArray);

            int[][] transMatrix = transMatrix(firstArray, rewardArray);
//            System.out.println("transMatrix =========================");
//            printMatrix(transMatrix);

            int[][] countMatrix = countMatrix(transMatrix);
//            System.out.println("countMatrix =========================");
//            printMatrix(countMatrix);

            statisticsRewardMatrix = statisticsRewardMatrix(statisticsRewardMatrix, countMatrix, firstArray);

        }

        System.out.println("Total time = " + (System.currentTimeMillis() - loopStart) / 1000 + " 秒");

        for (int i = 0; i < statisticsRewardMatrix.length; i++) {
            for (int j = 0; j < statisticsRewardMatrix[i].length; j++) {

                String times = statisticsRewardMatrix[i][j];

                if (times == null) {
                    times = "0";
                }
                if (isPercentage) {
                    //除法结果保留4位小数，
                    double per = new BigDecimal(Float.parseFloat(times) / loop).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //格式化为百分比字符串（自带百分号）
                    String Ratio = percent.format(per);
                    statisticsRewardMatrix[i][j] = Ratio;
                } else {
                    statisticsRewardMatrix[i][j] = times;
                }
            }
        }
        printMatrix(statisticsRewardMatrix);

    }

    public static void getProbability(List<int[]> wheelList) {

        // BigDecimal 格式化工具    保留两位小数
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(4);


        String[][] result = new String[10][3];
        BigDecimal[] totalSize = new BigDecimal[5];
        BigDecimal[] probabilityNum = new BigDecimal[3];

        for (int i = 0; i < wheelList.size(); i++) {
            totalSize[i] = BigDecimal.valueOf(wheelList.get(i).length);
        }
        probabilityNum[0] = totalSize[0].multiply(totalSize[1]).multiply(totalSize[2]);
        probabilityNum[1] = totalSize[0].multiply(totalSize[1]).multiply(totalSize[2]).multiply(totalSize[3]);
        probabilityNum[2] = totalSize[0].multiply(totalSize[1]).multiply(totalSize[2]).multiply(totalSize[3]).multiply(totalSize[4]);


        for (int i = 0; i < result.length; i++) {
            int z = i;
            int firstWheel = (int) Arrays.stream(wheelList.get(0)).filter(e -> e == z).count();
            int second = (int) Arrays.stream(wheelList.get(1)).filter(e -> e == z).count();
            int third = (int) Arrays.stream(wheelList.get(2)).filter(e -> e == z).count();
            int fourth = (int) Arrays.stream(wheelList.get(3)).filter(e -> e == z).count();
            int fifth = (int) Arrays.stream(wheelList.get(4)).filter(e -> e == z).count();
            BigDecimal cut4 = totalSize[3].subtract(new BigDecimal(fourth)).divide(totalSize[3], 10, BigDecimal.ROUND_HALF_UP).pow(3);
            BigDecimal cut5 = totalSize[4].subtract(new BigDecimal(fifth)).divide(totalSize[4], 10, BigDecimal.ROUND_HALF_UP).pow(3);
            BigDecimal per = new BigDecimal("0");
            String Ratio;
//            float product;
            BigDecimal product1;
            BigDecimal product2;
            BigDecimal totalNum3 = BigDecimal.valueOf(firstWheel * second * third * 27);
            BigDecimal totalNum4 = BigDecimal.valueOf(firstWheel * second * third * fourth * 81);
            BigDecimal totalNum5 = BigDecimal.valueOf(firstWheel * second * third * fourth * fifth * 243);
            for (int j = 0; j < result[i].length; j++) {
                switch (j) {
                    case 0:
                        //除法结果保留4位小数，
                        product1 = (totalNum3.divide(probabilityNum[j], 10, BigDecimal.ROUND_HALF_UP)).multiply(cut4);
//                        product2 = (totalNum4.divide(probabilityNum[j + 1], 10, BigDecimal.ROUND_HALF_UP));
//                        per = product1.subtract(product2);
                        per = product1.subtract(BigDecimal.valueOf(0));
                        break;
                    case 1:
                        product1 = (totalNum4.divide(probabilityNum[j], 10, BigDecimal.ROUND_HALF_UP)).multiply(cut5);

//                        product2 = (totalNum5.divide(probabilityNum[j + 1], 10, BigDecimal.ROUND_HALF_UP));
//                        per = product1.subtract(product2);

                        per = product1.subtract(BigDecimal.valueOf(0));
                        break;
                    case 2:
                        per = (totalNum5.divide(probabilityNum[j], 10, BigDecimal.ROUND_HALF_UP));
                        break;
                    default:
                        break;
                }
                //格式化为百分比字符串（自带百分号）
                Ratio = percent.format(per);
                result[i][j] = Ratio;
            }
        }
        System.out.println(matrixArrayToString(result));
    }

    public static String matrixArrayToString(String[][] array) {
        return Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
    }

    private static List<int[]> gernerateWhellList() {
        Gson gson = new Gson();
        RollerSetting rollerSetting1 = new RollerSetting(1, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999);
        RollerSetting rollerSetting2 = new RollerSetting(2, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999);
        RollerSetting rollerSetting3 = new RollerSetting(3, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999);
        RollerSetting rollerSetting4 = new RollerSetting(4, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999);
        RollerSetting rollerSetting5 = new RollerSetting(5, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999, 99999);

//        RollerSetting rollerSetting1 = new RollerSetting(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
//        RollerSetting rollerSetting2 = new RollerSetting(2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
//        RollerSetting rollerSetting3 = new RollerSetting(3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
//        RollerSetting rollerSetting4 = new RollerSetting(4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
//        RollerSetting rollerSetting5 = new RollerSetting(5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);


        List<RollerSetting> listRollerSetting = new ArrayList<>();
        listRollerSetting.add(rollerSetting5);
        listRollerSetting.add(rollerSetting3);
        listRollerSetting.add(rollerSetting4);
        listRollerSetting.add(rollerSetting2);
        listRollerSetting.add(rollerSetting1);

        // sql 下 order可省略
        List<RollerSetting> listRollerSettingSort = listRollerSetting.stream().sorted(Comparator.comparing(RollerSetting::getId)).collect(Collectors.toList());

        List<int[]> wheelList = new ArrayList<>(5);

        for (RollerSetting rollerSetting : listRollerSettingSort) {

            int reward0 = rollerSetting.getReward0();
            int reward1 = rollerSetting.getReward1();
            int reward2 = rollerSetting.getReward2();
            int reward3 = rollerSetting.getReward3();
            int reward4 = rollerSetting.getReward4();
            int reward5 = rollerSetting.getReward5();
            int reward6 = rollerSetting.getReward6();
            int reward7 = rollerSetting.getReward7();
            int reward8 = rollerSetting.getReward8();
            int reward9 = rollerSetting.getReward9();

            int total = reward0 + reward1 + reward2 + reward3 + reward4 + reward5 + reward6 + reward7 + reward8 + reward9;
            int[] wheelArray = new int[total];

            int count = 0;
            IntStream.range(count, count + reward0).forEach(i -> wheelArray[i] = 0);
            count += reward0;
            IntStream.range(count, count + reward1).forEach(i -> wheelArray[i] = 1);
            count += reward1;
            IntStream.range(count, count + reward2).forEach(i -> wheelArray[i] = 2);
            count += reward2;
            IntStream.range(count, count + reward3).forEach(i -> wheelArray[i] = 3);
            count += reward3;
            IntStream.range(count, count + reward4).forEach(i -> wheelArray[i] = 4);
            count += reward4;
            IntStream.range(count, count + reward5).forEach(i -> wheelArray[i] = 5);
            count += reward5;
            IntStream.range(count, count + reward6).forEach(i -> wheelArray[i] = 6);
            count += reward6;
            IntStream.range(count, count + reward7).forEach(i -> wheelArray[i] = 7);
            count += reward7;
            IntStream.range(count, count + reward8).forEach(i -> wheelArray[i] = 8);
            count += reward8;
            IntStream.range(count, count + reward9).forEach(i -> wheelArray[i] = 9);

            System.out.println(gson.toJson(wheelArray));
            wheelList.add(wheelArray);
        }

        return wheelList;
    }

    private static String[][] statisticsRewardMatrix(String[][] statisticsRewardMatrix, int[][] countMatrix, int[] firstArray) {

        for (int i = 0; i < countMatrix.length; i++) {
            for (int j = 0; j < countMatrix[i].length; j++) {

                int origin = strParseInt(statisticsRewardMatrix[firstArray[i]][j]);
                statisticsRewardMatrix[firstArray[i]][j] = String.valueOf(countMatrix[i][j] + origin);

            }
        }
        return statisticsRewardMatrix;
    }

    private static int[][] countMatrix(int[][] transMatrix) {
        /*
         *  每一个字母（id）对应的 3 4 5 连线
         *        id
         *          A    B    C
         *   3
         *   4
         *   5
         *
         * */

        int[][] countMatrix = new int[3][3];

        for (int i = 0; i < countMatrix.length; i++) {
            int count3 = transMatrix[i][0] * transMatrix[i][1];
            int count4 = transMatrix[i][0] * transMatrix[i][1] * transMatrix[i][2];
            int count5 = transMatrix[i][0] * transMatrix[i][1] * transMatrix[i][2] * transMatrix[i][3];
            for (int j = 0; j < countMatrix[i].length; j++) {
                switch (j) {
                    case 0:
                        countMatrix[i][j] = Math.max((count3 - count4 - count5), 0);
                        break;
                    case 1:
                        countMatrix[i][j] = Math.max((count4 - count5), 0);
                        break;
                    case 2:
                        countMatrix[i][j] = Math.max((count5), 0);
                        break;
//                    case 0:
//                        countMatrix[i][j] = transMatrix[i][0] * transMatrix[i][1];
//                        break;
//                    case 1:
//                        int count4 = transMatrix[i][0] * transMatrix[i][1] * transMatrix[i][2];
//                        countMatrix[i][j] = count4;
//                        if (count4 == 0) {
//                            break;
//                        }
//                        // 三连线 = 4连线 - 3連線
//                        countMatrix[i][j - 1] = Math.max((countMatrix[i][j - 1] - count4), 0);
//                        break;
//                    case 2:
//                        int count5 = transMatrix[i][0] * transMatrix[i][1] * transMatrix[i][2] * transMatrix[i][3];
//                        countMatrix[i][j] = count5;
//                        // 三连线--
//                        if (count5 == 0) {
//                            break;
//                        }
//                        // 4连线--
//                        countMatrix[i][j - 1] = Math.max((countMatrix[i][j - 1] - count5), 0);
//                        break;
                    default:
                        break;
                }


            }
        }


        return countMatrix;

    }

    private static int[][] transMatrix(int[] firstArray, int[][] rewardArray) {

        /*  目标                             firstArray
         *  每一个字母在每个滚轮各有几个       第一个滚轮的字母分别在第几row
         *        index                                 index
         *          1     2    3    4                A    0
         *   A                                       B    1
         *   B                                       C    2
         *   C
         *
         * */
        int[][] resultMatrix = new int[3][4];

        // 243路
        for (int i = 0; i < rewardArray.length; i++) {
            for (int j = 1; j < rewardArray[i].length; j++) {

                for (int k = 0; k < firstArray.length; k++) {
                    if (firstArray[k] == rewardArray[i][j]) {
                        int count = resultMatrix[k][j - 1];
                        resultMatrix[k][j - 1] = ++count;
                    }
                }
            }
        }

        return resultMatrix;
    }


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

                System.out.printf("%10d", i);

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

    private static int strParseInt(String str) {
        return (str == null) ? 0 : Integer.parseInt(str);
    }
}
