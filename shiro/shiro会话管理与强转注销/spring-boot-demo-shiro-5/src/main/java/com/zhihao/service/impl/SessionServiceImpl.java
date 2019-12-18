package com.zhihao.service.impl;

import com.zhihao.dao.UserMapper;
import com.zhihao.entity.User;
import com.zhihao.entity.UserOnline;
import com.zhihao.service.SessionService;
import com.zhihao.service.UserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: zhihao
 * @Date: 2019/12/17 21:50
 * @Description:
 * @Versions 1.0
 **/
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    /**
     * 注入会话dao
     */
    @Autowired
    private SessionDAO sessionDAO;
    /**
     * 注入用户dao
     */
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<UserOnline> findUserOnlineAll() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            User user = new User();
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (User) principalCollection.getPrimaryPrincipal();
                userOnline.setUsername(user.getUsername());
                userOnline.setUserId(user.getId());
            }
            userOnline.setSessionId((String) session.getId());
            userOnline.setHost(session.getHost());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            Long timeout = session.getTimeout();
            if (timeout !=null && timeout.equals(0L)) {
                userOnline.setStatus("离线");
            } else {
                userOnline.setStatus("在线");
            }
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return list;
    }

    private final String LOCK = "0";

    @Override
    public boolean forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        SimplePrincipalCollection principalCollection2 = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        User user = (User) principalCollection2.getPrimaryPrincipal();
        //锁号  修改用户表的用户状态为锁号
        if (this.lockNumber(user.getId(), LOCK)){
            //强制注销
            sessionDAO.delete(session);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean lockNumber(String id, String status) {
        return userMapper.lockNumber(id, status) == 1;
    }

    @Override
    public List<User> findLockNumberAll() {
        return userMapper.findLockNumberAll();
    }
}
