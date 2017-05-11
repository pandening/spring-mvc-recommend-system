package com.hujian.star.controller;

import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import com.hujian.star.services.ActionServices;
import com.hujian.star.services.RedisCacheServices;
import com.hujian.star.services.ReportServices;
import com.hujian.star.services.update.CommentStatusUpdateServices;
import com.hujian.star.services.update.UserStatusUpdateServices;
import com.hujian.star.services.update.WorkStatusUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hujian on 2017/5/9.
 */
@Controller
public class ActionController implements Serializable {

    /**
     * this is the services
     */
    @Autowired
    private ActionServices actionServices = null;

    /**
     * this is the redis cache services
     */
    @Autowired
    private RedisCacheServices redisCacheServices = null;

    /**
     * the user status services
     */
    @Autowired
    private UserStatusUpdateServices userStatusUpdateServices = null;

    /**
     * the comment status update services
     */
    @Autowired
    private CommentStatusUpdateServices commentStatusUpdateServices = null;

    /**
     * the work status comments.
     */
    @Autowired
    private WorkStatusUpdateServices workStatusUpdateServices = null;

    /**
     * for reporting action
     */
    @Autowired
    private ReportServices reportServices = null;

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

    /**
     * this is the part config object of this project
     */
    @Autowired
    private ConfigTemplate configTemplate = null;

    /**
     * report an user
     * @param from_user_id
     * @param to_user_id
     * @param why
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ac/report/user/{from_user_id}/{to_user_id}/{why}",method = RequestMethod.GET)
    public String reportUserAction(@PathVariable Integer from_user_id,
                                   @PathVariable Integer to_user_id,@PathVariable String why){
        assert from_user_id != null && to_user_id != null && why != null;

        //dao update
        this.reportServices.reportUser(from_user_id,to_user_id,why);

        //update the user status.
        Double credit_factor_set = this.configTemplate.getUser_credit_score_report_factor();
        if( credit_factor_set == null ){
            credit_factor_set = 1.0;
        }
        //change the user's credit
        this.userStatusUpdateServices.updateUserCreditScore(to_user_id,credit_factor_set * -1.0);
        this.userStatusUpdateServices.updateUserCreditScore(from_user_id,credit_factor_set);

        return new String("handling this report done.");
    }

    /**
     * report the comment
     * @param from_user_id
     * @param comment_id
     * @param why
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ac/report/comment/{from_user_id}/{comment_id}/{why}",method = RequestMethod.GET)
    public String reportCommentAction(@PathVariable Integer from_user_id,@PathVariable BigInteger comment_id,
                                      @PathVariable String why){
        assert from_user_id != null && comment_id != null && why != null;
        //dao update
        this.reportServices.reportComment(from_user_id,comment_id,why);
        //close this comment
        this.commentStatusUpdateServices.updateCommentStatus(comment_id,false);
        //update the user status.
        Double credit_factor_set = this.configTemplate.getUser_credit_score_report_factor();
        if( credit_factor_set == null ){
            credit_factor_set = 1.0;
        }
        this.userStatusUpdateServices.updateUserCreditScore(from_user_id,credit_factor_set);

        return new String("handling this report done");
    }


    /**
     * report the work
     * @param from_user_id
     * @param work_id
     * @param why
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ac/report/work/{from_user_id}/{work_id}/{why}",method = RequestMethod.GET)
    public String reportWorkAction(@PathVariable Integer from_user_id,@PathVariable BigInteger work_id,
                                   @PathVariable String why){
        assert from_user_id != null && work_id != null && why != null;

        //dao update
        this.reportServices.reportWork(from_user_id,work_id,why);
        //update the user status.
        Double credit_factor_set = this.configTemplate.getUser_credit_score_report_factor();
        if( credit_factor_set == null ){
            credit_factor_set = 1.0;
        }
        this.userStatusUpdateServices.updateUserCreditScore(from_user_id,credit_factor_set);

        //BAN THE WORK
        Integer work_ban_type = this.configTemplate.getWork_ban_type_set();
        if( work_ban_type == null ){
            work_ban_type = 0;
        }
        this.workStatusUpdateServices.updateWorkType(work_id,work_ban_type);

        return new String("handling the report done");
    }

    /**
     * like action.
     * @param work_id the work id
     * @param user_id the user id
     * @param httpServletRequest the request context
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ac/like/{work_id}/{user_id}",method = RequestMethod.GET)
    public String likeWorkAction(@PathVariable BigInteger work_id, @PathVariable Integer user_id,
                                 HttpServletRequest httpServletRequest){
        //cache update
        this.actionServices.userLikeWorkActionService(work_id,user_id);

        //dao update
        this.workStatusUpdateServices.updateWorkLikeCount(work_id,1);
        double factor = this.configTemplate.getLike_factor() == null ?0.5:
                this.configTemplate.getLike_factor();
        this.workStatusUpdateServices.updateWorkHotValue(work_id,factor);


        //if the work 's hot value is full,just pull to cache
        Double hotValue = workStatusUpdateServices.getWorkHotValue( work_id );
        Double hot_value_threshold = this.configTemplate.getWork_hot_value_threshold();
        if( hot_value_threshold == null ){
            hot_value_threshold = 10.0;
        }
        if( hotValue >= hot_value_threshold ){
            //pull the hot work entry into redis now
            this.redisCacheServices.copyWorkEntryFromDaoToCache(work_id);
        }

        String log = LogUtils.getTime() + "\t";
        log += httpServletRequest.getRequestURI()+ "\t";
        log += "like_ac\t";
        log += user_id + "\t";
        log += work_id + "\n";

        //do the log
        this.sampleLocalLogHandler.writeLog(log);

        return new String(log);
    }

    /**
     * dislike action
     * @param work_id
     * @param user_id
     */
    @ResponseBody
    @RequestMapping(value = "ac/dislike/{work_id}/{user_id}",method = RequestMethod.GET)
    public String dislikeWorkAction(@PathVariable BigInteger work_id,@PathVariable Integer user_id,
                                    HttpServletRequest httpServletRequest){
        //cache update
        this.actionServices.userDislikeWorkActionService(work_id,user_id);

        //dao update
        this.workStatusUpdateServices.updateWorkDislikeCount(work_id,1);
        double factor = this.configTemplate.getDislike_factor() == null ? - 0.5:
                this.configTemplate.getDislike_factor();
        this.workStatusUpdateServices.updateWorkHotValue(work_id,factor);

        String log = LogUtils.getTime() + "\t";
        log += httpServletRequest.getRequestURI()+ "\t";
        log += "dislike_ac\t";
        log += user_id + "\t";
        log += work_id + "\n";

        //do the log
        this.sampleLocalLogHandler.writeLog(log);

        return new String(log);
    }

