package com.test.kafkaConsumerJsonOffsetBased;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;





public class TestConsumerJson {
	public static final String TOPIC="TestTopic3";
	public static final String BOOTSTRAP_SERVERS="localhost:9092";
	
	public static void main (String args[]) {
		KafkaConsumer<String,Student> consumer=null;
		
		try {
			Properties prop = new Properties();
			prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
			prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,KafkaJsonDeserializer.class);
			prop.put(ConsumerConfig.GROUP_ID_CONFIG,"testtopic2");
			prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
			prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
			prop.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, Student.class.getName());

			TopicPartition p1 = new TopicPartition(TOPIC, 0);
			TopicPartition p2 = new TopicPartition(TOPIC, 1);
	
			consumer = new KafkaConsumer<String,Student>(prop);
			consumer.assign(Arrays.asList(p1,p2));
			
			consumer.seek(p1, 0);
			consumer.seek(p2, 0);
			
			RebalanceListener reb = new RebalanceListener(consumer);
	
			while(true) {
			
				ConsumerRecords<String,Student> cr = consumer.poll(Duration.ofMillis(100));
				for(ConsumerRecord<String,Student>  record : cr){  
					
	                System.out.println("Key: "+ record.key() + ", Id:" +record.value().getName());  
	                System.out.println("Partition:" + record.partition()+",Offset:"+record.offset());
	                
	                reb.addOffset(TOPIC, record.partition(), record.offset());
	            }  
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
