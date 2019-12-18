package com.zhihao.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;


@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 定义filterChain，静态资源不拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了 
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean  //这里有个坑使用的依赖是shiro-spring-boot-xx 返回值得是DefaultWebSecurityManager
    public DefaultWebSecurityManager securityManager() {
        // 配置SecurityManager，并注入shiroRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        //设置管理器记住我
        securityManager.setRememberMeManager(rememberMeManager());
        //设置缓存管理器
        securityManager.setCacheManager(cacheManager());
        //设置会话管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        // 配置Realm，需自己实现
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    /**
     * cookie对象
     *
     * @return org.apache.shiro.web.servlet.SimpleCookie
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }


    /**
     * cookie管理对象
     *
     * @return org.apache.shiro.web.mgt.CookieRememberMeManager
     * @author: zhihao
     * @date: 2019/12/13
     * {@link #}
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥  建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启shiro认证注解
     *
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author: zhihao
     * @date: 2019/12/13
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        //设置一小时超时  单位是秒
        redisManager.setExpire(60*60*60);
        return redisManager;
    }


    /**
     * 返回Redis缓存管理器
     *
     * @return org.crazycake.shiro.RedisCacheManager
     * @author: zhihao
     * @date: 2019/12/15
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 注册这个bean目的就是为了在thymeleaf中使用shiro的自定义tag。
     *
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     * @author: zhihao
     * @date: 2019/12/16
     */
    @Primary
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 注册RedisSessionDAO-bean,作用在与能使用sessionDAO
     *
     * @author: zhihao
     * @date: 2019/12/17
     */
    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //添加之前权限管理的redisManager
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     *  注册SessionManager会话管理器
     *
     * @return org.apache.shiro.session.mgt.SessionManager
     * @author: zhihao
     * @date: 2019/12/17
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        List<SessionListener> listeners = new ArrayList<>();
        //需要添加自己实现的会话监听器
        listeners.add(new ShiroSessionListener());
        //添加会话监听器给sessionManager管理
        sessionManager.setSessionListeners(listeners);
        //添加SessionDAO给sessionManager管理
        sessionManager.setSessionDAO(sessionDAO());
        //设置全局(项目)session超时单位 毫秒   -1为永不超时
//        sessionManager.setGlobalSessionTimeout(-1);
        return sessionManager;
    }
}
