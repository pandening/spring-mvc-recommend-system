package com.hujian.star.mapper;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/9.
 */
@Repository
public interface CrontabMapper extends Serializable{


    /**
     * every time do the cron,write the log to mysql table : cron_log @ star database
     * @param log the log content
     */
    void setCronLog(String log);

}
