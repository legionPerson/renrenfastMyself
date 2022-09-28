package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class StringUtils {

    public static boolean isEmpty(Object object) {
        boolean flag = false;
        if (null == object || object.equals("") || object.equals("null")) {
            flag = true;

        }
        return flag;
    }

    public static String DataToJson(Object object) {
        if (isEmpty(object)) {
            return "[{}]";
        }
        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(object);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "[{}]";
    }

    /**
     *  截取list
     * @param list 要截取的list
     * @param limit 每个list多少个
     * @param start 开始位置
     * @return 截取的集合
     */
    public static List subList(List list, Integer limit, Integer start) {
        int size = list.size();
        //尾部的位置
        int end = start + limit;
        //尾部位置不能超出范围，否则就取集合的长度
        end = Math.min(end, size);
        //截取
        List subList = list.subList(start, end);
        //计算下次截取的开始位置
        start = end;
        return subList;
    }

    /**
     *  截取传入字符的后几位
     * @param str str
     * @param character character
     * @return str
     */
    public static String subStringAfter(String str, String character) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        String [] strArr = str.split(",");
        for (String strForOne : strArr) {
            String strPro = str.substring(0, strForOne.indexOf(character));
            String strFix = strForOne.substring(strPro.length() + 1, strForOne.length());
            result.append(strFix);
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static void main(String[] args) throws Exception {
        String a = "郑研亮_014183,陈琳_019749,das_019745";
        System.out.println(subStringAfter(a, "_"));
    }

    public static Double number(String data) throws Exception {
        Double value = 0.00;
        if (!isEmpty(data)) {
            value = Double.parseDouble(data);
        }
        return value;
    }

    public static Map<String, Object> objectToMap(Object obj) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 20位末尾的数字id
     */
    public static volatile int Guid = 10000;

    public static String get20Guid() {

        StringUtils.Guid += 1;

        long now = System.currentTimeMillis();
        //获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //获取时间戳
        String time = dateFormat.format(now);
        String info= now + "";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        int ran = 0;
        if(StringUtils.Guid > 99999){
            StringUtils.Guid = 10000;
        }
        ran = StringUtils.Guid;

        return time + info.substring(2, info.length()) + ran;
    }

    /**
     *  解析xml字符串
     * @param xmlStr
     * @throws Exception
     */
    public static Map<String, String> parseXml4DOM4j(String xmlStr) throws Exception {
        Map<String, String> xmpMap = new HashMap<>(16);
        Document doc = DocumentHelper.parseText(xmlStr);
        Element message = doc.getRootElement();
        Iterator elements = message.elementIterator();
        while(elements.hasNext()){
            Element element = (Element)elements.next();
            xmpMap.put(element.getName(), element.getText());
        }
        return xmpMap;
    }


    public static String get32Guid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }


}
