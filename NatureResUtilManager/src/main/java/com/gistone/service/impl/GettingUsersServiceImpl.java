package com.gistone.service.impl;

import com.auth0.jwt.JWT;
import com.gistone.service.IGettingUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName GettingUsersServiceImpl
 * @Description TODO
 * @Author xxh
 * @Date 2019/8/13 11:40
 * @Version 1.0
 **/
@Service
public class GettingUsersServiceImpl implements IGettingUsersService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Map<Object, Object> getUsersByTokenRedis(String token) {
        if(stringRedisTemplate.hasKey(token)) {//判断redis是否存在于数据库
            if(JWT.decode(token).getAudience()!=null) {
//                String UserId = JWT.decode(token).getAudience().get(0);
               return stringRedisTemplate.opsForHash().entries(token);
            }
        }
        throw new RuntimeException("token不存在，请重新登录");
    }

}
