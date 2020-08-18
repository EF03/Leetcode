package alibaba;

import java.math.BigDecimal;

/**
 * @author Ron
 * @date 2020/8/13 下午 04:03
 */
public class NumberTest {
    public static void main(String[] args) {

        /* 9 浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用equals来判断 */
        // Ex.1
        float a = 1.0F - 0.9F;
        float b = 0.9F - 0.8F;
        float diff = 1e-6F;
        if (Math.abs(a - b) < diff) {
            System.out.println("true");
        }

        // Ex.2
        // equals()方法会比较值和精度（1.0与1.00返回结果为false），而compareTo()则会忽略精度
        BigDecimal c = new BigDecimal("1.0");
        BigDecimal d = new BigDecimal("0.9");
        BigDecimal e = new BigDecimal("0.8");
        BigDecimal x = c.subtract(d);
        BigDecimal y = d.subtract(e);
        if (x.compareTo(y) == 0) {
            System.out.println("true");
        }

        /* 13 禁止使用构造方法BigDecimal(double)的方式把double值转化为BigDecimal对象 */
        /*
         *
         * BigDecimal(double)存在精度损失风险，在精确计算或值比较的场景中可能会导致业务逻辑异常
         * BigDecimal g = new BigDecimal(0.1F); 实际的存储值为：0.10000000149
         * 优先推荐入参为String的构造方法，或使用BigDecimal的valueOf方法，此方法内部其实执行了Double的toString，
         * 而Double的toString按double的实际能表达的精度对尾数进行了截断
         *
         * */
        BigDecimal recommend1 = new BigDecimal("0.1");
        BigDecimal recommend2 = BigDecimal.valueOf(0.1);
    }
}
