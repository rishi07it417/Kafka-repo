package com.test.kafkaConsumer;

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





public class TestConsumer {
	public static final String TOPIC="Topic1";
	public static final String BOOTSTRAP_SERVERS="localhost:9092";
	
	public static void main (String args[]) {
		KafkaConsumer<String,String> consumer=null;
		
		try {
			Properties prop = new Properties();
			prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
			prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
			prop.put(ConsumerConfig.GROUP_ID_CONFIG,"topic1");
			prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");


	
			consumer = new KafkaConsumer<String,String>(prop);
			consumer.subscribe(Arrays.asList(TOPIC));
			
			while(true) {
			
				ConsumerRecords<String,String> cr = consumer.poll(Duration.ofMillis(100));
				for(ConsumerRecord<String,String>  record : cr){  
	                System.out.println("Key: "+ record.key() + ", Value:" +record.value());  
	                System.out.println("Partition:" + record.partition()+",Offset:"+record.offset());  
	            }  
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
