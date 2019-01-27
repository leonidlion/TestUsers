package com.dev.leo.testusers.utils;

import com.dev.leo.testusers.enums.DatePattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

    public static String getDateString(String serverDate){
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat(DatePattern.SERVER_DATE.getValue(), Locale.ENGLISH);
        try{
            Date date = simpleDataFormat.parse(serverDate);
            simpleDataFormat.applyPattern(DatePattern.USER_DATE.getValue());
            return simpleDataFormat.format(date);
        }catch (ParseException e){
            return serverDate;
        }
    }
}
