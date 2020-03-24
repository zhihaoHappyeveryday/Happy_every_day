package com.zhihao;

import com.zhihao.entity.Message;
import com.zhihao.result.ResultListenerMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhihao
 * @Date: 2020/3/23 18:30
 * @Description:
 * @Versions 1.0
 **/
@Configuration
@EnableKafka
public class KafkaConfig {

    /**
     * 注册kafka模板
     *
     * @param
     * @return org.springframework.kafka.core.KafkaTemplate<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/3/24
     */
    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    /**
     * 注册生产者工厂,并配置生产者配置
     *
     * @param
     * @return org.springframework.kafka.core.ProducerFactory<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/3/24
     */
    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        HashMap<String, Object> configs = new HashMap<>();
        //bootstrapServers为Kafka生产者的地址
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //确认配置
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        //重试次数
        configs.put(ProducerConfig.RETRIES_CONFIG, 1);
        //批处理数量,每当将多个记录发送到同一分区时，生产者将尝试将记录一起批处理成更少的请求。这有助于提高客户端和服务器的性能
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //批处理延迟时间上限：即10ms过后，不管是否达到批处理数，都直接发送一次请求
        configs.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        //缓冲区内存配置 32MB
        configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //key与value序列化方式
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //value使用kafka提供的JsonSerializer
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        DefaultKafkaProducerFactory<String,Message> kvDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(configs);
        return kvDefaultKafkaProducerFactory;
    }


    /**
     * 默认的卡夫卡消费工厂,并配置消费者配置
     *
     * @param
     * @return org.springframework.kafka.core.DefaultKafkaConsumerFactory<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/3/24
     */
    @Bean
    public DefaultKafkaConsumerFactory<String, Message> defaultKafkaConsumerFactory() {
        Map<String,Object> properties =  new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //费者群组ID，发布-订阅模式，即如果一个生产者，多个消费者都要消费，那么需要定义自己的群组，同一群组内的消费者只有一个能消费到消息
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"Mykafka");
        //自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //提交间隔
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //会话超时MS配置
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        //自动偏移重设
        //earliest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        //latest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        //none:topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        //exception:直接抛出异常
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //key与value序列化方式
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        //设置批量参数,一次调用返回的最大记录数
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        DefaultKafkaConsumerFactory<String, Message> DefaultKafkaConsumerFactory
                = new DefaultKafkaConsumerFactory<>(properties,new StringDeserializer(),new JsonDeserializer<>(Message.class));
        return DefaultKafkaConsumerFactory;
    }

    /**
     * 并发Kafka侦听器容器工厂
     *
     * @return org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<java.lang.String,java.lang.String>
     * @author: zhihao
     * @date: 2020/3/24
     */
    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(defaultKafkaConsumerFactory());
        factory.setConcurrency(3);
        //批处理侦听器
        factory.setBatchListener(true);
        //设置轮询超时
        factory.getContainerProperties().setPollTimeout(3000);
        //设置回复模板
        factory.setReplyTemplate(this.kafkaTemplate());
        //手动提交确认模式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //设置过滤信息
//        factory.setRecordFilterStrategy(new RecordFilterStrategy<String, String>() {
//            @Override
//            public boolean filter(ConsumerRecord<String, String> consumerRecord) {
//                //信息中包含love669就返回true过滤
//                return consumerRecord.value().contains("love669");
//            }
//        });
        return factory;
    }

    /**
     * 创建kafka主题
     *
     * @param
     * @return org.apache.kafka.clients.admin.NewTopic
     * @author: zhihao
     * @date: 2020/3/24
     */
    /*@Bean
    public NewTopic foo() {
        //第一个是参数是topic名字，第二个参数是分区个数，第三个是topic的复制因子个数
        //当broker个数为1个时会创建topic失败，
        //提示：replication factor: 2 larger than available brokers: 1
        //只有在集群中才能使用kafka的备份功能
        return new NewTopic("result", 1, (short) 1);
    }*/

    /**
     * 注册监听类到容器,可以使用注解方式注册(这里是的demo没配置包扫描)
     *
     * @return com.zhihao.AnnotationMessage
     * @author: zhihao
     * @date: 2020/3/24
     */
    @Bean
    public AnnotationListenerMessage annotationListenerMessage(){
        return new AnnotationListenerMessage();
    }

    @Bean
    public ResultListenerMessage resultListenerMessage(){
        return new ResultListenerMessage();
    }
}
