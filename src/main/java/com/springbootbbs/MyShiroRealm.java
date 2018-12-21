package com.springbootbbs;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

	/**
	 * 用于授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("MyShiroRealm的doGetAuthorizationInfo授权方法执行");

		// User user=(User)
		// principals.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
		// System.out.println("在MyShiroRealm中AuthorizationInfo（授权）方法中从session中获取的user对象:"+user);

		// 从PrincipalCollection中获得用户信息
		Object principal = principals.getPrimaryPrincipal();
		System.out.println("ShiroRealm  AuthorizationInfo:" + principal.toString());

		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
			permissions.add("admin:query");
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setStringPermissions(permissions);

		return info;
	}

	/**
	 * 方面用于加密 参数：AuthenticationToken是从表单穿过来封装好的对象
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo:" + token);

		UsernamePasswordToken upToken = (UsernamePasswordToken)token;

		String username = upToken.getUsername();

		UserService userService = new UserService();

		if (!userService.selectUsername(username)) {
			throw new UnknownAccountException("无此用户名！");
		}

		Object principal = username;
		Object credentials = userService.selectPassword(username);
		ByteSource credentialsSqlt = ByteSource.Util.bytes(username);
		String realmName = this.getName();

		SimpleAuthenticationInfo info = null;
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSqlt, realmName);

		return info;
	}
}
