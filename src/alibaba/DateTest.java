package alibaba;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author Ron
 * @date 2020/8/13 下午 04:43
 */
public class DateTest {
    /*
     *    3. 获取当前毫秒数：System.currentTimeMillis(); 而不是new Date().getTime()
     *    如果想获取更加精确的纳秒级时间值，使用System.nanoTime的方式。在JDK8中，针对统计时间等场景，推荐使用Instant类
     */

    /*
     * 不允许在程序任何地方中使用：
     *
     *   1）java.sql.Date。
     *   2）java.sql.Time。
     *   3）java.sql.Timestamp
     *
     * */

    /*
     *   不要在程序中写死一年为365天，避免在公历闰年时出现日期转换错误或程序逻辑错误
     *
     *   获取今年的天数
     *   获取指定某年的天数
     *
     * */

    int daysOfThisYear = LocalDate.now().lengthOfYear();
    int endOfCentury = LocalDate.of(2011, 12, 31).lengthOfYear();

    /** 使用枚举值来指代月份。如果使用数字，注意Date，Calendar等日期相关类的月份month取值在0-11之间 */
    int[] months = {Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH};

}
