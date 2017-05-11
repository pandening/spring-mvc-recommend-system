package com.hujian.star.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/8.
 */
public class UserUpdateEntry implements Serializable {

    /**
     * the user nick name(old)
     */
    @Autowired
    private String user_nick_name = null;

    /**
     * if change,null means you want not update the nick name
     */
    @Autowired
    private String new_nick_name = null;

    /**
     * the new e-mail
     */
    @Autowired
    private String new_email = null;

    /**
     * the new phone number
     */
    @Autowired
    private String new_phone = null;

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getNew_nick_name() {
        return new_nick_name;
    }

    public void setNew_nick_name(String new_nick_name) {
        this.new_nick_name = new_nick_name;
    }

    public String getNew_email() {
        return new_email;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
    }

    public String getNew_phone() {
        return new_phone;
    }

    public void setNew_phone(String new_phone) {
        this.new_phone = new_phone;
    }
}
