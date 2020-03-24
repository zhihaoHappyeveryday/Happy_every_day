package com.zhihao.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @Author: zhihao
 * @Date: 2020/3/24 14:31
 * @Description:
 * @Versions 1.0
 **/
public class ResultListenerMessage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 监听result主题
     *
     * @param message
     * @param acknowledgment
     * @return void
     * @author: zhihao
     * @date: 2020/3/24
     */
    @KafkaListener(topics = "result")
    public void myAnnotationMessage(String message, Acknowledgment acknowledgment){
        logger.info("成功消费消息: {}", message);
        //最终提交确认接收到消息  手动提交 offset
        acknowledgment.acknowledge();
    }
}
