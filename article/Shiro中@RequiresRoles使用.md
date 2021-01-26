# Shiro中@RequiresRoles使用

Shiro中通过@RequiresRoles注解可检验权限，在检验权限之前先要设置权限：

### 授权方法中给用户添加角色

在自定义的Realm中（继承实现AuthorizingRealm）的doGetAuthorizationInfo方法中授权方法中给用户添加角色。
```
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    String userName = (String) principalCollection.getPrimaryPrincipal();
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    // 从数据库获取角色
    Set<String> roles = getRolesByUserName(userName);
    simpleAuthorizationInfo.setRoles(roles);
    return simpleAuthorizationInfo;
}
```

### 使用@RequiresRoles检验权限

在Controller中对应的方法上使用注解校验对应的授权：
```
@PostMapping("/list")
@RequiresRoles("a")
public void list(){//...}
```

### 多个角色或权限

如果有多个权限/角色验证的时候中间用“,”隔开，默认是所有列出的权限/角色必须同时满足才生效。

默认为logical=Logical.AND，表示所有列出的都必须满足才能进入方法。

在注解中可通过logical=Logical.OR，表示所列出的条件只要满足其中一个就可以。

```
@RequiresRoles(value={"admin","user"},logical = Logical.OR)
@RequiresPermissions(value={"add","update"},logical = Logical.AND)
```

## 关注&技术交流

Shiro相关文章正在个人公众号"**程序新视界**"持续更新中，为确保获得最新文章，可关注公众号。后期会将Shiro相关文章整理成电子版，在公众号内免费分享给大家。

![程序新视界](https://www.choupangxia.com/wp-content/uploads/2019/07/weixin.jpg)

如有技术交流，也可添加个人微信进行交流：541075754。

