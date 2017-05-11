package com.hujian.star.mapper;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Repository
public interface WorkStatusUpdateMapper extends Serializable {

    /**
     * change the work's url in cloud
     * @param work_id
     * @param url
     */
    void changeWorkUrl(BigInteger work_id,String url);

    /**
     * update the work's factor
     * @param work_id
     * @param factor
     */
    void updateWorkHotValue(BigInteger work_id,Double factor);

    /**
     * change the work's like count
     * @param work_id
     * @param change
     */
    void updateWorkLikeCount(BigInteger work_id,Integer change);

    /**
     * update the work's dislike count
     * @param work_id
     * @param change
     */
    void updateWorkDislikeCount(BigInteger work_id,Integer change);

    /**
     * get the work's hot value
     * @param work_id
     * @return
     */
    Double getWorkHotValue(BigInteger work_id);

    /**
     * change the type,such as 0 means ban it.
     * @param work_id
     * @param newType
     */
    void updateWorkType(BigInteger work_id,Integer newType);

}
