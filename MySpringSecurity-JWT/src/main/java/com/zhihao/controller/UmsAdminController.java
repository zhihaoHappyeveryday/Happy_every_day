package com.zhihao.controller;

import com.zhihao.entity.CommonResult;
import com.zhihao.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 15/1/2020 下午 9:44
 * @Description:
 * @Versions 1.0
 **/
@RestController
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    private Map<String,Object> resultMap;

    /**
     * 登录接口,登录成功返回token
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public CommonResult login(String username,String password){
        resultMap = new HashMap<>();
        String token = umsAdminService.login(username, password);
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        resultMap.put("token", token);
        return CommonResult.success(resultMap);
    }

    /**
     * 登出功能
     * @return
     */
    @PostMapping(value = "/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    /**
     * 刷新token
     * @param request
     * @return
     */
    @GetMapping(value = "/refreshToken")
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        //获取去掉前缀请求头的token
        String token = request.getHeader(tokenHeader).substring(6);
        String refreshToken = umsAdminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        return CommonResult.success(tokenMap);
    }

    /**
     * 权限测试接口
     * @return
     */
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult getBrandList() {
        return CommonResult.success("权限访问成功");
    }
}
