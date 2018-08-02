package com.imooc.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.imooc.dataobject.Sms;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: Constant
 * @Description:系统工具类
 * @Auther: xuhaibin-work-PC
 * @Date: 2018-08-02 14:26
 * @Version: 1.0.0
 */
public class Constant {


    /**
     * 短信-订单通知
     *
     * @param phone 手机号=国家代码+手机号
     * @param sms   模板类型 1-验证码(传值：captcha)；2-订单名称(传值：orderName);3-一事一测服务接单提醒(传值：orderBuyTime、orderName、orderCountDownMinute)
     * @param type  模板类型 1-验证码；2-订单通知-大师;3-一事一测服务接单提醒
     * @return -1-发送失败，0-发送成功，大于0-倒计时秒数
     * @throws Exception
     */
    public static boolean sendOrder(String phone, Sms sms, int type) {

        try {

            if (sendSMS(phone, sms, type)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 阿里云短信发送  版本 2017-6月之后
     *
     * @param phone 手机号不需要添加区号
     * @param sms   模板类型 1-验证码(传值：captcha)；2-订单名称(传值：orderName);3-一事一测服务接单提醒(传值：orderBuyTime、orderName、orderCountDownMinute)
     * @param type  模板类型 1-验证码；2-订单通知;3-一事一测服务接单提醒
     * @return
     * @throws Exception <sms>
     *                   <accessKeyId>LTAIc8Guozl3mLQ4</accessKeyId>
     *                   <accessKeySecret>1Mcj62ji1ApJ1u2pHsqgGUDtnz2efG</accessKeySecret>
     *                   <signName>问道大师</signName>
     *                   <templateCodeCaptcha>SMS_97780033</templateCodeCaptcha>
     *                   <templateCodeOrder>SMS_97990030</templateCodeOrder>
     *                   <templateCodeOrderGratis>SMS_105750074</templateCodeOrderGratis>
     *                   </sms>
     */
    public static boolean sendSMS(String phone, Sms sms, int type) throws Exception {

        String templateCode = "";
        //模板类型-验证码通知
        if (type == 1) {
//            templateCode = "sms.templateCodeCaptcha";
        }
        //模板类型-订单通知
        if (type == 2) {
//            templateCode = "sms.templateCodeOrder";
        }

        //模板类型-一事一测服务接单提醒(订单提醒)
        if (type == 3) {
//            templateCode = common.getConfigTxt("sms.templateCodeOrderGratis").trim();
        }


        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "sms.accessKeyId";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "sms.accessKeySecret";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("sms.signName");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败

        //模板类型-验证码通知---这里的输出模板要跟阿里云控制台设置的一一对应
        if (type == 1) {
            request.setTemplateParam("{\"content\":\"" + sms.getCaptcha() + "\"}");
        }

        //模板类型-订单通知
        if (type == 2) {
            request.setTemplateParam("{\"name\":\"" + sms.getOrderName() + "\"}");
        }

        //模板类型-一事一测服务接单提醒(订单提醒)
        if (type == 3) {
            request.setTemplateParam("{\"time\":\"" + sms.getOrderBuyTime() + "\",\"name\":\"" + sms.getOrderName() + "\",\"minute\":\"" + sms.getOrderCountDownMinute() + "\"}");
        }


        //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("111111");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.print("Code:" + sendSmsResponse.getCode());
        System.out.println("________________Message:" + sendSmsResponse.getMessage());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            return true;
        } else {
            return false;
        }

    }


    /**
     * 计算两个日期相差的天数
     *
     * @param date 结束时间：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static int getIntervalDays(String date) throws Exception {
        if (null == date) {
            return -1;
        }
        // 获取当前时间戳
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        Date fDate = df.parse(df.format(new Date()));
        // 结束时间戳
        Date oDate = df.parse(date);

        long intervalMilli = oDate.getTime() - fDate.getTime();
        if (intervalMilli < 0) {
            return 0;
        }
        double dayTimes = ((double) intervalMilli / (24 * 60 * 60 * 1000));

        return (int) Math.ceil(dayTimes); // 1.0232>> 返回2

    }


    /**
     * 阳历日期转换为阴历日期
     *
     * @param birthday 阳历（公历）日期：1990-11-11 12:12:00
     * @throws Exception
     */
    public static String solarToLunar(String birthday) throws Exception {
        // 公历转农历
        String year = birthday.substring(0, 4);        // 年
        String month = birthday.substring(5, 7);        // 月
        String day = birthday.substring(8, 10);        // 日
        String time = birthday.substring(10).trim();    // 时分秒
        String solarDate = year + month + day;            // 转换参数
        // 返回结果为年月日的数组
        int[] lunar = LunarCalendar.solarToLunar(solarDate);
        // 获取农历生辰 2017-07-31 12:12:00
        String lunarDate = CommonUtil.objToString(lunar[0]) + "-" + CommonUtil.objToString(lunar[1]) + "-"
                + CommonUtil.objToString(lunar[2]) + " " + time;
        return lunarDate;
    }


    /**
     * 由阳历(公历)出生日期获得年龄
     *
     * @param birthday 阳历出生日期(1992-11-8)
     * @return
     * @throws Exception
     */
    public static int getAge(String birthday) throws ParseException {
        if ("".equals(birthday)) {
            return 0;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(birthday);
            Calendar cal = Calendar.getInstance();

            if (cal.before(date)) {
                return 0;
            }

            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(date);

            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            int age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;
                } else {
                    age--;
                }
            }
            return age;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取当前时间与设置时间之差---返回值，转化成秒
     *
     * @param start 设置时间(yyyy-MM-dd HH:mm:ss)
     * @return
     * @throws Exception
     */
    public static int getPoorTime(String start) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(start);

        long delta = new Date().getTime() - date.getTime();
        int seconds = (int) (delta / 1000L);

        return seconds;
    }


    /**
     * 解析html中img标签，并且取得所有图片地址
     *
     * @param content
     * @return
     */
    public static String[] getImgs(String content) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String str = "";
        String[] images = null;
        String regEx_img = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);
                tempSelected = tempSelected.replaceAll("'", "");
                if ("".equals(str)) {
                    str = tempSelected;
                } else {
                    String temp = tempSelected;
                    str = str + "," + temp;
                }
            }
        }
        if (!"".equals(str)) {
            images = str.split(",");
        }

        return images;
    }


    /**
     * 解决接收请求中参数中文乱码问题
     *
     * @param str
     * @return
     */
    public static String encodeStr(String str) throws Exception {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
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
     * SHA、SHA1加密
     *
     * @parameter： str：待加密字符串
     * @return： 加密串
     **/
    public static String getSHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 用SHA1算法生成安全签名
     *
     * @param appId     微信appId
     * @param appSecret 微信appSecret
     * @param nonceStr  微信随机字符串
     * @return 安全签名
     */
    public static String getSHA1(String appId, String appSecret, String nonceStr) {
        try {
            String[] array = new String[]{appId, appSecret, nonceStr};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * URL解码
     *
     * @param outBuffer
     * @return
     */
    public static String replacer(String outBuffer) {
        String data = outBuffer.toString();
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 过滤内容中HTML标签和"\r\n,\t,\r,\n",获取中文内容
     *
     * @param htmlContent
     * @return
     */
    public static String replaceHTMLContent(String htmlContent) throws Exception {
        if (htmlContent != null) {
            // 过滤HTML标签
            htmlContent = htmlContent.replaceAll("<[.[^<]]*>", "").replace("&nbsp;", "");
            Pattern p = Pattern.compile("(\r\n|\r|\n|\n\r|\t)");
            Matcher m = p.matcher(htmlContent);
            // 过滤"\r\n,\t,\r,\n"
            htmlContent = m.replaceAll("");
        }

        return htmlContent;
    }


    /**
     * 从N个元素的集合中随机取m个元素的算法实现
     * 随机取num个从0到maxVal的整数。包括零，不包括maxValue
     *
     * @param num
     * @param maxValue
     * @return
     */
    public static List<Integer> getRandomIndex(int num, int maxValue) {
        if (num > maxValue) {
            num = maxValue;
        }
        if (num < 0 || maxValue < 0) {
            throw new RuntimeException("num or maxValue must be greater than zero");
        }
        List<Integer> result = null;
        try {
            result = new ArrayList<Integer>(num);

            int[] tmpArray = new int[maxValue];
            for (int i = 0; i < maxValue; i++) {
                tmpArray[i] = i;
            }

            Random random = new Random();
            for (int i = 0; i < num; i++) {
                int index = random.nextInt(maxValue - i);
                int tmpValue = tmpArray[index];
                result.add(tmpValue);
                int lastIndex = maxValue - i - 1;
                if (index == lastIndex) {
                    continue;
                } else {
                    tmpArray[index] = tmpArray[lastIndex];
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            result.add(0);        // 默认取一个,
        }

        return result;
    }


    /**
     * 判断是否是手机访问
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean getIsPhone(HttpServletRequest request) {
        boolean flag = true;

        try {

            String userAgent = request.getHeader("user-agent");

            if (!(userAgent.indexOf("Android") != -1 || userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1)) {
                flag = false;
            }

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 获取火币涨跌幅---转化为百分数%
     *
     * @param open  开盘价
     * @param close 收盘价,当K线为最晚的一根时，是最新成交价
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal getHuobiGains(BigDecimal open, BigDecimal close, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "参数scale必须大于或等于0");
        }

        // 收盘价 - 开盘价
        BigDecimal sub = close.subtract(open);

        // (收盘价 - 开盘价) / 开盘价 = 涨跌幅
        BigDecimal gains = sub.divide(open, 10, BigDecimal.ROUND_UP);

        // 转化为百分数%
        return (gains.multiply(new BigDecimal(100))).setScale(scale, BigDecimal.ROUND_UP);
    }


    /**
     * 获取Okex币币行情涨跌幅
     *
     * @param high  开盘价
     * @param low   收盘价,当K线为最晚的一根时，是最新成交价
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double getOkexGains(BigDecimal high, BigDecimal low, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        // 收盘价 - 开盘价
        BigDecimal sub = low.subtract(high);

        // (最新成交价 - 前一天24点开盘价) / 前一天24点开盘价 = 涨跌幅
        return sub.divide(high, scale, BigDecimal.ROUND_UP).doubleValue();
    }


    /**
     * 去掉BigDecimal后无用的零
     *
     * @param num
     * @return
     */
    public static BigDecimal subTrailingZeros(BigDecimal num) {

        String str = num.stripTrailingZeros().toPlainString();

        return new BigDecimal(str);
    }


    /**
     * 验证实体对象是否为空
     *
     * @param bean
     * @param attributeName 自定义验证的
     */
    public static boolean isEmpty(Object bean, String... attributeName) {
        List<String> list = Arrays.asList(attributeName);
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            if (list.contains(name)) {
                if ("class".equals(name)) {
                    continue;
                }
                if (PropertyUtils.isReadable(bean, name)) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(bean, name);
                        if (value == null) {
                            continue;
                        } else {
                            return false;
                        }
                    } catch (java.lang.IllegalArgumentException ie) {

                    } catch (Exception e) {

                    }
                }
            } else {
                continue;
            }
        }
        return true;
    }


    /**
     * 验证实体对象是否为空
     * 如果对象属性为空，则判断该对象为空。
     *
     * @param bean
     * @return
     */
    public static boolean isNullOrEmptyBean(Object bean) {
        if (null == bean) {
            return true;
        }
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            if ("class".equals(name)) {
                continue;
            }
            if (PropertyUtils.isReadable(bean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(bean, name);
                    if (value == null) {
                        continue;
                    } else {
                        return false;
                    }
                } catch (java.lang.IllegalArgumentException ie) {

                } catch (Exception e) {

                }
            }
        }
        return true;
    }


    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Sms sms = new Sms();
        sms.setOrderBuyTime("2018-04-08 12:12:12");
        sms.setOrderCountDownMinute("15");
        sms.setOrderName("《一事一测》");
        sendOrder("18046771034", sms, 3);

    }


}
