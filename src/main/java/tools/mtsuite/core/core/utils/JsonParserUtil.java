package tools.mtsuite.core.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import tools.mtsuite.core.core.excepctions.BadRequestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class JsonParserUtil {

    private static final Gson gson = new Gson();

    public static String objectToJson(Object obj){
        return gson.toJson(obj);
    }


}
