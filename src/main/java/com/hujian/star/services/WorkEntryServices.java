package com.hujian.star.services;

import com.hujian.star.model.InsertWorkEntry;
import com.hujian.star.model.WorkEntry;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by hujian on 2017/5/9.
 */
public interface WorkEntryServices extends Serializable {

    /**
     * get the work entry by the work id
     * @param work_id  the work id
     * @return the work id,null means no such work now
     */
    WorkEntry getWorkByWorkId(Integer work_id);

    /**
     * get the user'd work list
     * @param user_id
     * @param limit  how many
     * @return the work entry list
     */
    List<WorkEntry> getWorkByOfUserId(Integer user_id, Integer limit);

    /**
     * insert a work entry into the database
     * @param workEntry
     * @return true or false
     */
    Boolean insertWork(InsertWorkEntry workEntry);

    /**
     * i think we need to use this function.
     * @param work_id
     * @return
     */
    WorkEntry getWorkByWorkIdEx(BigInteger work_id);

}
