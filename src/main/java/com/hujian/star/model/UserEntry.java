package com.hujian.star.model;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/8.
 */
public class UserEntry implements Serializable {

    /**
     * user id
     */
    private Integer user_id = null;

    /**
     * nick name
     */
    private String user_nick_name = null;

    /**
     * user type
     */
    private Integer user_type = null;

    /**
     * user sex
     */
    private String user_sex = null;

    /**
     * user phone
     */
    private String user_phone = null;

    /**
     * user email
     */
    private String user_email = null;

    /**
     * user level
     */
    private Integer user_level = null;

    /**
     * user online/offline status
     */
    private Boolean user_status = null;

    /**
     * user score for level
     */
    private Double user_score = null;

    /**
     * the credit for user
     */
    private Double user_credit_score = null;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Integer getUser_level() {
        return user_level;
    }

    public void setUser_level(Integer user_level) {
        this.user_level = user_level;
    }

    public Boolean getUser_status() {
        return user_status;
    }

    public void setUser_status(Boolean user_status) {
        this.user_status = user_status;
    }

    public Double getUser_score() {
        return user_score;
    }

    public void setUser_score(Double user_score) {
        this.user_score = user_score;
    }

    public Double getUser_credit_score() {
        return user_credit_score;
    }

    public void setUser_credit_score(Double user_credit_score) {
        this.user_credit_score = user_credit_score;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }
}
