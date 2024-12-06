package com.yh.imserver.framework;

import com.yh.imserver.common.core.domain.model.LoginUser;
import com.yh.imserver.common.core.redis.RedisCache;
import com.yh.imserver.common.utils.WebSocketUtils;
import com.yh.imserver.common.utils.spring.SpringUtils;
import com.yh.imserver.framework.web.service.TokenService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Objects;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {
    private String userName;
    private Session session;


    /**
     * To execute when it connected
     *
     * @param
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接成功");
        // 获取查询参数
        String queryString = session.getQueryString();
        Map<String, String> queryParams = parseQueryString(queryString);

        // 提取 Token
        String token = queryParams.get("Authorization");
        if (token == null || token.isEmpty()) {
            System.out.println("No token provided, closing connection.");
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Missing token"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("websocketToken:" + token);
            // 根据 Token 进行身份验证
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(token);
            if (!Objects.isNull(loginUser)) {
                this.session=session;
                this.userName=loginUser.getUsername();
                WebSocketUtils.serverClients.put(loginUser.getUsername(), this);
            }
        }

    }

    /**
     * To execute when get message
     *
     * @param message
     * @param session
     * @return
     */
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("onMessage" + this.userName + "：" + message);
        return this.userName + "：" + message;
    }

    /**
     * To execute when closed the connector
     *
     * @param session
     * @param closeReason
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        WebSocketUtils.serverClients.remove(session.getId());
        System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    /**
     * To execute when an error occurs
     *
     * @param t
     */
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    // 工具方法：解析查询字符串
    private Map<String, String> parseQueryString(String query) {
        Map<String, String> queryMap = new java.util.HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                queryMap.put(key, value);
            }
        }
        return queryMap;
    }
}
