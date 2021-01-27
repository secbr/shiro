package com.choupangxia.principal;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 用于测试Principal的Realm
 *
 * @author sec
 * @version 1.0
 * @date 2021/1/27
 **/
public class PrincipalRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = "tom";
        String mobile = "156*********";
        String password = "123456";
        String salt = "abc";
        System.out.println("进行认证...");
        return new SimpleAuthenticationInfo(new Principal(name, mobile),
                password, ByteSource.Util.bytes(salt), getName());
    }
}
