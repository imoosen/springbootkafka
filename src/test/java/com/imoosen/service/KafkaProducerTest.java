package com.imoosen.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by mengsen on 2017/8/23.
 *
 * @Description: [一句话描述该类的功能]
 * @UpdateUser: mengsen on 2017/8/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {
    @Autowired
    private KafkaProducer kafkaProducer;
    @Test
    public void sendMessage() throws Exception {

        kafkaProducer.sendMessage("mengsen","你好kafka");
    }

}