package com.zhihao.controller;

import com.zhihao.entity.User;
import com.zhihao.entity.UserOnline;
import com.zhihao.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作session控制层  所有api都需要管理员角色
 */
@RestController
@RequestMapping("/online")
public class SessionController {
    @Autowired
    private SessionService sessionService;


    private Map<String,String> resultMap = new HashMap<>();

    /**
     * 获取所有在线用户并跳转页面
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author: zhihao
     * @date: 2019/12/18
     */
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/list")
    public ModelAndView list() {
        List<UserOnline> userOnlineAll = sessionService.findUserOnlineAll();
        ModelAndView view = new ModelAndView();
        view.setViewName("online");
        view.addObject("list",userOnlineAll );
        return view;
    }

    /**
     *  强转注销用户与锁号
     *
     * @param sessionId 会话id
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2019/12/18
     */
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/forceLogout")
    public Map<String, String> forceLogout(String sessionId) {
        try {
            boolean forceLogout = sessionService.forceLogout(sessionId);
            if (forceLogout){
                resultMap.put("code","success");
                resultMap.put("msg","踢人与锁号成功");
            }
        } catch (Exception e) {
            resultMap.put("code","error");
            resultMap.put("msg","踢人与锁号失败");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     *  获取所有锁号用户
     *
     * @param
     * @return org.springframework.web.servlet.ModelAndView
     * @author: zhihao
     * @date: 2019/12/18
     */
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/locknumber")
    public ModelAndView getLockNumber(){
        ModelAndView view = new ModelAndView();
        List<User> lockNumberAll = sessionService.findLockNumberAll();
        view.addObject("list", lockNumberAll);
        view.setViewName("locknumber");
        return view;
    }

    private final String  UNLOCK = "2";
    /**
     * 解锁账号
     *
     * @param id 用户id
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2019/12/18
     */
    @RequiresRoles(value = {"admin"})
    @RequestMapping("/unlockNumber")
    public Map<String,String> unlockNumber(String id){
        boolean number = sessionService.lockNumber(id, UNLOCK);
        if (!number){
            resultMap.put("code", "error");
            resultMap.put("msg", "解锁失败");
        }
        resultMap.put("code", "success");
        resultMap.put("msg", "解锁成功");
        return resultMap;
    }
}