package com.zhihao.service;

import com.zhihao.entity.User;
import com.zhihao.entity.UserOnline;

import java.util.List;

/**
 * 获取会话信息与踢人功能并锁号
 */
public interface SessionService {
    /**
     *  获取所有在线用户
     *
     * @return java.util.List<com.zhihao.entity.UserOnline>
     * @author: zhihao
     * @date: 2019/12/17
     */
    List<UserOnline> findUserOnlineAll();
    /**
     *  强转注销用户并锁号
     *
     * @param sessionId 用户会话id
     * @return boolean
     * @author: zhihao
     * @date: 2019/12/17
     */
    boolean forceLogout(String sessionId);

    /**
     * 根据用户id进行锁号与解锁
     *
     * @param id 用户名
     * @param status 状态
     * @return boolean
     * @author: zhihao
     * @date: 2019/12/18
     */
    boolean lockNumber(String id,String status);

    /**
     * 获取所有锁号用户
     *
     * @return java.util.List<com.zhihao.entity.User>
     * @author: zhihao
     * @date: 2019/12/18
     */
    List<User> findLockNumberAll();
}
