package com.gistone.service;

import java.util.Map;

/**
 * @ClassName IGettingUsersService
 * @Description 使用redis 解析当前token，获取得到当前用户，
 * @Author xxh
 * @Date 2019/8/13 11:30
 * @Version 1.0
 **/
public interface IGettingUsersService {

    Map<Object,Object> getUsersByTokenRedis(String token);

}
