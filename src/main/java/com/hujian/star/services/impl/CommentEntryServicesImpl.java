package com.hujian.star.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.mapper.CommentEntryMapper;
import com.hujian.star.model.CommentEntry;
import com.hujian.star.services.CommentEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by hujian on 2017/5/9.
 */
@Service
public class CommentEntryServicesImpl implements CommentEntryServices {

    /**
     * this is the comment mapper(mybatis)
     */
    @Autowired
    private CommentEntryMapper commentEntryMapper = null;

    /**
     * this is the json mapper
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * get the from user id 's comment list at work id
     * @param from_user_id
     * @param work_id
     * @return the list
     */
    @Override
    public List<CommentEntry> getCommentListByFromUser(Integer from_user_id, BigInteger work_id) {
        if( from_user_id == null || work_id == null ){
            return null;
        }
        return this.commentEntryMapper.getCommentListByFromUser( from_user_id,work_id );
    }

    /**
     * get the work's comment list
     * @param work_id the work id
     * @return
     */
    @Override
    public List<CommentEntry> getCommentListByWorkId(BigInteger work_id) {
        if( work_id == null ){
            return null;
        }
        return this.commentEntryMapper.getCommentListByWorkId( work_id );
    }

    /**
     * post the comment by jaon string
     * @param jsonComment  the json string
     * @return
     */
    @Override
    public Boolean postComment(String jsonComment) throws IOException {
        if( jsonComment == null ){
            return false;
        }
        /**
         * get the comment entry and post it
         */
        CommentEntry commentEntry = mapper.readValue(jsonComment, CommentEntry.class);
        if( commentEntry == null ){
            return false;
        }
        this.commentEntryMapper.postComment( commentEntry );
        return true;
    }


}
