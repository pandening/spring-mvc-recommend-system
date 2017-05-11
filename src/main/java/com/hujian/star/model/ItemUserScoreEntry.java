package com.hujian.star.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/9.
 */
public class ItemUserScoreEntry implements Serializable {

    /**
     * the work id(item)
     */
    private BigInteger work_id = null;

    /**
     * the user id
     */
    private Integer user_id = null;

    /**
     * the score
     */
    private Double score = null;

    /**
     * to string
     * @return
     */
    @Override
    public String toString(){
        return this.user_id + " " + this.work_id + " " + this.score;
    }

    public BigInteger getWork_id() {
        return work_id;
    }

    public void setWork_id(BigInteger work_id) {
        this.work_id = work_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
