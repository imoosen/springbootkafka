package com.imoosen.test;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mengsen on 2017/8/23.
 *
 * @Description: [一句话描述该类的功能]
 * @UpdateUser: mengsen on 2017/8/23.
 */
public class ConsumerMain {

    public ConsumerConfig consumerConfig(){
        Properties pro = new Properties();
        pro.setProperty("metadata.broker.list","127.0.0.1:9092");
        // zookeeper 配置
        pro.put("zookeeper.connect", "127.0.0.1:2181");
        // group 代表一个消费组
        pro.put("group.id", "test-consumer-group");
        //zk连接超时
        pro.put("zookeeper.session.timeout.ms", "4000");
        pro.put("zookeeper.sync.time.ms", "200");
        pro.put("auto.commit.interval.ms","1000");
        pro.put("rebalance.max.retries", "5");
        pro.put("rebalance.backoff.ms", "1200");

        pro.put("auto.offset.reset", "smallest");
        //序列化类
        pro.put("serializer.class", "kafka.serializer.StringEncoder");
        ConsumerConfig config =new ConsumerConfig(pro);
        return config;
    }

    public ConsumerConnector getConsumeConnector(ConsumerConfig consumerConfig){
        return Consumer.createJavaConsumerConnector(consumerConfig);
    }

    public static void main(String[] args) {

        ConsumerMain cm =new ConsumerMain();
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put("mengsen", new Integer(1)); // 一次从主题中获取一个数据
        ConsumerConnector consumer = cm.getConsumeConnector(cm.consumerConfig());
        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get("mengsen").get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext()){
           // System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + it.next().message() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        MessageAndMetadata<String, String> message = it.next();
        String key = message.key();
        String value = message.message();
        System.out.println("key+++"+key);
        System.out.println("value+++"+value);}

    }

}

