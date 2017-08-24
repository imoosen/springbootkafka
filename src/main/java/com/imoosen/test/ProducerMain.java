package com.imoosen.test;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by mengsen on 2017/8/23.
 *
 * @Description: [一句话描述该类的功能]
 * @UpdateUser: mengsen on 2017/8/23.
 */
public class ProducerMain {

    public ProducerConfig producerConfig(){
        Properties pro = new Properties();
        //配置kafka的端口
        pro.put("metadata.broker.list","127.0.0.1:9092");
        pro.put("zk.connect", "127.0.0.1:2181");

        // 配置value的序列化类
        pro.put("serializer.class", "kafka.serializer.StringEncoder");

        // 配置key的序列化类
        pro.put("key.serializer.class", "kafka.serializer.StringEncoder");

        // 可选配置，如果不配置，则使用默认的partitioner
        pro.put("partitioner.class", "kafka.producer.DefaultPartitioner");

        // 值为0,1,-1,可以参考
        // http://kafka.apache.org/08/configuration.html
        pro.put("request.required.acks", "-1");

        ProducerConfig config = new ProducerConfig(pro);
        return config;
    }

    public Producer<String,String> getProducer(ProducerConfig producerConfig){
        return new Producer<String, String>(producerConfig);
    }

    public static void main(String[] args) {
        KeyedMessage<String,String> keyedMessage = new KeyedMessage("mengsen","这是kafka3");
        ProducerMain pm = new ProducerMain();
        pm.getProducer(pm.producerConfig()).send(keyedMessage);
        System.out.println("发送成功");
    }

}
