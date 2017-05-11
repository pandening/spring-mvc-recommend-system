package com.hujian.star.services.update.impl;

import com.hujian.star.mapper.CommentStatusUpdateMapper;
import com.hujian.star.services.update.CommentStatusUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Service
public class CommentStatusUpdateServicesImpl implements CommentStatusUpdateServices {

    /**
     * this is the Dao services mapper ( mybatis )
     */
    @Autowired
    private CommentStatusUpdateMapper commentStatusUpdateMapper = null;

    /**
     * change the dao update
     * @param comment_id  the comment.
     * @param status the new status
     */
    @Override
    public void updateCommentStatus(BigInteger comment_id, Boolean status) {
        if( comment_id == null || status == null ){
            return;
        }
        this.commentStatusUpdateMapper.updateCommentStatus(comment_id,status);
    }

}
