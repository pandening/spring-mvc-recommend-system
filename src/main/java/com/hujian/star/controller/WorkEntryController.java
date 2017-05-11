package com.hujian.star.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.model.InsertWorkEntry;
import com.hujian.star.model.WorkEntry;
import com.hujian.star.services.WorkEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujian on 2017/5/9.
 */
@Controller
public class WorkEntryController implements Serializable{

    /**
     * this is the work services
     */
    @Autowired
    private WorkEntryServices workEntryServices = null;

    /**
     * json tools
     */
    @Autowired
    private ObjectMapper jsonMapper = null;

    /**
     * get the work entry by the work id
     * @param work_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "work/get/{work_id}",method = RequestMethod.GET)
    public String getWorkById(@PathVariable Integer work_id) throws JsonProcessingException {
        WorkEntry workEntry = this.workEntryServices.getWorkByWorkId( work_id );
        if( workEntry == null ){
            return new String("no such work with work_id :" +work_id);
        }else{
            return jsonMapper.writeValueAsString( workEntry );
        }
    }

    /**
     * get the work list by the user id
     * @param user_id the user id
     * @param limit how many items
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "work/get/{user_id}/{limit}",method = RequestMethod.GET)
    public String getWorkListByOfUserId(@PathVariable Integer user_id,@PathVariable Integer limit)
            throws JsonProcessingException {
        List<WorkEntry> workEntryList = this.workEntryServices.getWorkByOfUserId( user_id,limit );
        if( workEntryList == null || workEntryList.size() == 0 ){
            return new String("empty result");
        }else{
            return jsonMapper.writeValueAsString( workEntryList );
        }
    }

    /**
     * forward to insert_work.jsp
     * @return
     */
    @RequestMapping(value = "work/add")
    public String insert_work(){
        return "insert_work";
    }

    /**
     * insert work
     * @param workEntry
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "work/insert_work.do",method = RequestMethod.POST)
    public String insertWork(InsertWorkEntry workEntry){
        Boolean result = this.workEntryServices.insertWork( workEntry );
        if( result == false ){
            return new String("insert error");
        }else{
            return new String("insert into mysql succeed");
        }
    }


}