    /**
     * hit and look actions
     * @param work_id
     * @param user_id
     * @return
     */
    @RequestMapping(value = "ac/hit/{work_id}/{user_id}",method = RequestMethod.GET)
    @ResponseBody
    public String hitAndLookAction(@PathVariable BigInteger work_id,@PathVariable Integer user_id,
                                   HttpServletRequest httpServletRequest){
        //cache update
        this.actionServices.userHitAndLookWorkActionService(work_id,user_id);

        //dao update
        Double factor = this.configTemplate.getHit_factor();
        if( factor == null ){
            factor = 0.1;
        }
        this.workStatusUpdateServices.updateWorkHotValue(work_id,factor);
        //if the work 's hot value is full,just pull to cache
        Double hotValue = workStatusUpdateServices.getWorkHotValue( work_id );

        if( hotValue == null ){
            sampleLocalLogHandler.writeLog("error logic at:" +
                    ActionController.class.getName() + " -> the result must have been initialized,but" +
                    "get null result from mysql");
            return new String("Logic error happen at:" + ActionController.class.getName());
        }

        Double hot_value_threshold = this.configTemplate.getWork_hot_value_threshold();
        if( hot_value_threshold == null ){
            hot_value_threshold = 10.0;
        }
        if( hotValue >= hot_value_threshold ){
            //pull the hot work entry into redis now
            this.redisCacheServices.copyWorkEntryFromDaoToCache(work_id);
        }


        String log = LogUtils.getTime() + "\t";
        log += httpServletRequest.getRequestURI()+ "\t";
        log += "hit_ac\t";
        log += user_id + "\t";
        log += work_id + "\n";

        //do the log
        this.sampleLocalLogHandler.writeLog(log);

        return new String(log);
    }

    /**
     * give comment to the work
     * @param work_id
     * @param user_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ac/comment/{work_id}/{user_id}")
    public String postCommentAction(@PathVariable BigInteger work_id,@PathVariable Integer user_id,
                                    HttpServletRequest httpServletRequest){
        //cache update
        this.actionServices.userPostCommentActionServices(work_id,user_id);

        //dao (mysql) update
        //dao update
        Double factor = this.configTemplate.getComment_factor();
        if( factor == null ){
            factor = 0.2;
        }
        this.workStatusUpdateServices.updateWorkHotValue(work_id,factor);
        //if the work 's hot value is full,just pull to cache
        Double hotValue = workStatusUpdateServices.getWorkHotValue( work_id );
        Double hot_value_threshold = this.configTemplate.getWork_hot_value_threshold();
        if( hot_value_threshold == null ){
            hot_value_threshold = 10.0;
        }
        if( hotValue >= hot_value_threshold ){
            //pull the hot work entry into redis now
            this.redisCacheServices.copyWorkEntryFromDaoToCache(work_id);
        }

        String log = LogUtils.getTime() + "\t";
        log += httpServletRequest.getRequestURI()+ "\t";
        log += "comment_ac\t";
        log += user_id + "\t";
        log += work_id + "\n";

        //do the log
        this.sampleLocalLogHandler.writeLog(log);

        return new String(log);
    }

    /**
     * forward the work
     * @param work_id
     * @param user_id
     * @return
     */
    @RequestMapping(value = "ac/forward/{work_id}/{user_id}")
    @ResponseBody
    public String forwardWorkAction(@PathVariable BigInteger work_id,@PathVariable Integer user_id,
                                    HttpServletRequest httpServletRequest){
        //cache update
        this.actionServices.userForwardWorkActionServices(work_id,user_id);

        //dao update
        //dao update
        Double factor = this.configTemplate.getForward_factor();
        if( factor == null ){
            factor = 0.6;
        }
        this.workStatusUpdateServices.updateWorkHotValue(work_id,factor);
        //if the work 's hot value is full,just pull to cache
        Double hotValue = workStatusUpdateServices.getWorkHotValue( work_id );
        Double hot_value_threshold = this.configTemplate.getWork_hot_value_threshold();
        if( hot_value_threshold == null ){
            hot_value_threshold = 10.0;
        }
        if( hotValue >= hot_value_threshold ){
            //pull the hot work entry into redis now
            this.redisCacheServices.copyWorkEntryFromDaoToCache(work_id);
        }

        String log = LogUtils.getTime() + "\t";
        log += httpServletRequest.getRequestURI()+ "\t";
        log += "forward_ac\t";
        log += user_id + "\t";
        log += work_id + "\n";

        //do the log
        this.sampleLocalLogHandler.writeLog(log);

        return new String(log);
    }

}
