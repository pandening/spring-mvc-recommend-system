package com.hujian.recommend.crontab;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/10.
 */
public interface CrontabServices extends Serializable {


    /**
     * generate the origin data for recommender
     */
    void generatorRecommendOriginDataCrontab();

}
