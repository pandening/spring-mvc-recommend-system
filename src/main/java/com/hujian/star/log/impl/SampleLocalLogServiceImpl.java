package com.hujian.star.log.impl;

import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import okio.BufferedSink;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hujian on 2017/5/10.
 */
@Service
public class SampleLocalLogServiceImpl implements LogTemplate {

    /**
     * this is the config
     */
    @Autowired
    private ConfigTemplate configTemplate = null;

    /**
     * get the file path
     * @return
     */
    private String getFilePathByTimeStamp(){

        SimpleDateFormat df = new SimpleDateFormat("YYYY_MM_dd_HH");
        Calendar calendar = Calendar.getInstance();

        /**
         * get the file path
         */
        String path = configTemplate.getLogBaseFile() + df.format(calendar.getTime());

        return path;
    }

    /**
     * write log service
     * @param log the log data content
     */
    @Override
    public void writeLog(String log) {
       this.writeLog(log,"default");
    }

    @Override
    public void writeLog(String log, String type) {
        /**
         * get file path
         */
        String file = this.getFilePathByTimeStamp() + "_" + type + ".log";
        try {
            BufferedSink bufferedSink = Okio.buffer( Okio.appendingSink( new File( file ) ) );
            //write log here
            try {
                bufferedSink.writeUtf8( log );
                bufferedSink.flush();
                //close the log
                bufferedSink.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
