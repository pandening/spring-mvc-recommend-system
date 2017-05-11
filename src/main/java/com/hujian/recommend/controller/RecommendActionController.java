package com.hujian.recommend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.recommend.services.RecommendServices;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import net.librec.recommender.item.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hujian on 2017/5/10.
 */
@Controller
public class RecommendActionController implements Serializable {

    /**
     * this is the recommend services
     */
    @Autowired
    private RecommendServices recommendServices = null;

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

    /**
     * the json tools
     */
    @Autowired
    private ObjectMapper jsonMapper = null;

    /**
     * get the recommend list ( using user-knn algorithm )
     * @param user_id the user id
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value =  "r/uknn/{user_id}",method = RequestMethod.GET)
    public String userKNNRecommendAction(@PathVariable Integer user_id, HttpServletRequest httpServletRequest)
            throws JsonProcessingException
    {
        /**
         * get the result
         */
        List<RecommendedItem> recommendedItemList =
                this.recommendServices.UserKNNRecommendAct(user_id);

        String log = LogUtils.getTime()+"\t";
        log += httpServletRequest.getRequestURI()+"\t";
        log += "recommend_user_knn_ac\t" + user_id + "\t";

        if( recommendedItemList == null ){
            log += "0\n";
        }else{
            log += recommendedItemList.size() + "\n";
        }

        //write the log
        this.sampleLocalLogHandler.writeLog( log );

        return jsonMapper.writeValueAsString(recommendedItemList);
    }

}
