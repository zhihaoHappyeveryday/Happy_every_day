package com.zhihao.common.orderpay.enums;

/**
 * 支付方式枚举
 *
 * @author: zhihao
 * @date: 2020/4/10
 */
public enum PayType {

    ZFB_APP("支付宝App方式","01"),
    WX_APP("微信App方式","02");

    private String type;

    private String code;

    PayType(String type,String code) {
        this.type = type;
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
