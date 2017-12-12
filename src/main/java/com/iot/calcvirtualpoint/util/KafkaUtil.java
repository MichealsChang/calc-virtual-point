package com.iot.calcvirtualpoint.util;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;

/**
 * @author changliang
 * @create 2017-12-12 16:17
 * @desc
 **/
public class KafkaUtil {

    private static String brokerList;
    private static String targetTopic;
    private static Producer producer;

    public static boolean open(String brokers, String topicName) {
        brokerList = brokers;
        targetTopic = topicName;
        producer = createProducer();
        if(producer!=null){
            return true;
        }
        return false;
    }

    private static Producer createProducer() {
        Properties properties = new Properties();
        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("metadata.broker.list", brokerList);// 声明kafka broker
        return new Producer<Integer, String>(new ProducerConfig(properties));
    }

    public static void sendMessage(String message){
        producer.send(new KeyedMessage<String,String>(targetTopic, message));
    }

    public static void close(){
        producer.close();
    }

}
