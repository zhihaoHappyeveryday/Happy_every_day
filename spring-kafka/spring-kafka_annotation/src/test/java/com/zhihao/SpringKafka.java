package com.zhihao;

import com.zhihao.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {KafkaConfig.class})
public class SpringKafka {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String,Message> kafkaTemplate;

    @Test
    public void sendMessage() throws InterruptedException {
        ListenableFuture<SendResult<String, Message>> mykafkaDemo = null;
        //int i = 1;
        for (int i = 0; i < 2; i++) {
            mykafkaDemo = kafkaTemplate.send("mykafkaDemo", "key" + i, new Message("love", String.valueOf(i)));
           final int j = i;
            //添加回调......
            Thread.sleep(1000);
        }
    }
}
