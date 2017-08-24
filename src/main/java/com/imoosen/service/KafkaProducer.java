package com.imoosen.service;



import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;


/**
 * Created by mengsen on 2017/8/23.
 *
 * @Description: [一句话描述该类的功能]
 * @UpdateUser: mengsen on 2017/8/23.
 */
@Service
public class KafkaProducer {

    private final Logger log = Logger.getLogger(KafkaProducer.class);

    @Autowired
    private Producer<String, String> producer;

    public boolean sendMessage(String topic,String message){
        KeyedMessage<String,String> keyedMessage = new KeyedMessage(topic,message);
        try{
            producer.send(keyedMessage);
            log.info("Kafka message send success");
            return true;
        }catch(Exception e)
        {
            log.error(MessageFormat.format("Kafka message send failed:{}{}",topic.concat("  "+message)),e);
            return false;
        }
    }

    public void sendMessage(String topic,String key,String partKey,String message){
        KeyedMessage<String,String> keyedMessage = new KeyedMessage(topic,key, partKey,message);
            producer.send(keyedMessage);
            log.info("Kafka message send success");

    }


}
