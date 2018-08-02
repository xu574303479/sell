package com.imooc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 阴历与阳历转换工具
 *
 * @author Tangyaosheng
 * @version 1.0.0
 * @date 2017年6月25日 下午9:54:24
 */
public class LunarCalendar {


    /**
     * 计算阴历日期参照1900年到2049年
     * 举个例子：
     * 1980年的数据是： 0x095b0
     * 二进制：0000 1001 0101 1011 0000
     * 20---------------------1
     * 1-4: 表示当年有无闰年，有的话，为闰月的月份，没有的话，为0。
     * 5-16：为除了闰月外的正常月份是大月还是小月，1为30天，0为29天。
     * 注意：从1月到12月对应的是第16位到第5位。
     * 17-20：表示闰月是大月还是小月，仅当存在闰月的情况下有意义。
     * 表示1980年没有闰月，从1月到12月的天数依次为：30、29、29、30、29、30、29、30、30、29、30、30。
     */
    private final static int[] LUNAR_INFO = {
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, // 1900 - 1909
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, // 1910 - 1919
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, // 1920 - 1929
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, // 1930 - 1939
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, // 1940 - 1949
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, // 1950 - 1959
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, // 1960 - 1969
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, // 1970 - 1979
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, // 1980 - 1989
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, // 1990 - 1999
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, // 2000 - 2009
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, // 2010 - 2019
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, // 2020 - 2029
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, // 2030 - 2039
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0  // 2040 - 2049
    };

    // 允许输入的最小年份
    public final static int MIN_YEAR = 1900;

    // 允许输入的最大年份
    public final static int MAX_YEAR = 2049;

    // 阳历日期计算起点
    private final static String START_DATE = "19000130";

    // 农历月份大写
    private final static String[] MONTHS = new String[]{"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    // 农历日大写
    private final static String[] DAYS = new String[]{"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"};

    // 十天干
    private final static String[] GANS = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};

    // 十二地支
    private final static String[] ZHIS = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    // 十二生肖
    private final static String[] ANIMALS = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    // 当年是否有闰月
    public static boolean isLeapYear;


    /**
     * 返回今日的阴历名称
     *
     * @return 阴历名称
     */
    public static String todayToLunar() {
        return getLunar("1991-11-11 11:11:11");
    }

    /**
     * 根据公历年月日，返回阴历名称
     *
     * @param date yyyy-mm-dd HH:mm:ss
     * @return 阴历名称
     * @throws Exception
     */
    public static String getLunar(String date) {
        // 转换日期格式(年,月,日,时)
        Map<String, String> dateMap = stringDateToMap(date);
        String years = dateMap.get("year");
        String months = dateMap.get("month");
        String days = dateMap.get("day");

        String solarDate = years + months + days;
        int hour = Integer.parseInt(dateMap.get("hour"));

        if (!solarDate.matches("[0-9]{8}")) { // 公历日期错误
            return "-1";
        }
        try {
            int[] result = solarToLunar(solarDate);
            String year = cyclical(result[0]) + "年";
            String month = MONTHS[result[1] - 1] + "月";
            String day = DAYS[result[2] - 1];
            String shi = getShiCheng(hour);

//            String leap = result[3] == 1 ? "闰" : "";          // 是否是闰年
//            String animal = AnimalsYear(result[0]) + "年";     // 生肖

            return year + " " + month + day + " " + shi;

        } catch (Exception e) {

        }

        return "-1"; // 公历日期错误
    }


