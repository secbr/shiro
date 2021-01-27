# Shiro之自定义Principal和Realm

在Shiro中Realm相当于一个安全数据源，在Realm中提供的方法获取用户数据，进行匹配验证。

比如，当身份验证时会调用doGetAuthenticationInfo方法，而具体的匹配操作可在该方法内查询权限，并进行匹配。

如果使用shiro默认的密码匹配的话，通常会返回一个SimpleAuthenticationInfo的对象，SimpleAuthenticationInfo的其中一个构造方法如下：
```
public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) {
    this.principals = new SimplePrincipalCollection(principal, realmName);
    this.credentials = credentials;
}


public SimpleAuthenticationInfo(PrincipalCollection principals, Object credentials) {
    this.principals = new SimplePrincipalCollection(principals);
    this.credentials = credentials;
}
```
通过构造方法可以看出，创建SimpleAuthenticationInfo对象时，构造参数中，有一个类型为Object的对象principal，或者PrincipalCollection类型的principals。

通过构造方法可以看出，principal为Object类型，也就是说可以是任何对象。那么也就可以自定义一个principal。

下面就尝试自定义一个principal对象，并看看如何使用。首先，自定义Principal：

```
public class Principal implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    public Principal(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }
    // 省略getter/setter
}
```
然后自定义一个Realm，通过实现AuthorizingRealm：

```
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
```
在doGetAuthenticationInfo方法中创建Principal对象，并返回。

下面看使用的实例：

```
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
```
执行单元测试，会发现通过Subject可以获得到在Realm中存储的信息，并且对传入的参数进行了校验。当换一个新的用户名密码之后，在此执行，会抛出异常。

上述实例完整代码：见PrincipalTest类。

## 关注&技术交流

Shiro相关文章正在个人公众号"**程序新视界**"持续更新中，为确保获得最新文章，可关注公众号。后期会将Shiro相关文章整理成电子版，在公众号内免费分享给大家。

![程序新视界](https://www.choupangxia.com/wp-content/uploads/2019/07/weixin.jpg)

如有技术交流，也可添加个人微信进行交流：541075754。
