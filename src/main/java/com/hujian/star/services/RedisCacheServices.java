package com.hujian.star.services;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
public interface RedisCacheServices extends Serializable {

    /**
     * the services will copy the work entry from mysql to redis
     * @param work_id
     */
    void copyWorkEntryFromDaoToCache(BigInteger work_id);

}
