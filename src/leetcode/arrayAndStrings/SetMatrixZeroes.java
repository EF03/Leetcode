package leetcode.arrayAndStrings;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ron
 * @date 2020/8/7 上午 10:12
 */
public class SetMatrixZeroes {

    public static void main(String[] args) {
//        int[][] matrix = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] matrix = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        setZeroes(matrix);

        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            System.out.println();
        }

    }

    public static void setZeroes(int[][] matrix) {

        Set<Integer> row = new HashSet<>();
        Set<Integer> column = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    column.add(j);
                    row.add(i);
                }
            }
        }


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (row.contains(i) || column.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }


    }
}
