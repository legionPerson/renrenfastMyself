package io.renren.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final String PATTEN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTEN_DEFAULT2 = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTEN_DEFAULT3 = "yyyyMMdd";
    public static final String PATTEN_DEFAULT4 = "yyyyMMddHHmmss";
    public static final String PATTEN_DEFAULT5 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String PATTEN_DEFAULT6 = "HHmmssSSS";
    public static final String PATTEN_DEFAULT7 = "HH:mm:ss";
    public static final String PATTEN_DEFAULT8 = "HHmmss";
    public static final String PATTEN_DEFAULT9 = "yyyy-MM-dd";
    
    public static final String START_TIME_SUFFIX = " 00:00:00";
    public static final String END_TIME_SUFFIX = " 23:59:59";

    /**
     * 	获取unix时间戳
     * @param dateStr 时间字符串
     * @return
     */
    public static Long getUnixTimestemp(String dateStr) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat(PATTEN_DEFAULT);
    	try {
    		return dateFormat.parse(dateStr).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
    }
    
    /**
     * 	将unix时间戳转换为yyyyMMddHHmmss格式的时间
     * @param time Unix时间戳
     * @return
     */
    public static String getTimeByLongDate(Long time) {
    	return new SimpleDateFormat(PATTEN_DEFAULT4).format(new Date(time * 1000));
    }
    
    public static Date dateParse(String src, String pattern){
        if(StringUtils.isEmpty(src)){
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(src);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateFormat(Date date ,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     *  返回 YYYY-MM-DDTHH:mm:ssZ
     * @return
     */
    public static String formatWithZone() {
        DateTimeFormatter formatter0 = DateTimeFormatter.ofPattern(PATTEN_DEFAULT).withZone(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTEN_DEFAULT5);
        ZonedDateTime zoneTime = ZonedDateTime.parse(dateFormat(new Date(), PATTEN_DEFAULT), formatter0);
        return zoneTime.withFixedOffsetZone().format(formatter);
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(dateFormat(new Date(), "HH:mm:ss"));
        System.out.println(dateFormat(new Date(), "HHmmssSSS"));
        System.out.println(getDateString(new Date(), "yyyyMMdd", -1));
    }

    public static String dateFormat(String dateStr ,String pattern){
        if(StringUtils.isEmpty(dateStr)){
            return "";
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            String d = dateFormat(date, pattern);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *  获取输入时间的前/后几天的字符串
     * @param date
     * @param pattern
     * @param differDay
     * @return
     */
    public static String getDateString(Date date, String pattern, int differDay) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, differDay);
        Date d = c.getTime();
        return format.format(d);
    }

}
