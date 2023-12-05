package tricolor.no1.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getNowTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentSimpleDateFormat = simpleDateFormat.format(date);
        return currentSimpleDateFormat;
    }

    public static java.sql.Date getSqlDate()
    {
        Date date = new Date();
        java.sql.Date dd = new java.sql.Date(date.getTime());
        return dd;
    }

}
