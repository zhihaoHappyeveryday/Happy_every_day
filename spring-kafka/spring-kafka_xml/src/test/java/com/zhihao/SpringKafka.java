package com.zhihao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Author: zhihao
 * @Date: 2020/3/23 14:12
 * @Description:
 * @Versions 1.0
 **/
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-kafka-producer.xml","classpath:spring-kafka-consumer.xml"})
public class SpringKafka {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Test
    public void sendMessage() throws InterruptedException {
        ListenableFuture<SendResult<String, String>> mykafkaDemo = null;
        for (int i = 0; i < 100; i++) {
            mykafkaDemo = kafkaTemplate.send("mykafkaDemo", "name" + i, "love" + i);
        }

        //可选添加回调,而不使用监听类
        //mykafkaDemo.addCallback();
        Thread.sleep(5000);
    }


    @KafkaListener(topics = "mykafkaDemo",groupId = "mykafka")
    public void handlerMessage(String message){
            System.out.println("接收到自动确认消息"+message);
    }

}
