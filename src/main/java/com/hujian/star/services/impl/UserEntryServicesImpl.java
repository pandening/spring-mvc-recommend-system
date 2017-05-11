package com.hujian.star.services.impl;

import com.hujian.star.mapper.UserEntryMapper;
import com.hujian.star.model.UserEntry;
import com.hujian.star.model.UserUpdateEntry;
import com.hujian.star.services.UserEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hujian on 2017/5/8.
 */
@Service
public class UserEntryServicesImpl implements UserEntryServices {

    /**
     * this is the mapper
     */
    @Autowired
    private UserEntryMapper userEntryMapper = null;

    /**
     * get an user from database by the user id
     * @param user_id the user id
     * @return the user entry
     */
    @Override
    public UserEntry getUserById(Integer user_id) {
        if( user_id == null ){
            return null;
        }
        return this.userEntryMapper.getUserById( user_id );
    }

    /**
     * get the user by the user nick name
     * @param user_nick_name
     * @return the user
     */
    @Override
    public UserEntry getUserByNickName(String user_nick_name) {
        if( user_nick_name == null ){
            return null;
        }
        return this.userEntryMapper.getUserByNickName( user_nick_name );
    }

    /**
     * insert an user into the mysql
     * @param userEntry the user entry
     */
    @Override
    public Boolean insertUser(Map<String,String> userEntry) {
        if( userEntry == null ){
            return false;
        }
        if( this.getUserByNickName(userEntry.get("user_nick_name")) != null ){
            return false;
        }
        this.userEntryMapper.insertUser( userEntry );
        return true;
    }

    /**
     * update the user info
     * @param userEntry  the new user info
     * @return return the old user info
     */
    @Override
    public UserEntry updateUser(UserUpdateEntry userEntry) {
        if( userEntry == null ){
            return null;
        }
        UserEntry old = this.getUserByNickName(userEntry.getUser_nick_name());
        if( old == null ){
            return null;
        }
        this.userEntryMapper.updateUser( userEntry );
        return old;
    }

}
