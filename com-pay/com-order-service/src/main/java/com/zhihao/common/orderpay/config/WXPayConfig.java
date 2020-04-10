package com.zhihao.common.orderpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhihao
 * @Date: 2020/4/10 12:46
 * @Description: 微信配置参数
 * @Versions 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "wx.app")
public class WXPayConfig {

    private String id;

    private String mchId;

    private String publicKey;

    private String notifyUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
