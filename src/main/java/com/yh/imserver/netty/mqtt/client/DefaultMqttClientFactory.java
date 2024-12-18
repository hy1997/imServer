package com.yh.imserver.netty.mqtt.client;

import com.yh.imserver.netty.mqtt.client.connector.MqttConnector;
import com.yh.imserver.netty.mqtt.client.createor.ObjectCreator;
import com.yh.imserver.netty.mqtt.client.handler.MqttDelegateHandler;
import com.yh.imserver.netty.mqtt.client.plugin.Interceptor;
import com.yh.imserver.netty.mqtt.client.store.MqttMsgStore;
import com.yh.imserver.netty.mqtt.client.support.proxy.ProxyFactory;
import com.yh.imserver.netty.mqtt.client.support.util.AssertUtils;
import com.yh.imserver.netty.mqtt.client.support.util.LogUtils;
import io.netty.channel.ChannelOption;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的MQTT客户端工厂
 * @author: xzc-coder
 */
@Component
public class DefaultMqttClientFactory implements MqttClientFactory {

    /**
     * 全局的MQTT配置，此工厂下创建的所有MQTT客户端都应该使用该配置
     */
    private final MqttConfiguration mqttConfiguration;
    /**
     * MQTT客户端MAP，用来避免重复创建，同时创建相同ClientId的MqttClient会导致不可预测的问题
     */
    private static final Map<String, MqttClient> MQTT_CLIENT_MAP = new ConcurrentHashMap<>();

    public DefaultMqttClientFactory() {
        this(1);
    }

    public DefaultMqttClientFactory(int maxThreadNumber) {
        this(new MqttConfiguration(maxThreadNumber));
    }

    public DefaultMqttClientFactory(MqttConfiguration mqttConfiguration) {
        AssertUtils.notNull(mqttConfiguration, "mqttConfiguration is null ");
        this.mqttConfiguration = mqttConfiguration;
        this.mqttConfiguration.setMqttClientFactory(this);
    }

    @Override
    public MqttClient createMqttClient(MqttConnectParameter mqttConnectParameter) {
        if(null!=MQTT_CLIENT_MAP.get(mqttConnectParameter.getClientId())){
            return MQTT_CLIENT_MAP.get(mqttConnectParameter.getClientId());
        }
        AssertUtils.notNull(mqttConnectParameter, "mqttConnectParameter is null");
        MqttClient mqttClient = mqttConfiguration.newMqttClient(this, mqttConfiguration, mqttConnectParameter);
        MQTT_CLIENT_MAP.put(mqttClient.getClientId(), mqttClient);
        mqttClient.connect();
        return mqttClient;
    }

    @Override
    public void closeMqttClient(String clientId) {
        MqttClient mqttClient = MQTT_CLIENT_MAP.get(clientId);
        if (mqttClient != null) {
            mqttClient.close();
        }
    }

    @Override
    public void releaseMqttClientId(String clientId) {
        MQTT_CLIENT_MAP.remove(clientId);
    }

    @Override
    public void setProxyFactory(ProxyFactory proxyFactory) {
        this.mqttConfiguration.setProxyFactory(proxyFactory);
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        this.mqttConfiguration.addInterceptor(interceptor);
    }

    @Override
    public void setMqttClientObjectCreator(ObjectCreator<MqttClient> mqttClientObjectCreator) {
        this.mqttConfiguration.setMqttClientObjectCreator(mqttClientObjectCreator);
    }

    @Override
    public void setMqttConnectorObjectCreator(ObjectCreator<MqttConnector> mqttConnectorObjectCreator) {
        this.mqttConfiguration.setMqttConnectorObjectCreator(mqttConnectorObjectCreator);
    }

    @Override
    public void setMqttDelegateHandlerObjectCreator(ObjectCreator<MqttDelegateHandler> mqttDelegateHandlerObjectCreator) {
        this.mqttConfiguration.setMqttDelegateHandlerObjectCreator(mqttDelegateHandlerObjectCreator);
    }

    @Override
    public void setMqttMsgStore(MqttMsgStore mqttMsgStore) {
        this.mqttConfiguration.setMqttMsgStore(mqttMsgStore);
    }

    @Override
    public MqttConfiguration getMqttConfiguration() {
        return this.mqttConfiguration;
    }

    @Override
    public void option(ChannelOption option, Object value) {
        this.mqttConfiguration.option(option, value);
    }

    @Override
    public synchronized void close() {
        try {
            LogUtils.info(DefaultMqttClientFactory.class, "MqttClientFactory close");
            closeClient();
        } finally {
            this.mqttConfiguration.close();
        }
    }

    /**
     * 关闭客户端
     */
    private void closeClient() {
        Set<String> clientIdSet = MQTT_CLIENT_MAP.keySet();
        for (String clientId : clientIdSet) {
            MqttClient mqttClient = MQTT_CLIENT_MAP.get(clientId);
            if (mqttClient != null && !mqttClient.isClose()) {
                try {
                    mqttClient.close();
                } catch (Exception e) {
                    LogUtils.warn(DefaultMqttClientFactory.class, "client:" + clientId + "close failed,cause : " + e.getMessage());
                }
            }
        }

    }

}
