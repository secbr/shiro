package com.choupangxia.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sec
 * @version 1.0
 * @date 2021/1/21
 **/
public class Tutorial {

    private static final transient Logger log = LoggerFactory.getLogger(Tutorial.class);

    public static void main(String[] args) {
        log.info("My First Apache Shiro Application");

        // 1.初始化环境，主要是加载shiro.ini配置文件的信息
        Environment environment = new BasicIniEnvironment("classpath:shiro.ini");
        // 2.获取SecurityManager安全管理器
        SecurityManager securityManager = environment.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);

        // 3.获取当前主体（用户）
        Subject currentUser = SecurityUtils.getSubject();

        // 4.获取当前主体的会话
        Session session = currentUser.getSession();
        // 5.向会话中存储一些内容（不需要web容器或EJB容器）
        session.setAttribute("someKey", "aValue");
        // 6.再次从会话中获取存储的内容，并比较与存储值是否一致。
        String value = (String) session.getAttribute("someKey");
        if ("aValue".equals(value)) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // 当前用户进行登录操作，进而可以检验用户的角色和权限。
        // 7.判断当前用户是否认证（此时很显然未认证）
        if (!currentUser.isAuthenticated()) {
            // 8.将账号和密码封装为UsernamePasswordToken中
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // 9.记住我
            token.setRememberMe(true);
            try {
                // 10.进行登录操作
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... 更多其他异常，包括应用程序异常
            catch (AuthenticationException ae) {
                // 其他意外异常、error处理
            }
        }

        // 打印当前用户的主体信息
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // 检查是否有指定角色权限（前面已经通过Environment加载了权限和角色信息）
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        // 判断是否有资源操作权限
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        // 更强级别的权限验证
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        // 登出
        currentUser.logout();
        System.exit(0);
    }
}
