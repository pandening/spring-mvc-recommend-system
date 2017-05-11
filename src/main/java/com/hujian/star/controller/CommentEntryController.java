package com.hujian.star.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.model.CommentEntry;
import com.hujian.star.services.CommentEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by hujian on 2017/5/9.
 */
@Controller
public class CommentEntryController implements Serializable {

    /**
     * this is the comment services
     */
    @Autowired
    private CommentEntryServices commentEntryServices = null;

    /**
     * the json tools
     */
    @Autowired
    private ObjectMapper jsonMapper = null;

    /**
     * get the work's comment by the from user
     * @param from_user_id
     * @param work_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "comment/getf/{from_user_id}/{work_id}",method = RequestMethod.GET)
    public String getCommentListByFromUser(@PathVariable Integer from_user_id, @PathVariable BigInteger work_id)
            throws JsonProcessingException {
        List<CommentEntry> commentEntryList =
                this.commentEntryServices.getCommentListByFromUser(from_user_id,work_id);
        if( commentEntryList == null || commentEntryList.size() == 0){
            return new String("no comments of user:" + from_user_id + " at work:" + work_id);
        }else{
            return jsonMapper.writeValueAsString( commentEntryList );
        }

    }

    /**
     * get the work's comment list by the work id
     * @param work_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "comment/gets/{work_id}",method = RequestMethod.GET)
    public String getCommentListByWorkId(@PathVariable BigInteger work_id)
            throws JsonProcessingException {
        List<CommentEntry> commentEntryList =
                this.commentEntryServices.getCommentListByWorkId( work_id );
        if( commentEntryList == null || commentEntryList.size() == 0 ){
            return new String("no comments at work:"+work_id);
        }else{
            return jsonMapper.writeValueAsString( commentEntryList );
        }
    }

    /**
     * forward to comment_post.jsp
     * @return
     */
    @RequestMapping(value = "comment/post",method = RequestMethod.GET)
    public  String post(){
        return "comment_post";
    }

    /**
     * post a comment
     * @param comment
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "comment/post.do",method = RequestMethod.POST)
    public String postComment(String comment) throws IOException {
        Boolean result = this.commentEntryServices.postComment( comment );
        if( result == false ){
            return new String("comment post error");
        }else{
            return new String("comment post succeed");
        }
    }


}
