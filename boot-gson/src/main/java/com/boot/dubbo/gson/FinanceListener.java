package com.boot.dubbo.gson;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2021-05-12 20:50
 */
@Slf4j
@Component
public class FinanceListener {

    @KafkaListener(topics = {"finance_db.finance_db.finance_withdraw_record"}, groupId = "finance_db.finance_db.finance_withdraw_record")
    public void onMessage(ConsumerRecord<Object, Object> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        String s = record.value().toString();
        System.out.println(s);
        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }
}
