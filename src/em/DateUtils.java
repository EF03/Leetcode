package em;


import cn.hutool.core.date.DateUtil;
import org.threeten.extra.Interval;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Ron
 * @date 2020/10/10 下午 10:09
 */
public class DateUtils {
    public static DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final ZoneOffset zoneOffSet = ZoneOffset.of("+08:00");
    //    public static final ZoneId zoneId = ZoneId.of("+8");
    public static final ZoneId zoneShanghai = ZoneId.of(ZoneIdEnum.CTT.getZoneIdName());
    public static final ZoneId zoneNewYork = ZoneId.of(ZoneIdEnum.EST.getZoneIdName());


    public static void main(String[] args) {
//        String pattern = "yyyy-MM-dd HH:mm:ss";
//        String timeStr = getNowString(pattern);

//        FORMATTER_YYYY_MM_DD_HH_MM_SS.format(System.nanoTime());
        // Parsing or conversion

        Date date = new Date();
//        Instant instant = date.toInstant();
        LocalDateTime newYorkTime = convertToLocalDateTimeViaInstant(date, zoneNewYork);
        LocalDateTime shanghaiDate = convertToLocalDateTimeViaInstant(date, zoneShanghai);
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
//        LocalDate localDate = instant.atZone(zoneShanghai).toLocalDate();
        System.out.println("Date = " + date);
        System.out.println("newYorkTime = " + newYorkTime);
        System.out.println("shanghaiDate = " + shanghaiDate);
//        =====================================

        List<TimeSetting> timeSettings = new ArrayList<>();
        // 点碰触不算 重复
        TimeSetting timeSetting1 = new TimeSetting("2016-01-01 05:20:00", "2016-02-01 05:20:00");
        TimeSetting timeSetting2 = new TimeSetting("2016-01-02 05:20:00", "2016-02-05 05:20:00");
        TimeSetting timeSetting3 = new TimeSetting("2016-01-03 05:20:00", "2016-02-05 05:20:00");
        TimeSetting timeSetting4 = new TimeSetting("2016-01-04 05:20:00", "2016-02-05 05:20:00");
        TimeSetting timeSetting5 = new TimeSetting("2016-01-05 05:20:00", "2016-02-05 05:20:00");

        TimeSetting timeSetting6 = new TimeSetting("2016-02-05 05:19:00", "2016-11-01 05:20:00");

        timeSettings.add(timeSetting1);
        timeSettings.add(timeSetting2);
        timeSettings.add(timeSetting3);
        timeSettings.add(timeSetting4);
        timeSettings.add(timeSetting5);

        boolean isOverlaps = isOverlaps(timeSettings, timeSetting6);
        System.out.println("isOverlaps = " + isOverlaps);


        List<DateSetting> dateSettings = new ArrayList<>();
        DateSetting dateSetting1 = new DateSetting(DateUtil.parseDate("2016-01-01 05:20:00"), DateUtil.parseDate("2016-02-01 05:20:00"));
        DateSetting dateSetting2 = new DateSetting(DateUtil.parseDate("2016-01-02 05:20:00"), DateUtil.parseDate("2016-02-01 05:20:00"));
        DateSetting dateSetting3 = new DateSetting(DateUtil.parseDate("2016-01-03 05:20:00"), DateUtil.parseDate("2016-02-01 05:20:00"));
        DateSetting dateSetting4 = new DateSetting(DateUtil.parseDate("2016-01-04 05:20:00"), DateUtil.parseDate("2016-02-01 05:20:00"));
        DateSetting dateSetting5 = new DateSetting(DateUtil.parseDate("2016-01-05 05:20:00"), DateUtil.parseDate("2016-02-01 05:20:00"));

        DateSetting dateSetting6 = new DateSetting(DateUtil.parseDate("2016-02-01 05:20:00"), DateUtil.parseDate("2016-04-01 05:20:00"));

        dateSettings.add(dateSetting1);
        dateSettings.add(dateSetting2);
        dateSettings.add(dateSetting3);
        dateSettings.add(dateSetting4);
        dateSettings.add(dateSetting5);
        boolean isOverlaps2 = isOverlaps(dateSettings, dateSetting6);

        System.out.println("isOverlaps2 = " + isOverlaps2);


    }

    private static boolean isOverlaps(List<DateSetting> timeSettings, DateSetting test) {
        Interval interval_A = Interval.of(test.getStartDate().toInstant(), test.getEndDate().toInstant());
        Interval interval_B;
        boolean overlaps;
        for (DateSetting dateSetting : timeSettings) {
            interval_B = Interval.of(dateSetting.getStartDate().toInstant(), dateSetting.getEndDate().toInstant());
            overlaps = interval_A.overlaps(interval_B);
            if (overlaps) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOverlaps(List<TimeSetting> timeSettings, TimeSetting test) {

        LocalDateTime dt1 = LocalDateTime.parse(test.getStartTime(), formatter);
        LocalDateTime dt2 = LocalDateTime.parse(test.getEndTime(), formatter);
        Instant i1 = dt1.toInstant(zoneOffSet);
        Instant i2 = dt2.toInstant(zoneOffSet);
        Interval interval_A = Interval.of(i1, i2);

        LocalDateTime dt3;
        LocalDateTime dt4;
        Instant i3;
        Instant i4;
        Interval interval_B;
        boolean overlaps;

        for (TimeSetting timeSetting : timeSettings) {
            dt3 = LocalDateTime.parse(timeSetting.getStartTime(), formatter);
            dt4 = LocalDateTime.parse(timeSetting.getEndTime(), formatter);
            i3 = dt3.toInstant(zoneOffSet);
            i4 = dt4.toInstant(zoneOffSet);

            interval_B = Interval.of(i3, i4);
            overlaps = interval_A.overlaps(interval_B);
            if (overlaps) {
                return true;
            }
        }
        return false;
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert, ZoneId zoneId) {
        return dateToConvert.toInstant()
                .atZone(zoneId)
                .toLocalDateTime();
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }


    public static String getNowString(String pattern) {
        LocalDateTime arrivalDate = LocalDateTime.now();
        String result = "";
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
            result = arrivalDate.format(format);

            System.out.printf("Arriving at : %s %n", result);
        } catch (DateTimeException ex) {
            System.out.printf("%s can't be formatted!%n", arrivalDate);
            ex.printStackTrace();
        }
        return result;
    }

}

class DateSetting {
    private Date startDate;
    private Date endDate;

    public DateSetting() {
    }

    public DateSetting(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

class TimeSetting {

    private String startTime;
    private String endTime;

    public TimeSetting() {
    }

    public TimeSetting(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
