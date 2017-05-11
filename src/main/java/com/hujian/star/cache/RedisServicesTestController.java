package com.hujian.star.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/9.
 */
@Controller
public class RedisServicesTestController implements Serializable {

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
     * put test
     * @return
     */
    @RequestMapping(value = "kv/puts",method = RequestMethod.GET)
    @ResponseBody
    public String putTest(){
        redisSerializer = redisTemplate.getStringSerializer();

        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                /**
                 * string test
                 */
                byte[] key = redisSerializer.serialize( "redis" );
                byte[] val = redisSerializer.serialize("127.0.0.1:6379");

                redisConnection.setNX(key,val);

                /**
                 * hash test
                 */
                key = redisSerializer.serialize("Redis");
                byte[] field = redisSerializer.serialize("remote");
                val = redisSerializer.serialize("12 ms");

                redisConnection.hSet(key,field,val);

                field = redisSerializer.serialize( "local" );
                val = redisSerializer.serialize( "10 ms" );

                redisConnection.hSet(key,field,val);

                /**
                 * list test
                 */
                key = redisSerializer.serialize("nameList1");
                val = redisSerializer.serialize( "hujian" );

                redisConnection.lPush(key,val);
                redisConnection.lPush(key,val);

                /**
                 * set test
                 */
                key = redisSerializer.serialize("nameList2");
                byte[] member = redisSerializer.serialize("hujian");
                redisConnection.sAdd(key,member);
                member = redisSerializer.serialize("hujian");
                redisConnection.sAdd(key,member);

                /**
                 * sorted set test
                 */
                key = redisSerializer.serialize("shot");
                val = redisSerializer.serialize("hujian");
                double score = 1.0;
                redisConnection.zAdd(key,score,val);

                key = redisSerializer.serialize("shot");
                val = redisSerializer.serialize( "libai" );
                score = 10.0;
                redisConnection.zAdd(key,score,val);

                key = redisSerializer.serialize("shot");
                val = redisSerializer.serialize("bob");
                score = 3.0;
                redisConnection.zAdd(key,score,val);

                return null;
            }
        });
        return new String("puts ok");
    }

    /**
     * delete test
     * @return
     */
    @RequestMapping(value = "kv/pops",method = RequestMethod.GET)
    @ResponseBody
    public String removesTest(){

        redisSerializer = redisTemplate.getStringSerializer();

        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                /**
                 * string test
                 */
                byte[] key = redisSerializer.serialize("redis");
                redisConnection.del(key);

                /**
                 * list test
                 */
                key = redisSerializer.serialize("nameList1");
                redisConnection.lPop(key);

                /**
                 * hash test
                 */
                key = redisSerializer.serialize("Redis");
                byte[] field = redisSerializer.serialize("local");
                redisConnection.hDel(key,field);
                field = redisSerializer.serialize("remote");
                redisConnection.hDel(key,field);

                /**
                 * set test
                 */
                key = redisSerializer.serialize("nameList2");
                byte[] member = redisSerializer.serialize("hujian");
                redisConnection.sRem(key,member);

                /**
                 * sorted set test
                 */
                key = redisSerializer.serialize("shot");
                redisConnection.zRem(key,member);
                member = redisSerializer.serialize("libai");
                redisConnection.zRem(key,member);
                member = redisSerializer.serialize("bob");
                redisConnection.zRem(key,member);

                return null;
            }
        });

        return new String("remove done");
    }


    /**
     * put
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "kv/put/{key}/{value}",method = RequestMethod.GET)
    @ResponseBody
    public String put(@PathVariable String key,@PathVariable String value){
        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisSerializer = redisTemplate.getStringSerializer();
                byte[] k = redisSerializer.serialize( key );
                byte[] v = redisSerializer.serialize( value );

                redisConnection.set( k,v );

                return null;
            }
        });
        return new String("ok");
    }

    /**
     * get the kv
     * @param key
     * @return
     */
    @RequestMapping(value = "kv/get/{key}",method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String key){
        final String[] val = {null};
        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisSerializer = redisTemplate.getStringSerializer();

                byte[] k = redisSerializer.serialize( key );
                byte[] v = redisConnection.get( k );

                val[0] = (String) redisSerializer.deserialize( v );
                return null;
            }
        });
        return new String("v:" + val[0]);
    }

}
