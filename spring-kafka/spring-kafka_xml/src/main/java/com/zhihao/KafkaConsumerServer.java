package com.zhihao;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @Author: zhihao
 * @Date: 2020/3/23 16:05
 * @Description: 实现的是确认消息监听器
 * @Versions 1.0
 **/
public class KafkaConsumerServer implements AcknowledgingMessageListener<String, String> {


    /**
     * 接收消息手动提交
     *
     * @param message
     * @param acknowledgment
     * @return void
     * @author: zhihao
     * @date: 2020/3/23
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) {
        String topic = message.topic();
        System.out.println(topic);
        System.out.println("--------topic--------");
        System.out.println(message.key());
        System.out.println("---------key-------");
        System.out.println(message.value());
        //最终提交确认接收到消息  手动提交 offset
        acknowledgment.acknowledge();
    }

    /**
     * 接收消息后自动提交offset 需要配置开启enable-auto-commit: true
     *
     * @param message
     * @return void
     * @author: zhihao
     * @date: 2020/3/23
     */
//    @Override
//    public void onMessage(ConsumerRecord<String, String> message) {
//        String topic = message.topic();
//        System.out.println(topic);
//        System.out.println("--------topic1--------");
//        System.out.println(message.key());
//        System.out.println("---------key1-------");
//        System.out.println(message.value());
//    }
}
