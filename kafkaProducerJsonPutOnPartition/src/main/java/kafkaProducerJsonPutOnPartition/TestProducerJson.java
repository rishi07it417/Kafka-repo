package kafkaProducerJsonPutOnPartition;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.KafkaJsonSerializer;


public class TestProducerJson {
	public static final String TOPIC="TestTopic3";
	public static final String BOOTSTRAP_SERVERS="localhost:9092";
	
	public static void main (String args[]) {
		KafkaProducer<String,Student> producer=null;
		
		try {
			Properties prop = new Properties();
			prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
			prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,KafkaJsonSerializer.class);
	        prop.put("partitioner.class", CustomPartitioner.class);
	        prop.put("myclass.name", "Student");

	
			producer = new KafkaProducer<String,Student>(prop);
			for(int i=0; i<5; i++) {
				Student s = new Student("Rishi"+i,"07IT417","IT-"+i);
				ProducerRecord<String,Student> pr = new ProducerRecord<String,Student>(TOPIC,"Student",s);
		        System.out.println("Student = " + i);

				producer.send(pr);
			}
			
			for(int i=0; i<5; i++) {
				Student s = new Student("Common"+i,"07IT417","IT-"+i);
		        System.out.println("common = " + i);
				ProducerRecord<String,Student> pr = new ProducerRecord<String,Student>(TOPIC,"common",s);
				producer.send(pr);
			}
		}finally{
			if(producer !=null) {
				producer.flush();
				producer.close();
			}
		}
		
	}

}
