package com.youbout.batchperformance.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String YYYY_YEAR_MM_MONTH_DD_DATE = "yyyy year MM month dd date";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_BIAS_MM_BIAS_DD = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String HH = "HH";
    public static final String MI = "mm";
    public static final String SS = "ss";
    public static final String SIMPLE_YYYY_MM_DD = "yyyyMMdd";
    
    
    public static Date toDate(String date, String format) throws ParseException {
        if(date == null || date == "") throw new NullPointerException("input date is null or empty!");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public static String toStr(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Timestamp toTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    /**
     * Convert String type date to TimeStamp</br>
     * Tip: The format type of the date parameter must be consistent with the format of the format parameter, otherwise a ParseException will be thrown
     * @param date-date
     * @param format-format type
     * @return Timestamp
     * @throws ParseException
     */
    public static Timestamp toTimestamp(String date,String format) throws ParseException{
        return toTimestamp(toDate(date, format));
    }
    
    public static String toStr(String date, String original_format,String transform_format) throws ParseException{
        return toStr(toDate(date, original_format), transform_format);
    }
    
    /**
     * Convert the Date of Timestamp type into Sting type according to the format of the input
     * @param timestamp date
     * @param format format
     * @return String
     */
    public static String toStr(Timestamp timestamp,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }
    
    public static String dateToStr(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
       return sdf.format(date);
    }

    public static Timestamp getBeginTimeOfDay(Date calBeginDate) throws ParseException {
        String date = dateToStr(calBeginDate, YYYY_MM_DD);
        date = date + " 00:00:00.000";
        return toTimestamp(date,YYYY_MM_DD_HH_MM_SS_SSS);
    }
    
    public static Timestamp getEndTimeOfDay(Date calBeginDate) throws ParseException {
        String date = dateToStr(calBeginDate, YYYY_MM_DD);
        date = date + " 23:59:59.999";
        return toTimestamp(date,YYYY_MM_DD_HH_MM_SS_SSS);
    }

    public static Date getFirstDayOfMonth(String date,String format){  
        Date d = null;
        try {
            d = toDate(date, format);
            return calFirstDayOfMonth(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    public static Date getFirstDayOfMonth(Date date){
        return calFirstDayOfMonth(date);
    }

    private static Date calFirstDayOfMonth(Date date) {
        Calendar firstDate = null;
        try {
            firstDate = Calendar.getInstance();  
            firstDate.setTime(date);
            firstDate.set(Calendar.DATE,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firstDate.getTime();
    }
    
    public static Date getLastDayOfMonth(String date, String format) {
        Date d = null;
        try {
            d = toDate(date, format);
            return calLastDayOfMonth(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date getLastDayOfMonth(Date date){
        return calLastDayOfMonth(date);
    }

    private static Date calLastDayOfMonth(Date date) {
        Calendar lastDate = null;
        try {
            lastDate = Calendar.getInstance();
            lastDate.setTime(date);
            lastDate.set(Calendar.DATE, 1);// Set to the 1st of the current month
            lastDate.add(Calendar.MONTH, 1);// Add one month to become the 1st of the next month
            lastDate.add(Calendar.DATE, -1);// Subtract one day to become the last day of the month
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastDate.getTime();
    }

    public static int getDaysOfMonth(Date date){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(toStr(date,YYYY )));
        c.set(Calendar.MONTH, Integer.valueOf(toStr(date,MM ))-1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static void main(String[] args) throws ParseException{
        String date = "2014-09-02 00:00:00";
        String date2 = "2014-12-02 23:59:59";
        Date begin = DateUtil.getFirstDayOfMonth(date,DateUtil.YYYY_MM_DD_HH_MM_SS );
        Date end = DateUtil.getLastDayOfMonth(date2,DateUtil.YYYY_MM_DD_HH_MM_SS );
        System.out.println(begin);
        System.out.println(end);
        System.out.println(getDaysOfMonth(begin)+"");
        System.out.println(toDate("03/01/2021", "MM/dd/yyyy"));
    }
}