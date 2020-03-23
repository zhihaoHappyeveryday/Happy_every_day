package com.zhihao.resultlistener;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * @Author: zhihao
 * @Date: 2020/3/23 16:17
 * @Description: 发送消息结果监听
 * @Versions 1.0
 **/
public class KafkaProducerListener implements ProducerListener<String,String> {

    @Override
    public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
        System.out.println("发送成功了"+producerRecord.value());
    }

    @Override
    public void onError(ProducerRecord<String, String> producerRecord, Exception exception) {
        System.out.println("发送失败了"+producerRecord.value());
        System.out.println("发送失败了"+exception.getMessage());
    }
}
