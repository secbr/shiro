package com.choupangxia.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sec
 * @version 1.0
 * @date 2021/1/27
 **/
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello Shiro!");
        return "Hello Shiro!";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "please login!";
    }

    @RequestMapping("/doLogin")
    public void doLogin(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("用户：" + username + ",登录成功！");
        } catch (Exception e) {
            System.out.println("登录异常" + e.getMessage());
        }
    }

}