    /**
     * 将日期字符串转化为Map分别存储：年月日，年，月，日字段
     *
     * @param date
     * @return
     */
    public static Map<String, String> stringDateToMap(String date) {
        if ("".equals(date) || date == null) {
            return null;
        }

        Date d = new Date();
        try {
            d = new SimpleDateFormat("yyyy-MM-dd HH").parse(date);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
            SimpleDateFormat sdf5 = new SimpleDateFormat("HH");

            String birthday = sdf1.format(d);
            String year = sdf2.format(d);
            String month = sdf3.format(d);
            String day = sdf4.format(d);
            String hour = sdf5.format(d);

            Map<String, String> map = new HashMap<String, String>();
            map.put("birthday", birthday);
            map.put("year", year);
            map.put("month", month);
            map.put("day", day);
            map.put("hour", hour);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 通过时，返回生辰
     *
     * @param hour
     * @return
     */
    public static String getShiCheng(int hour) {
        if (hour < 0 || hour > 23) return "";

        return shicheng[(hour + 1) / 2];
    }


    static String[] shicheng = new String[]{
            "子时", "丑时", "寅时", "卯时", "辰时", "巳时", "午时", "未时", "申时", "酉时", "戌时", "亥时", "子时"
    };


    /**
     * 阴历转换为阳历
     *
     * @param lunarDate     阴历日期,格式YYYYMMDD
     * @param leapMonthFlag 是否为闰月
     * @return 阳历日期, 格式：YYYYMMDD
     * @throws Exception
     */
    public static String lunarToSolar(String lunarDate, boolean leapMonthFlag) throws Exception {
        if (!lunarDate.matches("[0-9]{8}")) {
            throw (new Exception("非法农历年份！"));
        }

        int lunarYear = Integer.parseInt(lunarDate.substring(0, 4));
        int lunarMonth = Integer.parseInt(lunarDate.substring(4, 6));
        int lunarDay = Integer.parseInt(lunarDate.substring(6, 8));

        // 检查阴历日期是否合法
        checkLunarDate(lunarYear, lunarMonth, lunarDay, leapMonthFlag);

        int offset = 0;

        for (int i = MIN_YEAR; i < lunarYear; i++) {
            int yearDaysCount = getYearDays(i); // 求阴历某年天数
            offset += yearDaysCount;
        }
        // 计算该年闰几月
        int leapMonth = getLeapMonth(lunarYear);

        if (leapMonthFlag & leapMonth != lunarMonth) {
            throw (new Exception("您输入的闰月标志有误！"));
        }

        // 当年没有闰月或月份早于闰月或和闰月同名的月份（但本月不是闰月）
        if (leapMonth == 0 || (lunarMonth < leapMonth) || (lunarMonth == leapMonth && !leapMonthFlag)) {
            for (int i = 1; i < lunarMonth; i++) {
                int tempMonthDaysCount = getMonthDays(lunarYear, i);
                offset += tempMonthDaysCount;
            }

            // 检查日期是否大于最大天
            if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                throw (new Exception("不合法的农历日期！"));
            }
            offset += lunarDay; // 加上当月的天数
        } else {// 当年有闰月，且月份晚于或等于闰月
            for (int i = 1; i < lunarMonth; i++) {
                int tempMonthDaysCount = getMonthDays(lunarYear, i);
                offset += tempMonthDaysCount;
            }
            if (lunarMonth > leapMonth) {
                int temp = getLeapMonthDays(lunarYear); // 计算闰月天数
                offset += temp; // 加上闰月天数

                if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                    throw (new Exception("不合法的农历日期！"));
                }
                offset += lunarDay;
            } else { // 如果需要计算的是闰月，则应首先加上与闰月对应的普通月的天数
                // 计算月为闰月
                int temp = getMonthDays(lunarYear, lunarMonth); // 计算非闰月天数
                offset += temp;

                if (lunarDay > getLeapMonthDays(lunarYear)) {
                    throw (new Exception("不合法的农历日期！"));
                }
                offset += lunarDay;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date myDate = null;
        myDate = formatter.parse(START_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(myDate);
        c.add(Calendar.DATE, offset);
        myDate = c.getTime();

        return formatter.format(myDate);
    }

    /**
     * 阳历日期转换为阴历日期
     *
     * @param solarDate 阳历日期,格式YYYYMMDD
     * @return 阴历日期长度为4的int数组，0位-年，1位-月，2位-日，3位-是否闰月的标志（0-不是闰月，1-是闰月）
     * @throws Exception
     */
    public static int[] solarToLunar(String solarDate) throws Exception {
        int i;
        int temp = 0;
        int lunarYear;
        int lunarMonth; // 农历月份
        int lunarDay; // 农历当月第几天
        boolean leapMonthFlag = false;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date myDate = null;
        Date startDate = null;
        try {
            myDate = formatter.parse(solarDate);
            startDate = formatter.parse(START_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int offset = daysBetween(startDate, myDate);

        for (i = MIN_YEAR; i <= MAX_YEAR; i++) {
            temp = getYearDays(i); // 求当年农历年天数
            if (offset - temp < 1) {
                break;
            } else {
                offset -= temp;
            }
        }
        lunarYear = i;

        int leapMonth = getLeapMonth(lunarYear);// 计算该年闰哪个月
        // 设定当年是否有闰月
        if (leapMonth > 0) {
            isLeapYear = true;
        } else {
            isLeapYear = false;
        }

        for (i = 1; i <= 12; i++) {
            if (i == leapMonth + 1 && isLeapYear) {
                temp = getLeapMonthDays(lunarYear);
                isLeapYear = false;
                leapMonthFlag = true;
                i--;
            } else {
                temp = getMonthDays(lunarYear, i);
            }
            offset -= temp;
            if (offset <= 0) {
                break;
            }
        }

        offset += temp;
        lunarMonth = i;
        lunarDay = offset;

        int[] result = {lunarYear, lunarMonth, lunarDay, (leapMonthFlag & (lunarMonth == leapMonth)) ? 1 : 0};

        return result;

//        return "阴历：" + lunarYear + "年" + (leapMonthFlag & (lunarMonth == leapMonth) ? "闰" : "") + lunarMonth + "月"
//                + lunarDay + "日";
    }

    /**
     * 返回阴历年所属生肖
     *
     * @param year 阴历年
     * @return 生肖
     */
    public static String AnimalsYear(int year) {
        return ANIMALS[(year - 4) % 12];
    }


    /**
     * 返回阴历年的名称
     *
     * @param year 　阴历年
     * @return 年份名称
     */
    public static String cyclical(int year) {
        int num = year - 1900 + 36;
        return (GANS[num % 10] + ZHIS[num % 12]);
    }


    /**
     * 计算阴历{@code year}年闰哪个月 1-12 , 没闰传回 0 (1-4位)
     * 0xf----1111
     * 位与操作相当于只保留1－4位
     *
     * @param year 阴历年
     * @return (int)月份
     */
    private static int getLeapMonth(int year) {
        return (int) (LUNAR_INFO[year - 1900] & 0xf);
    }

    /**
     * 计算阴历{@code year}年闰月多少天  (17-20位)
     *
     * @param year 阴历年
     * @return (int)天数
     */
    private static int getLeapMonthDays(int year) {
        if (getLeapMonth(year) != 0) {
            if ((LUNAR_INFO[year - 1900] & 0xf0000) == 0) {
                return 29;
            } else {
                return 30;
            }
        } else {
            return 0;
        }
    }

    /**
     * 计算阴历{@code lunarYeay}年{@code month}月的天数 (5-16位)
     *
     * @param lunarYeay 阴历年
     * @param month     阴历月
     * @return (int)该月天数
     * @throws Exception
     */
    private static int getMonthDays(int lunarYeay, int month) throws Exception {
        if ((month > 12) || (month < 1)) {
            throw (new Exception("月份有错！"));
        }
        // 0X0FFFF[0000 {1111 1111 1111} 1111]中间12位代表12个月，1为大月，0为小月
        int bit = 1 << (16 - month);
        if (((LUNAR_INFO[lunarYeay - 1900] & 0x0FFFF) & bit) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 计算阴历{@code year}年的总天数
     *
     * @param year 阴历年
     * @return (int)总天数
     */
    private static int getYearDays(int year) {
        int sum = 29 * 12;
        for (int i = 0x8000; i >= 0x8; i >>= 1) {
            if ((LUNAR_INFO[year - 1900] & 0xfff0 & i) != 0) {
                sum++;
            }
        }
        return sum + getLeapMonthDays(year);
    }

    /**
     * 计算两个阳历日期相差的天数。
     *
     * @param startDate 开始时间
     * @param endDate   截至时间
     * @return (int)天数
     */
    private static int daysBetween(Date startDate, Date endDate) {
        int days = 0;
        // 将转换的两个时间对象转换成Calendar对象
        Calendar can1 = Calendar.getInstance();
        can1.setTime(startDate);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(endDate);
        // 拿出两个年份
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);
        // 天数

        Calendar can = null;
        // 如果can1 < can2
        // 减去小的时间在这一年已经过了的天数
        // 加上大的时间已过的天数
        if (can1.before(can2)) {
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            can = can1;
        } else {
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++) {
            // 获取小的时间当前年的总天数
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            // 再计算下一年。
            can.add(Calendar.YEAR, 1);
        }
        return days;
    }

    /**
     * 检查阴历日期是否合法
     *
     * @param lunarYear     阴历年
     * @param lunarMonth    阴历月
     * @param lunarDay      阴历日
     * @param leapMonthFlag 闰月标志
     * @throws Exception
     */
    private static void checkLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean leapMonthFlag)
            throws Exception {
        if ((lunarYear < MIN_YEAR) || (lunarYear > MAX_YEAR)) {
            throw (new Exception("非法农历年份！"));
        }
        if ((lunarMonth < 1) || (lunarMonth > 12)) {
            throw (new Exception("非法农历月份！"));
        }
        if ((lunarDay < 1) || (lunarDay > 30)) { // 中国的月最多30天
            throw (new Exception("非法农历天数！"));
        }

        int leap = getLeapMonth(lunarYear);// 计算该年应该闰哪个月
        if ((leapMonthFlag == true) && (lunarMonth != leap)) {
            throw (new Exception("非法闰月！"));
        }
    }

    /**
     * 是否为公历闰年
     *
     * @param year 要判断的公历年
     * @return 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        boolean isLeap = false;
        if (year % 4 == 0) {
            isLeap = true;
        }

        if (year % 100 == 0) {
            isLeap = false;
        }

        if (year % 400 == 0) {
            isLeap = true;
        }

        return isLeap;
    }


    public static void main(String[] args) throws Exception {
//    	System.out.println(LunarCalendar.lunarToSolar("19921014", isLeapYear));
//    	int[] solarToLunar = LunarCalendar.solarToLunar("19931108");
//    	for (int i : solarToLunar) {
//    		System.out.print(i);
//		}
//        System.out.println(0x8 & 0xf);
//        System.out.println(AnimalsYear(1981));
//        System.out.println(cyclical(1981));

        System.out.println(getLunar("1991-11-11 11:11:11"));
//        System.out.println(todayToLunar());
    }


}
