package cn.tycoding.monitor.service.impl;

import cn.tycoding.common.utils.AddressUtil;
import cn.tycoding.monitor.entity.OnlineUser;
import cn.tycoding.monitor.service.SessionService;
import cn.tycoding.system.entity.User;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 在线会话管理，可参看
 * 张开涛老师的博文：https://zm10.sm-tc.cn/?src=l4uLj4zF0NCVlpGRlp6RjJeWk5CRmJGWnpHRlouahprRnJCS0J2TkJjQzc%2FLyMnLzA%3D%3D&from=derive&depth=3&link_type=60&wap=false&v=1&uid=03200e6c3a76bced6b1828a8cf8d6404&restype=1
 * Mrbird博文：https://mrbird.cc/Spring-Boot-Shiro%20session.html
 *
 * @author tycoding
 * @date 2019-02-14
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<OnlineUser> list() {
        List<OnlineUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions(); //获取在线会话的集合
        for (Session session : sessions) {
            if (session != null) {
                OnlineUser onlineUser = new OnlineUser();
                SimplePrincipalCollection principalCollection;
                User user;
                //判断此session是否还在登录状态
                if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                    continue;
                } else {
                    //如果此session正在登录，将此session的数据放入principalCollection集合中，从而可获取登录用户对象数据
                    principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                    user = (User) principalCollection.getPrimaryPrincipal();
                    onlineUser.setUid(user.getId().toString());
                    onlineUser.setUsername(user.getUsername());
                }
                onlineUser.setId(session.getId().toString());
                onlineUser.setHost(session.getHost());
                onlineUser.setAddress(AddressUtil.getAddress(session.getHost()));
                onlineUser.setStartTime(session.getStartTimestamp());
                onlineUser.setEndTime(session.getLastAccessTime());
                long timeout = session.getTimeout();
                onlineUser.setTimeout(timeout);
                onlineUser.setStatus(timeout == 0L ? "0" : "1"); //0在线 1下线
                list.add(onlineUser);
            }
        }
        return list;
    }

    @Override
    public void forceLogout(String id) {
        Session session = sessionDAO.readSession(id);
        session.setTimeout(0L);
        session.stop();
        sessionDAO.delete(session);
    }
}
