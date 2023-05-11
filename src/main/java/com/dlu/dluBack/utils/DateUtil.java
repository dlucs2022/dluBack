package com.dlu.dluBack.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static String DateAdd(Date d,String dd) {
        try{
            if(d!=null&&dd!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = addDate(d,Long.parseLong(dd));
                return (sdf.format(date));
            }else {
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Date addDate(Date date,long day) throws Exception {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
        time+=day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    public static Date string2Date(String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if (value!=null){
                return simpleDateFormat.parse(value);
            }
            else{ return null;}
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String DateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if (date!=null){
                return simpleDateFormat.format(date);
            }
            else{ return null;}
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String DateTimeToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            if (date!=null){
                return simpleDateFormat.format(date);
            }
            else{ return null;}
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getNear7Days() {
        List<String> dates = new ArrayList<String>();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dates.add(localDate.format(dtf));
        for(int i=1;i<=6;i++){
            LocalDate localDate1 = localDate.minusDays(i);
            dates.add(localDate1.format(dtf));
        }
        return dates;


    }

    public static  String nowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
}
