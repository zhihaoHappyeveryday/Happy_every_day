package com.chenzhihao;

import chenzhihao.ApplicationKafka;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Author: zhihao
 * @Date: 8/1/2020 下午 9:36
 * @Description: 测试发送消息
 * @Versions 1.0
 **/
@SpringBootTest(classes = ApplicationKafka.class)
@RunWith(value = SpringRunner.class)
public class KafkaSendMessage {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /***
     * 简单发送消息
     *
     * @param message 消息
     * @return
     * @author: zhihao
     * @date: 8/1/2020
     * {@link #}
     */
    public void testSend(String message){
        //向test主题发送消息
        kafkaTemplate.send("test",message);
    }


    /***
     * 发送消息获取发送成功或者失败
     *
     * @param message 消息
     * @return
     * @author: zhihao
     * @date: 8/1/2020
     * {@link #}
     */
    public void Send(String message){
        //向test主题发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test", message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.printf("消息：{} 发送失败，原因：{}", message, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                System.out.printf("成功发送消息：{}，offset=[{}]", message, stringStringSendResult.getRecordMetadata().offset());
            }
        });

    }

    @Test
    public void test(){
        this.testSend("这是一个简单发送消息测试");

        this.Send("这是一个发送消息获取发送结果测试");
    }


}
