package com.yh.imserver.admin.controller.message;


import cn.hutool.json.JSONUtil;
import com.yh.imserver.admin.dto.MessageSendDTO;
import com.yh.imserver.admin.dto.ReceiveDTO;
import com.yh.imserver.common.core.controller.BaseController;
import com.yh.imserver.common.core.domain.AjaxResult;
import com.yh.imserver.common.core.domain.entity.SysUser;
import com.yh.imserver.common.core.domain.model.LoginUser;
import com.yh.imserver.common.utils.WebSocketUtils;
import com.yh.imserver.framework.WebSocketServer;
import com.yh.imserver.netty.mqtt.client.MqttClient;
import com.yh.imserver.netty.mqtt.client.MqttClientFactory;
import com.yh.imserver.netty.mqtt.client.MqttConnectParameter;
import com.yh.imserver.netty.mqtt.client.callback.MqttCallback;
import com.yh.imserver.netty.mqtt.client.callback.MqttChannelExceptionCallbackResult;
import com.yh.imserver.netty.mqtt.client.callback.MqttReceiveCallbackResult;
import com.yh.imserver.netty.mqtt.client.constant.MqttVersion;
import com.yh.imserver.netty.mqtt.client.msg.MqttSubInfo;
import com.yh.imserver.system.service.ISysUserService;
import com.yh.imserver.utils.PropertiesUtils;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/message")
@Slf4j
@Lazy
public class MessageController extends BaseController {

    @Resource
    private ISysUserService userService;

    @Resource
    private MqttClientFactory mqttClientFactory;

    @Resource
    private WebSocketServer webSocketServer;


    @PostConstruct
    public void init() throws IOException {
        PropertiesUtils.loadTestProperties();
        subScribe();
    }

    @PostMapping("/send")
    public AjaxResult sendReceiveMessageQos0(@RequestBody MessageSendDTO messageSendDTO) {
        String host = PropertiesUtils.getHost();
        int port = PropertiesUtils.getPort();
        LoginUser loginUser = getLoginUser();
        byte[] bytes = messageSendDTO.getContent().getBytes(StandardCharsets.UTF_8);
        MqttQoS mqttQoS = MqttQoS.AT_MOST_ONCE;
        MqttConnectParameter mqttConnectParameter = new MqttConnectParameter(loginUser.getUsername());
        mqttConnectParameter.setHost(host);
        mqttConnectParameter.setPort(port);
        mqttConnectParameter.setMqttVersion(MqttVersion.MQTT_3_1_1);
        MqttClient mqttClient = mqttClientFactory.createMqttClient(mqttConnectParameter);
        //发送消息
        if (!mqttClient.isActive() || !mqttClient.isOnline()) {
            mqttClient.connect();
        }
        mqttClient.publish(bytes, messageSendDTO.getTopic(), mqttQoS);
         return AjaxResult.success("发送成功");

    }


    public void subScribe() {
        List<MqttSubInfo> mqttSubInfoList = userService.selectUserList(new SysUser()).stream()
                .map(user -> new MqttSubInfo(user.getUserName(), MqttQoS.AT_MOST_ONCE))
                .collect(Collectors.toList());
        String host = PropertiesUtils.getHost();
        int port = PropertiesUtils.getPort();
        MqttConnectParameter mqttConnectParameter = new MqttConnectParameter("admin");
        mqttConnectParameter.setHost(host);
        mqttConnectParameter.setPort(port);
        mqttConnectParameter.setMqttVersion(MqttVersion.MQTT_3_1_1);
        MqttClient mqttClient = mqttClientFactory.createMqttClient(mqttConnectParameter);
        mqttClient.addMqttCallback(new MqttCallback() {
            @Override
            public void messageReceiveCallback(MqttReceiveCallbackResult receiveCallbackResult) {
                ReceiveDTO receiveDTO = new ReceiveDTO();
                receiveDTO.setTopic(receiveCallbackResult.getTopic());
                receiveDTO.setMessage(new String(receiveCallbackResult.getPayload()));
                System.out.println("接收到消息："+receiveCallbackResult.getPayload());
//                WebSocketUtils.sendAllMessage(receiveDTO);
                WebSocketUtils.sendMessageByUser(receiveCallbackResult.getTopic(),JSONUtil.parseObj(receiveDTO).toString());
            }

            @Override
            public void channelExceptionCaught(MqttConnectParameter mqttConnectParameter, MqttChannelExceptionCallbackResult mqttChannelExceptionCallbackResult) {
                Throwable cause = mqttChannelExceptionCallbackResult.getCause();
                System.out.println(cause);



            }
        });
        //发送消息
        if (!mqttClient.isActive() || !mqttClient.isOnline()) {
            mqttClient.connect();
        }
        mqttClient.subscribes(mqttSubInfoList);
    }


}
