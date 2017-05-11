package com.hujian.star.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.recommend.data.NoDataException;
import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import com.hujian.star.mapper.WorkEntryMapper;
import com.hujian.star.model.WorkEntry;
import com.hujian.star.services.RedisCacheServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Service
public class RedisCacheServicesImpl implements RedisCacheServices {

    /**
     * the redis template
     */
    @Autowired
    private RedisTemplate redisTemplate = null;

    /**
     * the serializer
     */
    private RedisSerializer redisSerializer = null;

    /**
     * the work entry mapper
     */
    @Autowired
    private WorkEntryMapper workEntryMapper = null;

    /**
     * this is the part config object of this project
     */
    @Autowired
    private ConfigTemplate configTemplate = null;

    /**
     * this is the json tools
     */
    @Autowired
    private ObjectMapper jsonMapper = null;

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

    /**
     * copy entry from mysql to redis
     * @param work_id
     */
    @Override
    public void copyWorkEntryFromDaoToCache(BigInteger work_id) {
        assert work_id != null;
        //first of all,get the work entry from mysql
        WorkEntry workEntry = this.workEntryMapper.getWorkByWorkIdEx( work_id );
        String log = "";
        if( workEntry == null ){

            log += LogUtils.getTime() + "\tno work entry exist in mysql,work id = " + work_id + "\n";
            sampleLocalLogHandler.writeLog(log,"run_warn");

            try {
                throw  new NoDataException("no work entry exist in mysql,work id = " + work_id );
            } catch (NoDataException e) {
                e.printStackTrace();
            }
        }else{

            log += LogUtils.getTime() + "\tget the hot work entry from mysql,push into redis now\n";
            sampleLocalLogHandler.writeLog(log,"info_log");

            //pull to work
            this.redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    String work_hot_entry_key = configTemplate.getRedis_work_entry_key();

                    String log = "";

                    if( work_hot_entry_key == null ){

                        log += LogUtils.getTime() +
                                "\tget work entry hot key as null,set default:work_hot_entry_key\n";
                        sampleLocalLogHandler.writeLog(log,"warn_log");

                        work_hot_entry_key = "work_hot_entry_key";
                    }
                    redisSerializer = redisTemplate.getStringSerializer();
                    byte[] key = redisSerializer.serialize( work_hot_entry_key );
                    byte[] field = redisSerializer.serialize(work_id.toString());

                    try {

                        log = LogUtils.getTime() +
                                "\ttry to push the hot work entry into redis now,work_id:" + work_id + "\n";
                        sampleLocalLogHandler.writeLog(log,"info_log");

                        String entry = jsonMapper.writeValueAsString( workEntry );
                        byte[] value = redisSerializer.serialize( entry );
                        //push into redis now
                        redisConnection.hSet(key,field,value);
                    } catch (JsonProcessingException e) {

                        log = LogUtils.getTime() +
                                "\terror while try to push the hot work entry into redis,work id:" + work_id + "\n";
                        sampleLocalLogHandler.writeLog(log,"error_log");

                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }
    }


}
