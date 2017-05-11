package com.hujian.star.log;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/10.
 */
public interface LogTemplate extends Serializable {

    /**
     * you want to write log.
     * the file name will auto-generator by service according to the timestamp
     * @param log the log data content
     */
    void writeLog(String log);

    /**
     * the log will stored in the file : timestamp_type
     * @param log log
     * @param type type
     */
    void writeLog(String log,String type);

}
