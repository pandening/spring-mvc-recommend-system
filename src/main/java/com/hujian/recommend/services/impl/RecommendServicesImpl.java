package com.hujian.recommend.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.recommend.services.RecommendServices;
import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujian on 2017/5/10.
 */
@Service
public class RecommendServicesImpl implements RecommendServices {

    /**
     * this is the log
     */
    @Autowired
    private LogTemplate sampleLocalLogHandler = null;

    /**
     * the redis template
     */
    @Autowired
    private RedisTemplate redisTemplate = null;

    /**
     * the serializer
     */
    private RedisSerializer redisSerializer = null;

    /**
     * this is the json tools
     */
    @Autowired
    private ObjectMapper jsonMapper = null;

    /**
     * this is the config
     */
    @Autowired
    private ConfigTemplate configTemplate = null;


    /**
     * using knn recommender
     * @param user_id
     * @param limit  how many items you want to get
     * @return
     */
    @Override
    public List<RecommendedItem> UserKNNRecommendAct(Integer user_id, Integer limit) {
        //redis is useful
        this.redisSerializer = this.redisTemplate.getStringSerializer();
        //get the result
        List<RecommendedItem> recommendedItemList = new ArrayList<>();
        byte[] key = this.redisSerializer.serialize("recommend_cache");
        byte[] field = this.redisSerializer.serialize(user_id.toString());

        final String[] recommendJsonStr = {null};
        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] v = redisConnection.hGet(key, field);
                if (v != null) {

                    sampleLocalLogHandler.writeLog("DEBUG:[" + RecommendServicesImpl.class.getName() + "]" +
                            "  redis cache not empty for user:" + user_id + "\n");

                    recommendJsonStr[0] = (String) redisSerializer.deserialize(v);
                }
                return null;
            }
        });

        if (recommendJsonStr[0] != null ) {

            String log = LogUtils.getTime() + "\t";
            log += user_id + "\t" + "user-knn-recommend\t" + "1\t";
            try {
                recommendedItemList = jsonMapper.readValue(recommendJsonStr[0],List.class);

                if (recommendedItemList.size() <= limit) {

                    sampleLocalLogHandler.writeLog("DEBUG:[" + RecommendServicesImpl.class.getName() + "]" +
                            "  redis empty now for user:" + user_id + "\n");

                    log += "0\n";
                    this.sampleLocalLogHandler.writeLog(log);

                    this.redisTemplate.execute(new RedisCallback() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            redisConnection.hDel(key,field);
                            return null;
                        }
                    });

                    return recommendedItemList;

                } else {

                    sampleLocalLogHandler.writeLog("DEBUG:[" + RecommendServicesImpl.class.getName() + "]" +
                            "  redis extra data push ok for user:" + user_id + "\n");

                    //push the extra data into redis
                    byte[] extra = redisSerializer.serialize(
                            jsonMapper.writeValueAsString(recommendedItemList.subList
                                    (limit + 1, recommendedItemList.size())));
                    this.redisTemplate.execute(new RedisCallback() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            redisConnection.hSet(key, field, extra);
                            return null;
                        }
                    });

                    log += "1\n";
                    this.sampleLocalLogHandler.writeLog(log);
                    //return the list
                    return recommendedItemList.subList(0, limit);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //get the algorithm's config resources
            Configuration configuration = new Configuration();
            Configuration.Resource resource =
                    new Configuration.Resource("rec/cf/userknn-test.properties");
            configuration.addResource(resource);

            // build data model
            DataModel dataModel = new TextDataModel(configuration);
            try {
                dataModel.buildDataModel();
                // set recommendation context
                RecommenderContext context =
                        new RecommenderContext(configuration, dataModel);
                RecommenderSimilarity recommenderSimilarity = new PCCSimilarity();
                recommenderSimilarity.buildSimilarityMatrix(dataModel);
                context.setSimilarity(recommenderSimilarity);

                Recommender recommender = new UserKNNRecommender();

                //train the model
                recommender.recommend(context);

                //get the result
                recommendedItemList = recommender.getRecommendedList();
                GenericRecommendedFilter recommendedFilter = new GenericRecommendedFilter();
                List<String> fileUserList = new ArrayList<>();
                fileUserList.add( user_id.toString() );
                recommendedFilter.setUserIdList( fileUserList );
                recommendedItemList = recommendedFilter.filter( recommendedItemList );

                String log;
                if( recommendedItemList == null || recommendedItemList.size() == 0 ){
                    log = LogUtils.getTime() + "\tget empty(or null) recommend list by user-knn\n";
                    sampleLocalLogHandler.writeLog(log,"error_log");
                    return null;
                }

                //create the log
                log = LogUtils.getTime() + "\t";
                log += "user_knn_recommend\t" + recommendedItemList.size() + "\t" + user_id + "\t" +
                        recommendedItemList.size() + "\n";
                //write log
                this.sampleLocalLogHandler.writeLog(log);

                if (recommendedItemList.size() <= limit) {
                    return recommendedItemList;
                } else {
                    //redis is useful
                    byte[] value = new byte[0];
                    try {
                        value = this.redisSerializer.serialize
                                (jsonMapper.writeValueAsString(
                                        recommendedItemList.subList(limit + 1, recommendedItemList.size())));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    byte[] finalValue = value;
                    this.redisTemplate.execute(new RedisCallback() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            redisConnection.hSet(key, field, finalValue);
                            return null;
                        }
                    });
                    return recommendedItemList.subList(0, limit);
                }
            } catch (LibrecException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * set the default return items' count
     * @param user_id
     * @return
     */
    @Override
    public List<RecommendedItem> UserKNNRecommendAct(Integer user_id) {

        Integer limit = this.configTemplate.getEach_push_recommend_items();
        if( limit == null ){
            limit = 10;
        }
        /**
         * call the base services
         */
        return this.UserKNNRecommendAct(user_id,limit);
    }

}
