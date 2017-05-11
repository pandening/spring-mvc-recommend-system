package com.hujian.recommend.services;

import net.librec.recommender.item.RecommendedItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujian on 2017/5/10.
 */
public interface RecommendServices extends Serializable {

    /**
     * get some recommend items of user id
     * @param user_id
     * @param limit  how many items you want to get
     * @return
     */
    List<RecommendedItem> UserKNNRecommendAct(Integer user_id,Integer limit);

    /**
     * get recommend
     * @param user_id
     * @return
     */
    List<RecommendedItem> UserKNNRecommendAct(Integer user_id);

}
