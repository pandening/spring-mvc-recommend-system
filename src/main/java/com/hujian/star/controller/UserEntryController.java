package com.hujian.star.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.model.InsertUserEntry;
import com.hujian.star.model.UserEntry;
import com.hujian.star.model.UserUpdateEntry;
import com.hujian.star.services.UserEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hujian on 2017/5/8.
 */
@Controller
public class UserEntryController implements Serializable {

    /**
     * this is the user services
     */
    @Autowired
    private UserEntryServices userEntryServices = null;

    /**
     * json convert
     */
    @Autowired
    private ObjectMapper jsonMapper = null;


    /**
     * get the user by the user id
     * @param user_id
     * @return the user json string
     */
    @RequestMapping(value = "user/get/{user_id}",method = RequestMethod.GET)
    @ResponseBody
    String getUserEntryById(@PathVariable Integer user_id) throws JsonProcessingException {
        UserEntry userEntry = this.userEntryServices.getUserById( user_id );
        if( userEntry == null ){
            return new String("no such user");
        }else{
            return jsonMapper.writeValueAsString(userEntry);
        }
    }

    /**
     * get the user info by the user nick name
     * @param user_nick_name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/gets/{user_nick_name}",method = RequestMethod.GET)
    String getUserByNickName(@PathVariable String user_nick_name) throws JsonProcessingException {
        UserEntry userEntry = this.userEntryServices.getUserByNickName( user_nick_name  );
        if( userEntry == null ){
            return new String("no such user named:"+user_nick_name);
        }else{
            return jsonMapper.writeValueAsString(userEntry);
        }
    }

    /**
     * forward to "add.jsp"
     * @return
     */
    @RequestMapping(value = "user/add")
    String insert(){
        return "add";
    }

    @RequestMapping(value = "user/update")
    String update(){
        return "update";
    }

    /**
     * insert an new user into mysql
     * @param userEntry
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/add/insert.do",method = RequestMethod.POST)
    String insertUser(InsertUserEntry userEntry){
        if( userEntry == null ){
            return new String("null information");
        }else{
            Map<String,String> map = new HashMap<>();
            map.put("user_nick_name",userEntry.getUser_nick_name());
            map.put("user_email",userEntry.getUser_email());
            boolean result = this.userEntryServices.insertUser(map);
            if( result == false ){
                return new String("insert error");
            }else{
                return new String("insert succeed");
            }
        }
    }

    /**
     * update the user info by the nick name
     * @param userUpdateEntry
     * @return the old user info
     */
    @ResponseBody
    @RequestMapping(value = "user/change.do",method = RequestMethod.POST)
    String updateUserInfo(UserUpdateEntry userUpdateEntry) throws JsonProcessingException {
        if( userUpdateEntry == null ){
            return new String("empty new information");
        }else{
            UserEntry userEntry = this.userEntryServices.updateUser( userUpdateEntry );
            if( userEntry == null ){
                return new String("no user named:" + userUpdateEntry.getUser_nick_name());
            }else{
                return jsonMapper.writeValueAsString( userEntry );
            }
        }
    }

}
