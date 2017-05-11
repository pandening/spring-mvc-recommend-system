package com.hujian.star.services;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/9.
 */
public interface ActionServices extends Serializable {

    /**
     * the user like the work
     * @param work_id
     * @param user_id
     */
    void userLikeWorkActionService(BigInteger work_id,Integer user_id);

    /**
     * dis like action
     * @param work_id
     * @param user_id
     */
    void userDislikeWorkActionService(BigInteger work_id,Integer user_id);

    /**
     * hit the work and look it
     * @param work_id
     * @param user_id
     */
    void userHitAndLookWorkActionService(BigInteger work_id,Integer user_id);

    /**
     * give comment to work
     * @param work_id
     * @param user_id
     */
    void userPostCommentActionServices(BigInteger work_id,Integer user_id);

    /**
     * forward the work action
     * @param work_id
     * @param user_id
     */
    void userForwardWorkActionServices(BigInteger work_id,Integer user_id);


}
