package com.hujian.recommend.crontab.impl;

import com.hujian.recommend.crontab.CrontabServices;
import com.hujian.recommend.data.impl.GeneratorRecommendOriginSourceServicesImpl;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by hujian on 2017/5/10.
 */
@Component
public class CrontabServicesImpl implements CrontabServices {

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

    /**
     * this is the services
     */
    @Autowired
    private GeneratorRecommendOriginSourceServicesImpl sourceServices = null;

    /**
     * do the flush data job
     */
    @Scheduled(cron = "0/60 * *  * * ? ")
    @Override
    public void generatorRecommendOriginDataCrontab() {
        this.sourceServices.generate();
        String log = LogUtils.getTime() + "\t [DEBUG]{" + CrontabServicesImpl.class.getName() +
                "}\t generate once done\n";
        sampleLocalLogHandler.writeLog(log);
    }
}
