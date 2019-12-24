package com.zhihao.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhihao.util.JWTToken;
import com.zhihao.util.JwtUtil;
import com.zhihao.util.SpringContextUtil;
import lombok.SneakyThrows;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String TOKEN = "Token";

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Map errorMap;


    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        JwtUtil JwtUtil = SpringContextUtil.getBean(JwtUtil.class);
        //判断是否是登录请求
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String[] split = StringUtils.split(JwtUtil.getAnonUrl(), ",");
        for (String url : split) {
            //如果是后端免认证接口 直接放行
            if (pathMatcher.match(url,httpServletRequest.getRequestURI())){
                return true;
            }
        }
        //进入executeLogin方法判断请求的请求头是否带上 "Token"
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            return executeLogin(request, response);
        }else {
            this.tokenError(response,"token为空");
        }
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN);
        try {
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            getSubject(request, response).login(new JWTToken(token));
            // 如果没有抛出异常则代表登入成功，返回true
            return true;
        } catch (Exception e) {
            this.tokenError(response, "token认证失败");
            return false;
        }
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(TOKEN);
        return token != null;
    }

    /**
     * token问题响应
     *
     * @param response
     * @param msg
     * @return void
     * @author: zhihao
     * @date: 2019/12/24
     * {@link #}
     */
    private void tokenError(ServletResponse response,String msg) throws IOException {
        errorMap = new LinkedHashMap();
        errorMap.put("code", "error");
        errorMap.put("msg", msg);
        //响应token为空
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.resetBuffer(); //清空第一次流响应的内容
        //转成json格式
        ObjectMapper object = new ObjectMapper();
        String asString = object.writeValueAsString(errorMap);
        response.getWriter().println(asString);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
