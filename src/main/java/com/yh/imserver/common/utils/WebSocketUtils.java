package com.yh.imserver.common.utils;


import cn.hutool.json.JSONUtil;
 import com.yh.imserver.admin.dto.ReceiveDTO;
import com.yh.imserver.framework.WebSocketServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketUtils {
	public static final Map<String, WebSocketServer> serverClients = new ConcurrentHashMap<String, WebSocketServer>();

	public static Boolean sendMessageByUser(String userName, String text) {
		try {
			for (WebSocketServer server : serverClients.values()) {
				if (userName.equals(server.getUserName()) && server.getSession() != null
						&& server.getSession().isOpen()) {
					server.getSession().getAsyncRemote().sendText(text);
				}
			}

		} catch (Exception e) {

		}
		return false;
	}

	public static void sendAllMessage(ReceiveDTO receiveDTO) {
		try{
			for (WebSocketServer server : serverClients.values()) {
				if (server.getSession() != null && server.getSession().isOpen()) {
					server.getSession().getAsyncRemote().sendText(JSONUtil.parseObj(receiveDTO).toString());
				}
			}
		}catch (Exception e){
			log.error("websocket发送错误！",e);
		}

	}
}
