package com.example.luckDraw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hzz on 2018/12/20
 */
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * @description 获取当前时间日期
     * @author hzz
     * @date 2019/1/3 10:01
    **/
    public static Date getCurrentDate() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date);
            return sdf.parse(s);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }



    /**
     * @description 获取当前时间日期
     * @author zl
     * @date 2019/8/15 10:01
     **/
    public static Date getCurrentDateTime() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = sdf.format(date);
            return sdf.parse(s);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }





    public static Date getYesDate() {
        try {
            Date date = new Date(System.currentTimeMillis()-1000*60*60*24);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date);
            return sdf.parse(s);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }



    //将短时间格式转化为Date yyyy-MM-dd
    public static Date convertStringToDate(String operateDate) {
        try {
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
//            ParsePosition parsePosition=new ParsePosition(0);  ,parsePosition
            Date strToDate=simpleDateFormat.parse(operateDate);
            return strToDate;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }



    public static Date getTheDateAfter(String operateDate) {
        try {

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(convertStringToDate(operateDate));
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date afterDate=calendar.getTime();
            return  afterDate;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }





    /**
     * @description date日期转string
     * @author hzz
     * @date 2018/12/21 9:26
     **/
    public static String convertDateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * @description date规定日期转string
     * @author hzz
     * @date 2018/12/21 9:26
     **/
    public static String convertDateToStringDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(date);
    }

////服务请求方提交服务请求的时间 "YYYY-MM-DDTHH:mm:ss.SSS"  "2018-04-17T12:38:19.219"
    public static String getReqTime() {
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS");//Illegal pattern character 'T'
        return sdf.format(date);
    }


    public static String splitString(String s) {
        StringBuilder stringBuilder=new StringBuilder();
        String[] strings=s.split(",");
        for(String string:strings){
            stringBuilder.append("'").append(string).append("'").append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }


}
