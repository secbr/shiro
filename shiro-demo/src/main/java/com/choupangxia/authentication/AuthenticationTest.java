package com.choupangxia.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sec
 * @version 1.0
 * @date 2021/1/21
 **/
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        // 在方法开始前添加一个用户
        simpleAccountRealm.addAccount("wmyskxz", "123456");
    }

    @Test
    public void testAuthentication() {

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        // 设置SecurityManager环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 获取当前主体
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wmyskxz", "123456");
        // 登录
        subject.login(token);

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        // 输出true
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        // 登出
        subject.logout();

        // 输出false
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
    }
}
