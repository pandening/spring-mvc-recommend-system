package com.hujian.star.model;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/9.
 */
public class InsertWorkEntry implements Serializable {

    /**
     * the work name
     */
    private String work_name = null;

    /**
     * the user id
     */
    private Integer work_of_user_id = null;

    /**
     * work type
     */
    private Integer work_type = null;


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
}
