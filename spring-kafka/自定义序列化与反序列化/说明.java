		
		//生产者key与value序列化方式----泛型都是object
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		//消费者,使用构造方法,传入包名
		DefaultKafkaConsumerFactory<String, Object> DefaultKafkaConsumerFactory
        = new DefaultKafkaConsumerFactory<>(properties,new StringDeserializer(),new MyJsonDeserializer("com.zhihao.entity"));