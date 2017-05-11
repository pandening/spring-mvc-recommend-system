package com.hujian.star.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hujian on 2017/5/8.
 */
public class WorkEntry implements Serializable {

    /**
     * the work id
     */
    private BigInteger work_id = null;

    /**
     * the work name
     */
    private String work_name = null;

    /**
     * owner
     */
    private Integer work_of_user_id = null;

    /**
     * the work type
     */
    private Integer work_type = null;

    /**
     * the url path
     */
    private String work_url = null;

    /**
     * the size (Mb)
     */
    private Double work_size = null;

    /**
     * the date
     */
    private Date work_date = null;

    /**
     * hot score
     */
    private Double work_hot_value = null;

    /**
     * like count
     */
    private Double work_like_count = null;

    /**
     * dislike count
     */
    private Double work_dislike_count = null;



    public BigInteger getWork_id() {
        return work_id;
    }

    public void setWork_id(BigInteger work_id) {
        this.work_id = work_id;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public Integer getWork_of_user_id() {
        return work_of_user_id;
    }

    public void setWork_of_user_id(Integer work_of_user_id) {
        this.work_of_user_id = work_of_user_id;
    }

    public Integer getWork_type() {
        return work_type;
    }

    public void setWork_type(Integer work_type) {
        this.work_type = work_type;
    }

    public String getWork_url() {
        return work_url;
    }

    public void setWork_url(String work_url) {
        this.work_url = work_url;
    }

    public Double getWork_size() {
        return work_size;
    }

    public void setWork_size(Double work_size) {
        this.work_size = work_size;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public Double getWork_like_count() {
        return work_like_count;
    }

    public void setWork_like_count(Double work_like_count) {
        this.work_like_count = work_like_count;
    }

    public Double getWork_dislike_count() {
        return work_dislike_count;
    }

    public void setWork_dislike_count(Double work_dislike_count) {
        this.work_dislike_count = work_dislike_count;
    }

    public Double getWork_hot_value() {
        return work_hot_value;
    }

    public void setWork_hot_value(Double work_hot_value) {
        this.work_hot_value = work_hot_value;
    }
}
