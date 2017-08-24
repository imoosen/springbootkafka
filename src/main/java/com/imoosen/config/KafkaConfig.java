package com.imoosen.config;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by mengsen on 2017/8/23.
 *
 * @Description: [一句话描述该类的功能]
 * @UpdateUser: mengsen on 2017/8/23.
 */
@Component
public class KafkaConfig {

    @Autowired
    private ConsumerConfig consumerConfig;
    @SuppressWarnings("deprecation")
    @Autowired
    private ProducerConfig producerConfig;

    @Bean(name = "consumerConfig")
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

    @Bean(name="producerConfig")
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
    @Bean(name = "consumerConnector")
    public ConsumerConnector getConsumeConnector(){
        return Consumer.createJavaConsumerConnector(consumerConfig);
    }
    @Bean(name="producer")
    public Producer<String,String> getProducer(){
        return new Producer<String, String>(producerConfig);
    }
}
