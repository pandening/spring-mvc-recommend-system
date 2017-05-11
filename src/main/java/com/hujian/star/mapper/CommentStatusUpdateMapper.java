package com.hujian.star.mapper;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/11.
 */
@Repository
public interface CommentStatusUpdateMapper extends Serializable {

    /**
     * change the status's of one comment
     * @param status the new status
     * @param comment_id  the comment.
     */
    void updateCommentStatus(BigInteger comment_id, Boolean status);

}
