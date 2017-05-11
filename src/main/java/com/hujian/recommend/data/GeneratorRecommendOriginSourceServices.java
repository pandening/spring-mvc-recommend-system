package com.hujian.recommend.data;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/10.
 */
public interface GeneratorRecommendOriginSourceServices extends Serializable{

    /**
     * generate the recommend data from redis cache
     */
    void generate();
}
