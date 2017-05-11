package com.hujian.star.services.update.impl;

import com.hujian.star.mapper.WorkStatusUpdateMapper;
import com.hujian.star.services.update.WorkStatusUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Service
public class WorkStatusUpdateServicesImpl implements WorkStatusUpdateServices {

    /**
     * this is the work status update mapper by mybatis
     */
    @Autowired
    private WorkStatusUpdateMapper workStatusUpdateMapper = null;

    /**
     * change the url
     * @param work_id
     * @param url the new url
     */
    @Override
    public void changeWorkUrl(BigInteger work_id, String url) {
        assert work_id != null && url != null;
        this.workStatusUpdateMapper.changeWorkUrl( work_id,url );
    }

    /**
     * change the hot value
     * @param work_id
     * @param factor
     */
    @Override
    public void updateWorkHotValue(BigInteger work_id, Double factor) {
        if( work_id == null || factor == null ){
            return;
        }
        this.workStatusUpdateMapper.updateWorkHotValue(work_id,factor);
    }

    /**
     * change the like count
     * @param work_id
     * @param change
     */
    @Override
    public void updateWorkLikeCount(BigInteger work_id, Integer change) {
        assert work_id != null && change != null;
        this.workStatusUpdateMapper.updateWorkLikeCount(work_id,change);
    }

    /**
     * change the dislike count
     * @param work_id
     * @param change
     */
    @Override
    public void updateWorkDislikeCount(BigInteger work_id, Integer change) {
        assert work_id != null && change != null;
        this.workStatusUpdateMapper.updateWorkDislikeCount( work_id,change );
    }

    /**
     * get the work's hot value now
     * @param work_id
     * @return
     */
    @Override
    public Double getWorkHotValue(BigInteger work_id) {
        if( work_id == null ){
            return null;
        }
        return this.workStatusUpdateMapper.getWorkHotValue( work_id );
    }

    /**
     * change the work type
     * @param work_id
     * @param newType
     */
    @Override
    public void updateWorkType(BigInteger work_id, Integer newType) {
        if( work_id == null || newType == null ){
            return;
        }
        this.workStatusUpdateMapper.updateWorkType(work_id,newType);
    }

}
