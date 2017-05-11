package com.hujian.star.model;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/8.
 */
public class InsertUserEntry implements Serializable {

    private String user_nick_name= null;
    private String user_email = null;

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
