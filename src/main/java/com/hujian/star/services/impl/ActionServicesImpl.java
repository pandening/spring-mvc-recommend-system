package com.hujian.star.services.impl;

import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.services.utils.ItemUserScoreChangeServicesUtil;
import com.hujian.star.services.ActionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/9.
 */
@Service
public class ActionServicesImpl implements ActionServices {

    /**
     * some configs
     */
    @Autowired
    private ConfigTemplate configTemplate = null;

    @Autowired
    private ItemUserScoreChangeServicesUtil itemUserScoreChangeServicesUtil = null;

    /**
     * like services
     * @param work_id
     * @param user_id
     */
    @Override
    public void userLikeWorkActionService(BigInteger work_id, Integer user_id) {
        Double factor = configTemplate.getLike_factor();
        if( factor == null ){
            factor = 1.0;
        }
        //update the redis
        itemUserScoreChangeServicesUtil.doChangeScoreActionAtRedis(work_id,user_id,factor);
    }

    /**
     * dislike services
     * @param work_id
     * @param user_id
     */
    @Override
    public void userDislikeWorkActionService(BigInteger work_id, Integer user_id) {
        //if exists
        Double factor = configTemplate.getDislike_factor();
        if( factor == null ){
            factor = -1.0;
        }
        //update the redis
        itemUserScoreChangeServicesUtil.doChangeScoreActionAtRedis(work_id,user_id,factor);
    }

    /**
     * hit and look the work
     * @param work_id
     * @param user_id
     */
    @Override
    public void userHitAndLookWorkActionService(BigInteger work_id, Integer user_id) {
        //if exists
        Double factor = configTemplate.getHit_factor();
        if( factor == null ){
            factor = 0.1;
        }
        //update the redis
        itemUserScoreChangeServicesUtil.doChangeScoreActionAtRedis(work_id,user_id,factor);
    }

    /**
     * give comment to the work
     * @param work_id
     * @param user_id
     */
    @Override
    public void userPostCommentActionServices(BigInteger work_id, Integer user_id) {
        //if exists
        Double factor = configTemplate.getComment_factor();
        if( factor == null ){
            factor = 0.05;
        }
        //update the redis
        itemUserScoreChangeServicesUtil.doChangeScoreActionAtRedis(work_id,user_id,factor);
    }

    /**
     * forward the work
     * @param work_id
     * @param user_id
     */
    @Override
    public void userForwardWorkActionServices(BigInteger work_id, Integer user_id) {
        //if exists
        Double factor = configTemplate.getForward_factor();
        if( factor == null ){
            factor = 0.1;
        }
        //update the redis
        itemUserScoreChangeServicesUtil.doChangeScoreActionAtRedis(work_id,user_id,factor);
    }


}
