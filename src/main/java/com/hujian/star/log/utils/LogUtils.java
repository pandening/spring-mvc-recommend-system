package com.hujian.star.log.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hujian on 2017/5/10.
 */
public class LogUtils implements Serializable {

    /**
     * get the time of now
     * @return
     */
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

}
