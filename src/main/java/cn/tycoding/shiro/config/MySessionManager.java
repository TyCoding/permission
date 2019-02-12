package cn.tycoding.shiro.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-01-23
 */
public class MySessionManager extends DefaultWebSessionManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTHORIZATION = "X-Token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        logger.info("==> already to MySessionManager");
        //获取请求头中X-Token中保存的sessionId
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则默认从cookie中获取sessionId
            return super.getSessionId(request, response);
        }
    }
}
