# shiro触发doGetAuthorizationInfo方法的几种方式

什么情况下会触发shiro的授权检测呢？通常有以下三种方式：

### 方式一：代码中通过Subject对象主动调用权限校验

```
subject.hasRole(“admin”);
//或
subject.isPermitted(“admin”);
```

这种方式属于在代码中需要校验权限的时候主动调用，判断返回结果来确定是否通过。

### 方式二：通过注解的形式检查对用的方法请求

```
@RequiresRoles("admin")
```
这种方式通常用在Controller的方法上。

### 方式三：页面shiro标签

针对ftl等页面，可直接在页面中使用标签来来标注对应的请求。进入该页面时扫描到对应的标签进行权限校验。

```
<shiro:hasPermission name="item:update">
```
如果是jsp页面，在使用Shiro标签库前，首先需要在JSP引入shiro标签：
```
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
```
当加上shiro标签后，会与后台代码结合使用：需要继承AuthorizingRealm，
通过protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) 方法进行业务的处理。
                       


