package kafkaProducerJson;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import kafkaProducerJson.Student;

import io.confluent.kafka.serializers.KafkaJsonSerializer;


public class TestProducerJson {
	public static final String TOPIC="TestTopic2";
	public static final String BOOTSTRAP_SERVERS="localhost:9092";
	
	public static void main (String args[]) {
		KafkaProducer<String,Student> producer=null;
		
		try {
			Properties prop = new Properties();
			prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
			prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,KafkaJsonSerializer.class);
	
			producer = new KafkaProducer<String,Student>(prop);
			Student s = new Student("Mrudang","07IT416","IT");
			ProducerRecord<String,Student> pr = new ProducerRecord<String,Student>(TOPIC,s);
		producer.send(pr);
		}finally{
			if(producer !=null) {
				producer.flush();
				producer.close();
			}
		}
		
	}

}
