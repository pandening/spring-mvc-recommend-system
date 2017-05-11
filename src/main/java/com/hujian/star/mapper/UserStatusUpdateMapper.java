package com.hujian.star.mapper;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/11.
 */
@Repository
public interface UserStatusUpdateMapper extends Serializable {

    /**
     * update the user status
     * @param user_id
     * @param status
     */
    void updateUserStatus(Integer user_id,Boolean status);

    /**
     * update the user credit score ( old + factor )
     * @param user_id the user
     * @param factor the change part ( +- )
     */
    void updateUserCreditScore(Integer user_id,Double factor);

    /**
     * update the user level score
     * @param user_id
     * @param factor (+-)
     */
    void updateUserLevelScore(Integer user_id,Double factor);

    /**
     * just increase the user's level ( ++ 1)
     * @param user_id
     */
    void increaseUserLevel(Integer user_id);

    /**
     * get the user's credit score
     * @param user_id
     * @return
     */
    Double getUserCreditSore(Integer user_id);

    /**
     * get the user's level score
     * @param user_id
     * @return
     */
    Double getUserLevelScoreNow(Integer user_id);

}
