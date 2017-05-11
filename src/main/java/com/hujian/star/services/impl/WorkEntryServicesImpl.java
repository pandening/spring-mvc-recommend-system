package com.hujian.star.services.impl;

import com.hujian.star.mapper.WorkEntryMapper;
import com.hujian.star.model.InsertWorkEntry;
import com.hujian.star.model.WorkEntry;
import com.hujian.star.services.WorkEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by hujian on 2017/5/9.
 */
@Service
public class WorkEntryServicesImpl implements WorkEntryServices {

    /**
     * this is the work mapper.
     * @Repository
     */
    @Autowired
    private WorkEntryMapper workEntryMapper = null;

    /**
     * get the work by the work id
     * @param work_id  the work id
     * @return the work entry , null means no such work id
     */
    @Override
    public WorkEntry getWorkByWorkId(Integer work_id) {
        if( work_id == null ){
            return null;
        }
        return this.workEntryMapper.getWorkByWorkId( work_id );
    }

    /**
     * get the work list
     * @param user_id the user id
     * @param limit  how many
     * @return the list
     */
    @Override
    public List<WorkEntry> getWorkByOfUserId(Integer user_id, Integer limit) {
        if( user_id == null || limit == null || limit <= 0 ){
            return null;
        }
        return this.workEntryMapper.getWorkByOfUserId( user_id,limit );
    }

    /**
     * insert a work into mysql
     * @param workEntry
     * @return
     */
    @Override
    public Boolean insertWork(InsertWorkEntry workEntry) {
        if( workEntry == null || workEntry.getWork_name() == null
                || workEntry.getWork_type() == null || workEntry.getWork_of_user_id() == null ){
            return false;
        }
        this.workEntryMapper.insertWork( workEntry );
        return true;
    }

    /**
     * get work by work id
     * @param work_id
     * @return
     */
    @Override
    public WorkEntry getWorkByWorkIdEx(BigInteger work_id) {
        assert work_id != null;
        return this.workEntryMapper.getWorkByWorkIdEx( work_id );
    }
}
