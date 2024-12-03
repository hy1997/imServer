package com.yh.imserver;

public class TestSmartBeanUtil {
    public static void main(String[] args) {
        try {
            Class.forName("com.yh.imserver.admin.util.SmartBeanUtil");
            System.out.println("SmartBeanUtil loaded successfully.");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
