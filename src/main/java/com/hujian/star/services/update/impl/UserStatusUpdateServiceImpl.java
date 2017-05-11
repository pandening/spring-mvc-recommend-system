package com.hujian.star.services.update.impl;

import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import com.hujian.star.mapper.UserStatusUpdateMapper;
import com.hujian.star.services.update.UserStatusUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Created by hujian on 2017/5/11.
 */
@Service
public class UserStatusUpdateServiceImpl implements UserStatusUpdateServices {

    /**
     * this is the user update mapper by mybatis
     */
    @Autowired
    private UserStatusUpdateMapper userStatusUpdateMapper = null;

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

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
     * this is the part config object of this project
     */
    @Autowired
    private ConfigTemplate configTemplate = null;

    /**
     * update user status
     * @param user_id
     * @param status
     */
    @Override
    public void updateUserStatus(Integer user_id, Boolean status) {
        assert user_id != null && status != null;
        this.userStatusUpdateMapper.updateUserStatus( user_id,status );
    }

    /**
     * update the user credit score
     * @param user_id the user
     * @param factor the change part ( +- )
     */
    @Override
    public void updateUserCreditScore(Integer user_id, Double factor) {
        assert user_id != null && factor != null;
        this.userStatusUpdateMapper.updateUserCreditScore(user_id,factor);
        //if the user's credit is 0,just ban it
        Double credit = this.getUserCreditSore( user_id );
        if( credit == null ){
            return;
        }else{
            if( credit <= 0 ){
                //push into redis's negative-credit-user entry space
                this.redisTemplate.execute(new RedisCallback() {
                    @Override
                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        redisSerializer = redisTemplate.getStringSerializer();
                        String log = null;
                        String key = configTemplate.getRedis_negative_credit_user_entry_key();
                        if( key == null ){
                            log += LogUtils.getTime()+"\tget empty negative credit user " +
                                    "redis key,using default key:[negative_credit_user_entry]\n";
                            key = "negative_credit_user_entry";
                            sampleLocalLogHandler.writeLog(log,"warn_log");
                        }

                        log = LogUtils.getTime() + "\tget the negative credit user key,using " +
                                "set of redis record the user:[" + user_id + "]\n";
                        sampleLocalLogHandler.writeLog(log,"info_log");

                        byte[] k = redisSerializer.serialize( key );
                        byte[] members = redisSerializer.serialize( user_id );

                        //using set.
                        redisConnection.sAdd(k,members);

                        return null;
                    }
                });
            }
        }
    }

    /**
     * update user level score
     * @param user_id
     * @param factor (+-)
     */
    @Override
    public void updateUserLevelScore(Integer user_id, Double factor) {
        assert user_id != null && factor != null;
        this.userStatusUpdateMapper.updateUserLevelScore(user_id,factor);
    }

    /**
     * update the user level
     * @param user_id
     */
    @Override
    public void increaseUserLevel(Integer user_id) {
        assert user_id != null;
        this.userStatusUpdateMapper.increaseUserLevel(user_id);
    }

    /**
     * get the user credit score
     * @param user_id
     * @return
     */
    @Override
    public Double getUserCreditSore(Integer user_id) {
        if( user_id == null ){
            return null;
        }
        return this.userStatusUpdateMapper.getUserCreditSore( user_id );
    }

    /**
     * get the user level score
     * @param user_id
     * @return
     */
    @Override
    public Double getUserLevelScoreNow(Integer user_id) {
        if( user_id == null ){
            return null;
        }
        return this.userStatusUpdateMapper.getUserLevelScoreNow( user_id);
    }

}
