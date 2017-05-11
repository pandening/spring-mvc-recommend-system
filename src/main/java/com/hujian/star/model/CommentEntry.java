package com.hujian.star.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hujian on 2017/5/8.
 */
public class CommentEntry implements Serializable {

    /**
     * who push this comment
     */
    private Integer from_user_id = null;

    /**
     * comment to which comment
     */
    private Integer to_user_id = null;

    /**
     * the comment work id
     */
    private BigInteger  work_id = null;

    /**
     * the comment content
     */
    private String comment_content = null;

    /**
     * hide or not
     */
    private Boolean comment_status = null;

    /**
     * the comment time
     */
    private Date comment_date = null;


    public Integer getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(Integer from_user_id) {
        this.from_user_id = from_user_id;
    }

    public Integer getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(Integer to_user_id) {
        this.to_user_id = to_user_id;
    }

    public BigInteger getWork_id() {
        return work_id;
    }

    public void setWork_id(BigInteger work_id) {
        this.work_id = work_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public Boolean getComment_status() {
        return comment_status;
    }

    public void setComment_status(Boolean comment_status) {
        this.comment_status = comment_status;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }
}
