package com.springbootbbs.config;

import com.springbootbbs.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfigBean {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 设置login URL
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/LoginSuccess.action");
		// 未授权的页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/static/**", "anon");
		// 设置登录的URL为匿名访问，因为一开始没有用户验证
		filterChainDefinitionMap.put("/login.action", "anon");
		filterChainDefinitionMap.put("/Exception.class", "anon");
		// 我写的url一般都是xxx.action，根据你的情况自己修改
		filterChainDefinitionMap.put("/*.action", "roles[user]");
		// 退出系统的过滤器
		filterChainDefinitionMap.put("/logout", "logout");
		// 现在资源的角色
		filterChainDefinitionMap.put("/templates/admin.ftl", "roles[admin]");
		filterChainDefinitionMap.put("/templates/user.ftl", "roles[user]");
        filterChainDefinitionMap.put("/profile/basic", "roles[user]");
        filterChainDefinitionMap.put("/admin*", "roles[admin]");
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");

		filterChainDefinitionMap.put("/topic_new", "roles[user]");
		filterChainDefinitionMap.put("/topic_save", "roles[user]");
		filterChainDefinitionMap.put("/topic/*/edit", "roles[user]");
		filterChainDefinitionMap.put("/topic/*/edit_post", "roles[user]");
		filterChainDefinitionMap.put("/topic/*/reply", "roles[user]");
		filterChainDefinitionMap.put("/topic/*/remove", "roles[user]");
		filterChainDefinitionMap.put("/post/*/edit", "roles[user]");
		filterChainDefinitionMap.put("/post/*/edit_post", "roles[user]");
		filterChainDefinitionMap.put("/post/*/remove", "roles[user]");
		filterChainDefinitionMap.put("/topic_upload", "roles[user]");
		// 临时测试
		filterChainDefinitionMap.put("/user/*", "anon");
		// 最后一班都，固定格式
		filterChainDefinitionMap.put("/**", "anon");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/*
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(1024);

		return hashedCredentialsMatcher;
	}

	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入自定义的realm;
		securityManager.setRealm(myShiroRealm());
        // 注入缓存管理器;
		securityManager.setCacheManager(ehCacheManager());
		// 注入rememberMe管理器
		securityManager.setRememberMeManager(rememberMeManager());

		return securityManager;
	}

	/*
	 * 开启shiro aop注解支持 使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	/*
	 * shiro缓存管理器;
	 * 需要注入对应的其它的实体类中-->安全管理器：securityManager可见securityManager是整个shiro的核心；
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		System.out.println("ShiroConfiguration.getEhCacheManager()");
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}

	@Bean
	public SimpleCookie rememberMeCookie(){
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//cookie生效时间30天,单位秒;
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
		// cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCipherKey("springbootbbskey".getBytes());
		return cookieRememberMeManager;
	}

}
