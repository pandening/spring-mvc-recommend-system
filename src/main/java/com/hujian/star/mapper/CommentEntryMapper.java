package com.hujian.star.mapper;

import com.hujian.star.model.CommentEntry;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by hujian on 2017/5/8.
 */
@Repository
public interface CommentEntryMapper extends Serializable {

    /**
     * get the from_user_id's comment list at work_id
     * @param from_user_id
     * @param work_id
     * @return
     */
    List<CommentEntry> getCommentListByFromUser(Integer from_user_id,BigInteger work_id);

    /**
     * get the work's comment list
     * @param work_id the work id
     * @return the list
     */
    List<CommentEntry> getCommentListByWorkId(BigInteger work_id);

    /**
     * from now,the comment will from json.
     * @param comment
     * @return
     */
    Boolean postComment(CommentEntry comment);


}
