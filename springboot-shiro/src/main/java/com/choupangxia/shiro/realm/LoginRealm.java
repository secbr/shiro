package com.choupangxia.shiro.realm;

import com.choupangxia.shiro.entity.User;
import com.choupangxia.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义Realm，对登录进行验证
 *
 * @author sec
 * @version 1.0
 * @date 2021/1/27
 **/
@Component("loginRealm")
public class LoginRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 这里不做授权校验
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 用户登录时传入的用户名称
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 根据用户名查询用户信息
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UnknownAccountException("账户不存在!");
        }

        if (!password.equals(user.getPassword())) {
            throw new UnknownAccountException("密码错误!");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }
}
