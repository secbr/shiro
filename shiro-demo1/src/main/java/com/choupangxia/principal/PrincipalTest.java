package com.choupangxia.principal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 测试自定义Principal
 *
 * @author sec
 * @version 1.0
 * @date 2021/1/21
 **/
public class PrincipalTest {


    @Test
    public void testAuthentication() {

        PrincipalRealm principalRealm = new PrincipalRealm();

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(principalRealm);

        // 2.主体提交认证请求
        // 设置SecurityManager环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 获取当前主体
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token = new UsernamePasswordToken("Tom", "123456");
        // 登录
        subject.login(token);

        // 获取自定会的Principal
        Principal principal = (Principal) subject.getPrincipal();
        System.out.println(principal.getMobile());

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
    }
}
