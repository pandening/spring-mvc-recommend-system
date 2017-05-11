package com.hujian.star.services;

import com.hujian.star.model.UserEntry;
import com.hujian.star.model.UserUpdateEntry;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by hujian on 2017/5/8.
 */
public interface UserEntryServices extends Serializable {

    /**
     * get the user id by the user id
     * @param user_id the user id
     * @return the user entry if exists,null means no this user
     */
    UserEntry getUserById(Integer user_id);

    /**
     * get the user by the nick name
     * @param user_nick_name
     * @return
     */
    UserEntry getUserByNickName( String user_nick_name );

    /**
     * insert an user into database
     * @param userEntry the user entry
     */
    Boolean insertUser(Map<String,String> userEntry);

    /**
     * update the user
     * @param userEntry  the new user info
     * @return return the old user info(if this is the first,return the new user)
     */
    UserEntry updateUser(UserUpdateEntry userEntry);

}
