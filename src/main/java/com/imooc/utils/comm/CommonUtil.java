package com.imooc.utils.comm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: CommonUtil
 * @Description:
 * @Auther: xuhaibin-work-PC
 * @Date: 2018-08-02 14:32
 * @Version: 1.0.0
 */
public class CommonUtil implements Serializable {
    public static String AESPWD = "rWUlg5Rp!iWLdmGpHE^d*hVhC!xQd6Cpjp^bqYIUj2!SXlXv#vNI3TBbesMIl5P8";
    private static final long serialVersionUID = 1L;
    private static final String[] CN_UPPER_NUMBER = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_UPPER_MONETRAY_UNIT = new String[]{"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};
    private static final String CN_FULL = "整";
    private static final String CN_NEGATIVE = "负";
    private static final int MONEY_PRECISION = 2;
    private static final String CN_ZEOR_FULL = "零元整";

    public CommonUtil() {
    }

    public static int differentDays(String date1, String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        return differentDays(format.parse(date1), format.parse(date2));
    }

    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(6);
        int day2 = cal2.get(6);
        int year1 = cal1.get(1);
        int year2 = cal2.get(1);
        if (year1 == year2) {
            return day2 - day1;
        } else {
            int timeDistance = 0;

            for (int i = year1; i < year2; ++i) {
                if ((i % 4 != 0 || i % 100 == 0) && i % 400 != 0) {
                    timeDistance += 365;
                } else {
                    timeDistance += 366;
                }
            }

            return timeDistance + (day2 - day1);
        }
    }

    public static int getTimeForInt(String time) {
        return Integer.parseInt(time.replaceAll(":", ""));
    }


    public static String getNumCode(int charCount) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        char[] ch = "0123456789".toCharArray();
        int len = ch.length;

        for (int i = 0; i < charCount; ++i) {
            int index = r.nextInt(len);
            sb.append(ch[index]);
        }

        return sb.toString();
    }

    public static int getRandom(int m, int n) {
        if (n < m) {
            n = m;
        }

        Random rand = new Random();
        int num = m + rand.nextInt(n - m + 1);
        return num;
    }

    public static int getUUID() {
        return getRandom(10000000, 100000000);
    }

    public static int getSerial() {
        return getRandom(1000000, 10000000);
    }

    public static String getFormatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formatDate = format.format(date);
        return formatDate;
    }

    public static String getFormatDate(String datestr, String oldPattern, String newPattern) {
        SimpleDateFormat formatOld = new SimpleDateFormat(oldPattern);
        SimpleDateFormat formatNew = new SimpleDateFormat(newPattern);

        try {
            Date date = formatOld.parse(datestr);
            String formatDate = formatNew.format(date);
            return formatDate;
        } catch (ParseException var7) {
            var7.printStackTrace();
            return datestr;
        }
    }

    public static String getFormatCurrdate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            String formatDate = format.format(date);
            return formatDate;
        } catch (Exception var4) {
            var4.printStackTrace();
            return "";
        }
    }

    public static String getStrCode(int charCount) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        char[] ch = "0123456789".toCharArray();
        int len = ch.length;

        for (int i = 0; i < charCount; ++i) {
            int index = r.nextInt(len);
            sb.append(ch[index]);
        }

        return sb.toString();
    }

    public static String getCurWeek() {
        String[] weekDays = new String[]{"0", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        int w = cal.get(7) - 1;
        return weekDays[w];
    }

    public static String createSerial(String pre, int length) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String rand = getStrCode(length);
        return pre + format.format(now) + rand;
    }

    public static String forDight(double f) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(f);
    }

    public static String forDight4(double f) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(f);
    }

    public static double forCeil(double f, int n) {
        double num = Math.ceil(f * Math.pow(10.0D, (double) n)) / Math.pow(10.0D, (double) n);
        return num;
    }

    public static double forFloor(double f, int n) {
        double num = Math.floor(f * Math.pow(10.0D, (double) n)) / Math.pow(10.0D, (double) n);
        return num;
    }

    public static String objToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String objToString(Object obj, String def) {
        return obj == null ? def : obj.toString();
    }

    public static int compareDouble(double val1, double val2) {
        BigDecimal v1 = new BigDecimal(val1);
        BigDecimal v2 = new BigDecimal(val2);
        if (v1.compareTo(v2) < 0) {
            return -1;
        } else {
            return v1.compareTo(v2) == 0 ? 0 : 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(getFormatDate("2017-6-5", "yyyy-MM-dd", "MM/dd/yy"));
    }
}
