package kafkaProducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;



public class TestProducer {
	public static final String TOPIC="Topic1";
	public static final String BOOTSTRAP_SERVERS="localhost:9092";
	
	public static void main (String args[]) {
		KafkaProducer<String,String> producer=null;
		
		try {
			Properties prop = new Properties();
			prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
			prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
			prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
	
			producer = new KafkaProducer<String,String>(prop);
			
			ProducerRecord<String,String> pr = new ProducerRecord<String,String>(TOPIC,"First Message From Producer Program");
		producer.send(pr);
		}finally{
			if(producer !=null) {
				producer.flush();
				producer.close();
			}
		}
		
	}

}
