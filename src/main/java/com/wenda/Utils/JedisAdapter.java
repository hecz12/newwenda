package com.wenda.Utils;

import com.sun.deploy.trace.LoggerTraceListener;
import jdk.nashorn.internal.ir.BlockLexicalContext;
import jdk.nashorn.internal.ir.Terminal;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Set;

/**
 * redis的工具类，使用redis实现一些访问频率高的信息
 * Created by 49540 on 2017/6/29.
 */
@Component
public class JedisAdapter implements InitializingBean {
    private JedisPool jedisPool;



    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379/10");
    }

    public Set<String> zrevrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key,start,end);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return  null;
    }

    public List<String> lrange(String key,int start,int end)
    {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.lrange(key,start,end);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return  null;
    }

    /**
     * 获取有序集合的长度
     * @param key
     * @return
     */
    public long zcard(String key)
    {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return  0;
    }

    /**
     * 从对应key的有序集合中找到成员member的score
     * @param key   集合的标识值
     * @param member  排序集合中的成员标识
     * @return
     */
    public Double zscore(String key,String member)
    {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zscore(key,member);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return  null;
    }

    /**
     * 开启事务
     * @param jedis jedis连接
     * @return
     */
    public Transaction multi(Jedis jedis)
    {
        try {
            return jedis.multi();
        }
        catch (Exception e)
        {
            System.out.println("出现开启事务异常"+e.getMessage());
        }
        finally {
        }
        return null;
    }

    /**
     * 对事务进行原子操作的执行
     * @param tx  事务
     * @param jedis jedis连接
     * @return
     */
    public List<Object> exec(Transaction tx,Jedis jedis)
    {
        try {
            return tx.exec();
        }
        catch (Exception e)
        {
            System.out.println("出现执行异常"+e.getMessage());
            tx.discard();
        }
        finally {
            if(tx!=null)
            {
                try {
                    tx.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 向为key的set集合中添加value值
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key,String value)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key,value);
        }
        catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 从为key的集合中删除为value的值
     * @param key
     * @param value
     * @return
     */
    public long srem(String key,String value)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }
        catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 根据key值获取set集合的数量
     * @param key
     * @return
     */
    public long scard(String key)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }
        catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    /***
     * 判断value是否为对应key的set中的成员
     * @param key
     * @param value
     * @return
     */
    public boolean isremember(String key,String value)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        }
        catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return false;
    }

    /***
     * 向有序集合添加信息
     * @param key
     * @param score
     * @param value
     * @return
     */
    public long zadd(String key,double score,String value)
    {
        Jedis jedis = null;
        try{
            return jedis.zadd(key, score, value);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 向为key的有序队列中删除为value的值
     * @param key
     * @param value
     * @return
     */
    public long zrem(String key,String value)
    {
        Jedis jedis = null;
        try{
            return jedis.zrem(key,value);
        }catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int timeout,String key)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout,key);
        }
        catch (Exception e)
        {
            System.out.println("出现异常"+e.getMessage());
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
        return null;
    }

    public Jedis getJedis()
    {
        return jedisPool.getResource();
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            System.out.println("出现异常"+e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
