package com.test.kafkaConsumerJsonOffsetBased;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;


public class RebalanceListener implements ConsumerRebalanceListener{
	private KafkaConsumer con;
	private Map<TopicPartition,OffsetAndMetadata> currentOffset = new HashMap();
	
	RebalanceListener(KafkaConsumer consumer){
		con = consumer;
	}
	
	public void addOffset(String topic,int partition, long offset) {
		System.out.println("adding offset:: Topic-"+ topic+"-partition-"+ partition+ "-offset-"+offset);
		currentOffset.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset,"commit"));
	}
	
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		System.out.println("on Revoke....");

		for(TopicPartition p : partitions) {
			System.out.println("partition ::-"+p.partition());

		}
		
		System.out.println("offset committed....");

		Set me =  currentOffset.entrySet();
		Iterator i = me.iterator();
		
		while(i.hasNext()) {
			Map.Entry<TopicPartition,OffsetAndMetadata> mep = (Map.Entry)i.next();
			System.out.println("key::"+mep.getKey()+":: Value::"+mep.getValue());

		}
		
		
				
		con.commitSync();
		currentOffset.clear();
	}

	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		System.out.println("Assignning....");
		for(TopicPartition p : partitions) {
			System.out.println("partition ::-"+p.partition());

		}
	}

}
