package com.hujian.star.config;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/10.
 */
public class ConfigTemplate implements Serializable {
    /**
     * like rate
     */
    private Double like_factor = null;

    /**
     * dislike factor
     */
    private Double dislike_factor = null;

    /**
     * hit and look
     */
    private Double hit_factor = null;

    /**
     * comment factor
     */
    private Double comment_factor = null;

    /**
     * forward factor
     */
    private Double forward_factor = null;

    /**
     * set the log file
     */
    private String logBaseFile = null;

    /**
     * the data file
     */
    private String originRecommendDataBaseFile = null;

    /**
     * how many items each request's response
     */
    private Integer each_push_recommend_items = null;

    /**
     * how many log can be a chunk
     */
    private Integer log_chunk_set = null;

    /**
     * pull into cache
     */
    private Double work_hot_value_threshold = null;

    /**
     * the work entry cache key
     */
    private String redis_work_entry_key = null;

    /**
     * the negative credit user space
     */
    private String redis_negative_credit_user_entry_key = null;

    /**
     * report -> credit - this
     */
    private Double user_credit_score_report_factor = null;

    /**
     * which type means the work has been baned
     */
    private Integer work_ban_type_set = null;

    /**
     * like -> level score
     */
    private Double level_growth_score_like_ac = null;

    /**
     * hit -> level score
     */
    private Double level_growth_score_hit_ac = null;

    /**
     * forward -> level score
     */
    private Double level_growth_score_forward = null;


    public Double getLike_factor() {
        return like_factor;
    }

    public void setLike_factor(Double like_factor) {
        this.like_factor = like_factor;
    }

    public Double getDislike_factor() {
        return dislike_factor;
    }

    public void setDislike_factor(Double dislike_factor) {
        this.dislike_factor = dislike_factor;
    }

    public Double getComment_factor() {
        return comment_factor;
    }

    public void setComment_factor(Double comment_factor) {
        this.comment_factor = comment_factor;
    }

    public Double getForward_factor() {
        return forward_factor;
    }

    public void setForward_factor(Double forward_factor) {
        this.forward_factor = forward_factor;
    }

    public Double getHit_factor() {
        return hit_factor;
    }

    public void setHit_factor(Double hit_factor) {
        this.hit_factor = hit_factor;
    }

    public String getLogBaseFile() {
        return logBaseFile;
    }

    public void setLogBaseFile(String logBaseFile) {
        this.logBaseFile = logBaseFile;
    }

    public Integer getEach_push_recommend_items() {
        return each_push_recommend_items;
    }

    public void setEach_push_recommend_items(Integer each_push_recommend_items) {
        this.each_push_recommend_items = each_push_recommend_items;
    }

    public Integer getLog_chunk_set() {
        return log_chunk_set;
    }

    public void setLog_chunk_set(Integer log_chunk_set) {
        this.log_chunk_set = log_chunk_set;
    }

    public String getOriginRecommendDataBaseFile() {
        return originRecommendDataBaseFile;
    }

    public void setOriginRecommendDataBaseFile(String originRecommendDataBaseFile) {
        this.originRecommendDataBaseFile = originRecommendDataBaseFile;
    }

    public Double getWork_hot_value_threshold() {
        return work_hot_value_threshold;
    }

    public void setWork_hot_value_threshold(Double work_hot_value_threshold) {
        this.work_hot_value_threshold = work_hot_value_threshold;
    }

    public String getRedis_work_entry_key() {
        return redis_work_entry_key;
    }

    public void setRedis_work_entry_key(String redis_work_entry_key) {
        this.redis_work_entry_key = redis_work_entry_key;
    }

    public Double getLevel_growth_score_like_ac() {
        return level_growth_score_like_ac;
    }

    public void setLevel_growth_score_like_ac(Double level_growth_score_like_ac) {
        this.level_growth_score_like_ac = level_growth_score_like_ac;
    }

    public Double getLevel_growth_score_hit_ac() {
        return level_growth_score_hit_ac;
    }

    public void setLevel_growth_score_hit_ac(Double level_growth_score_hit_ac) {
        this.level_growth_score_hit_ac = level_growth_score_hit_ac;
    }

    public Double getLevel_growth_score_forward() {
        return level_growth_score_forward;
    }

    public void setLevel_growth_score_forward(Double level_growth_score_forward) {
        this.level_growth_score_forward = level_growth_score_forward;
    }

    public Double getUser_credit_score_report_factor() {
        return user_credit_score_report_factor;
    }

    public void setUser_credit_score_report_factor(Double user_credit_score_report_factor) {
        this.user_credit_score_report_factor = user_credit_score_report_factor;
    }

    public String getRedis_negative_credit_user_entry_key() {
        return redis_negative_credit_user_entry_key;
    }

    public void setRedis_negative_credit_user_entry_key(String redis_negative_credit_user_entry_key) {
        this.redis_negative_credit_user_entry_key = redis_negative_credit_user_entry_key;
    }

    public Integer getWork_ban_type_set() {
        return work_ban_type_set;
    }

    public void setWork_ban_type_set(Integer work_ban_type_set) {
        this.work_ban_type_set = work_ban_type_set;
    }
}
