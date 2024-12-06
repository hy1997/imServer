package com.yh.imserver.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageSendDTO {
    /**
     * 发送的消息
     */
    public String content;
    /**
     * 类型
     */
    public String type;
    /**
     * 发给谁
     */
    public String topic;
    /**
     * 发送时间
     */
    public Date time;

}
