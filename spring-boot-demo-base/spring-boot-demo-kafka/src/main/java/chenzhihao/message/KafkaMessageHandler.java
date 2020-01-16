package chenzhihao.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @Author: zhihao
 * @Date: 8/1/2020 下午 9:19
 * @Description: 消息消费者
 * @Versions 1.0
 **/
@Component
@Slf4j
public class KafkaMessageHandler {

    /***
     * 接收消息后手动提交
     *
     * @param record 消费记录
     * @param acknowledgment 确认接收
     * @return void
     * @author: zhihao
     * @date: 8/1/2020
     */

    @KafkaListener(topics = "test",containerFactory = "ackContainerFactory")
    public void handlerMessage(ConsumerRecord record, Acknowledgment acknowledgment){
        try {
            //手动接收消息
            String value = (String) record.value();
            System.out.println("手动接收<<接收到消息,进行消费>>>"+value);
        } catch (Exception e) {
            log.error("手动接收<<消费异常信息>>>"+e.getMessage());
        }finally {
            //最终提交确认接收到消息
            acknowledgment.acknowledge();
        }
    }

//    /***
//     * 接收消息后自动提交
//     *
//     * @param message 消息
//     * @return void
//     * @author: zhihao
//     * @date: 8/1/2020
//     */
//    @KafkaListener(topics = "test",groupId = "test-consumer")
//    public void handlerMessage(String message){
//        System.out.println("接收到自动确认消息"+message);
//    }
}
