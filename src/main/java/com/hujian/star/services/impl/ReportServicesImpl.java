package com.hujian.star.services.impl;

import com.hujian.star.mapper.ReportMapper;
import com.hujian.star.services.ReportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Service
public class ReportServicesImpl implements ReportServices {

    /**
     * this is the report services mapper from mybatis
     */
    @Autowired
    private ReportMapper reportMapper = null;

    /**
     * report user
     * @param from_user_id
     * @param to_user_id
     * @param why
     */
    @Override
    public void reportUser(Integer from_user_id, Integer to_user_id, String why) {
        assert from_user_id != null && to_user_id != null && why != null;
        this.reportMapper.reportUser( from_user_id,to_user_id,why );
    }

    /**
     * report comment
     * @param from_user_id
     * @param comment_id
     * @param why
     */
    @Override
    public void reportComment(Integer from_user_id, BigInteger comment_id, String why) {
        assert from_user_id != null && comment_id != null && why != null;
        this.reportMapper.reportComment( from_user_id,comment_id,why );
    }

    /**
     * report work
     * @param from_user_id
     * @param work_id
     * @param why
     */
    @Override
    public void reportWork(Integer from_user_id, BigInteger work_id, String why) {
        assert from_user_id != null && work_id != null && why != null;
        this.reportMapper.reportWork(from_user_id,work_id,why);
    }

}
