package utils.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    private final static DateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss");
    public static String getTimeString()
    {
        return FORMATTER.format(new Date());
    }

    public static Date getTime() {
        return new Date();
    }
}