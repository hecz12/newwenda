package com.wenda.Utils;

import com.sun.deploy.trace.LoggerTraceListener;
import jdk.nashorn.internal.ir.BlockLexicalContext;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * redis的set工具类，保证用户不能多次出现在同一个set中
 * Created by 49540 on 2017/6/29.
 */
@Component
public class JedisAdapter implements InitializingBean {
    private JedisPool jedisPool;



    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379/10");
    }

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
