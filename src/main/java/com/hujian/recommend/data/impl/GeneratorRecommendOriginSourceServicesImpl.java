package com.hujian.recommend.data.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.recommend.data.GeneratorRecommendOriginSourceServices;
import com.hujian.recommend.data.NoDataException;
import com.hujian.star.config.ConfigTemplate;
import com.hujian.star.log.LogTemplate;
import com.hujian.star.log.utils.LogUtils;
import com.hujian.star.model.ItemUserScoreEntry;
import okio.BufferedSink;
import okio.Okio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hujian on 2017/5/10.
 */
@Service
public class GeneratorRecommendOriginSourceServicesImpl implements
        GeneratorRecommendOriginSourceServices {

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
     * generator the source data
     */
    @Override
    public void generate() {
        redisSerializer = redisTemplate.getStringSerializer();
        byte[] key = redisSerializer.serialize("RecommendCredit");
        //get the key-list
        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //the key list
                Set<byte[]> keysList = new HashSet<byte[]>();
                keysList = redisConnection.hKeys( key );
                if( keysList == null ){
                    try {
                        throw new NoDataException("No Origin Data Now");
                    } catch (NoDataException e) {
                        e.printStackTrace();
                    }
                }else{
                    //flush to file
                    String dataChunk = "";
                    for( byte[] fields: keysList ){
                        byte[] value = redisConnection.hGet(key,fields);
                        String tuple = (String) redisSerializer.deserialize( value );
                        try {
                            ItemUserScoreEntry entry = jsonMapper.readValue(tuple,ItemUserScoreEntry.class);
                            dataChunk += entry.toString() + "\n";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String dataFile = configTemplate.getOriginRecommendDataBaseFile();
                    try {
                        BufferedSink sink = Okio.buffer( Okio.sink( new File(dataFile)) );
                        try {
                            sink.writeUtf8(dataChunk);
                            sink.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String log = LogUtils.getTime() + "\t [DEBUG]{" +
                            GeneratorRecommendOriginSourceServicesImpl.class.getName() + "}\t " +
                            "flush the recommend origin data into file:" + dataFile+"\n";
                    sampleLocalLogHandler.writeLog( log );
                }
                return null;
            }
        });
    }

}
