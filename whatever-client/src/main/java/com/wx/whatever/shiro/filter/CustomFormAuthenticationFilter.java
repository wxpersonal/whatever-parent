package com.wx.whatever.shiro.filter;

import com.wx.whatever.shiro.authc.CustomUsernamepasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.cache.CacheManager;
import sun.security.util.Cache;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 重写该方法,为了将loginType参数保存到token中
     *
     * @param request  请求
     * @param response 响应
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        Integer loginType = Integer.parseInt(getLoginType(request));
        return createToken(username, password, request, response, loginType);
    }

    private AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response, Integer loginType) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return createToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, boolean rememberMe, String host, Integer loginType) {

        return new CustomUsernamepasswordToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, Integer loginType) {
        return new CustomUsernamepasswordToken(username, password, loginType);
    }

    private String getLoginType(ServletRequest request) {
        return WebUtils.getCleanParam(request, "loginType");
    }

}