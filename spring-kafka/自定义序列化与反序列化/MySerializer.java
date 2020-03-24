package com.zhihao;


import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/3/24 17:11
 * @Description:
 * @Versions 1.0
 **/
public class MySerializer<T> implements Serializer<T> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, T data) {
        return this.beanToByte(data);
    }

    @Override
    public void close() {

    }

    public static byte[] beanToByte(Object obj) {
        byte[] bb = null;
        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)){
            outputStream.writeObject(obj);
            outputStream.flush();
            bb = byteArray.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bb;
    }
}
