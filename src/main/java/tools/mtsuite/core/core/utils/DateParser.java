package tools.mtsuite.core.core.utils;

import tools.mtsuite.core.core.excepctions.BadRequestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateParser {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dateToString(Date date){
        return sdf.format(date);
    }
    public static Date stringToDate(String date){
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw  new BadRequestException("500","Bad input date: "+date);
        }
    }

}
