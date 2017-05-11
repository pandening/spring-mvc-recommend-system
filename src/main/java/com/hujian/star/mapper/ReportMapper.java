package com.hujian.star.mapper;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Repository
public interface ReportMapper extends Serializable {

    /**
     * report user
     * @param from_user_id
     * @param to_user_id
     * @param why
     */
    void reportUser(Integer from_user_id,Integer to_user_id,String why);

    /**
     * report the comment
     * @param from_user_id
     * @param comment_id
     * @param why
     */
    void reportComment(Integer from_user_id, BigInteger comment_id,String why);

    /**
     * report the work
     * @param from_user_id
     * @param work_id
     * @param why
     */
    void reportWork(Integer from_user_id,BigInteger work_id,String why);

}
