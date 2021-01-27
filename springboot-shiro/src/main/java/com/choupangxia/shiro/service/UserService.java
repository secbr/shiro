package com.choupangxia.shiro.service;

import com.choupangxia.shiro.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author sec
 * @version 1.0
 * @date 2021/1/27
 **/
@Service
public class UserService {

    public User getUserByUserName(String username) {
        // 模拟返回，生产中不建议直接返回明文密码
        User user = new User();
        user.setUsername("secbro");
        user.setPassword("123456");
        return user;
    }
}
