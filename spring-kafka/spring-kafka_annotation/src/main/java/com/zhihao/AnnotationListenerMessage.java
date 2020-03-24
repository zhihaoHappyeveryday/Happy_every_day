package com.zhihao;

import com.zhihao.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @Author: zhihao
 * @Date: 2020/3/23 18:18
 * @Description:
 * @Versions 1.0
 **/
public class AnnotationListenerMessage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *  消费消息
     *  <p>
     *      消费者分组的组名为Mykafka
     *      监听的主题是mykafkaDemo
     *  </p>
     *
     * @param message
     * @param acknowledgment
     * @author: zhihao
     * @date: 2020/3/24
     * @return
     */
    /*@KafkaListener(groupId = "Mykafka",topics = "mykafkaDemo")
    @SendTo(value = "result") //
    public String myAnnotationMessage(@Payload @Validated String message,
                                      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                      Acknowledgment acknowledgment){
        logger.info("接收消息message: {}", message);
        logger.info("接收消息key: {}", key);
        //最终提交确认接收到消息  手动提交 offset
        acknowledgment.acknowledge();
        //返回成功消费信息
        return message;
    }*/
    @KafkaListener(groupId = "Mykafka",topics = "mykafkaDemo")
    @SendTo(value = "result") //
    public String myAnnotationMessage(Message message,
                                      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                      Acknowledgment acknowledgment){
        logger.info("接收消息message: {}", message.toString());
        logger.info("接收消息key: {}", key);
        //最终提交确认接收到消息  手动提交 offset
        acknowledgment.acknowledge();
        //返回成功消费信息
        return message.getMessage();
    }

//    @KafkaListener(topics = "mykafkaDemo")
//    public void myAnnotationMessage(ConsumerRecord<?, ?> message){
//        String topic = message.topic();
//        System.out.println(topic);
//        System.out.println("--------topic--------");
//        System.out.println(message.key());
//        System.out.println("---------key-------");
//        System.out.println(message.value());
//    }
}
