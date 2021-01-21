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

		// get the currently executing user:
		// 3.获取当前主体（用户）
		Subject currentUser = SecurityUtils.getSubject();

		// Do some stuff with a Session (no need for a web or EJB container!!!)
		// 4.获取当前主体的会话
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if ("aValue".equals(value)) {
			log.info("Retrieved the correct value! [" + value + "]");
		}

		// let's login the current user so we can check against roles and permissions:
		// 判断当前用户是否认证
		if (!currentUser.isAuthenticated()) {
			// 将账号和密码封装为UsernamePasswordToken中
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			// 记住我
			token.setRememberMe(true);
			try {
				// 进行登录操作
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				log.info("There is no user with username of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				log.info("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {
				log.info("The account for username " + token.getPrincipal() + " is locked.  " +
						"Please contact your administrator to unlock it.");
			}
			// ... catch more exceptions here (maybe custom ones specific to your application?
			catch (AuthenticationException ae) {
				//unexpected condition?  error?
			}
		}

		//say who they are:
		//print their identifying principal (in this case, a username):
		log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

		//test a role:
		// 检查是否有指定角色权限
		if (currentUser.hasRole("schwartz")) {
			log.info("May the Schwartz be with you!");
		} else {
			log.info("Hello, mere mortal.");
		}

		//test a typed permission (not instance-level)
		// 判断是否有资源操作权限
		if (currentUser.isPermitted("lightsaber:wield")) {
			log.info("You may use a lightsaber ring.  Use it wisely.");
		} else {
			log.info("Sorry, lightsaber rings are for schwartz masters only.");
		}

		//a (very powerful) Instance Level permission:
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
