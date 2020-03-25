package com.zhihao;

import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * @Author: zhihao
 * @Date: 2020/3/25 9:40
 * @Description: 继承JsonDeserializer实现传输object
 * @Versions 1.0
 **/
public class MyJsonDeserializer extends JsonDeserializer<Object> {


    public MyJsonDeserializer(String... packages) {
        this.addTrustedPackages(packages);
    }

    /**
     * 添加可信包
     *
     * @param packages 包名,例如:com.zhihao.entity
     * @return void
     * @author: zhihao
     * @date: 2020/3/25
     */
    @Override
    public void addTrustedPackages(String... packages) {
            super.addTrustedPackages(packages);
    }
}
