package com.hujian.star.services.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.star.model.ItemUserScoreEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by hujian on 2017/5/9.
 */
@Component
public class ItemUserScoreChangeServicesUtil implements Serializable {

    /**
     * this is the redis template
     */
    @Autowired
    private   RedisTemplate redisTemplate = null;

    /**
     * the serializer
     */
    private    RedisSerializer redisSerializer = null;

    /**
     * the json mapper handler
     */
    @Autowired
    private    ObjectMapper jsonMapper = null;

    /**
     * change the score
     * @param work_id work id(item)
     * @param user_id user id
     * @param factor change rate
     */
    public   void doChangeScoreActionAtRedis(BigInteger work_id,Integer user_id,Double factor){
        //now,change the work's score at user.
        redisSerializer = redisTemplate.getStringSerializer();
        byte[] key = redisSerializer.serialize("RecommendCredit");
        byte[] field = redisSerializer.serialize(user_id.toString());
        Double finalFactor = factor;
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] v = redisConnection.hGet(key,field);
                String vs = (String) redisSerializer.deserialize(v);
                ItemUserScoreEntry itemUserScoreEntry = null;
                if( vs == null ){
                    itemUserScoreEntry = new ItemUserScoreEntry();
                    //insert
                    itemUserScoreEntry.setUser_id(user_id);
                    itemUserScoreEntry.setWork_id(work_id);
                    itemUserScoreEntry.setScore(finalFactor);
                }else{
                    try {
                        itemUserScoreEntry = jsonMapper.readValue(vs,ItemUserScoreEntry.class);
                        //change the value
                        itemUserScoreEntry.setScore(itemUserScoreEntry.getScore() + finalFactor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    byte[] value = redisSerializer.serialize(jsonMapper.writeValueAsString(itemUserScoreEntry));
                    redisConnection.hSet(key,field,value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

}
