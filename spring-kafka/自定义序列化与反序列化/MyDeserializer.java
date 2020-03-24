package com.zhihao;


import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/3/24 17:11
 * @Description:
 * @Versions 1.0
 **/
public class MyDeserializer implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return this.byteToBean(data);
    }

    public Object byteToBean(byte[] data) {
        Object readObject = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            readObject = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readObject;
    }
}
