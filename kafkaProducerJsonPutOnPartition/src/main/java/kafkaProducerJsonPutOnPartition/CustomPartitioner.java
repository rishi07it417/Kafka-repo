package kafkaProducerJsonPutOnPartition;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

public class CustomPartitioner implements Partitioner {
	
	private String myclass;

	public void configure(Map<String, ?> configs) {
		myclass = configs.get("myclass.name").toString();
	}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		 	List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
	        int numPartitions = partitions.size();
	        System.out.println("numPartitions = " + numPartitions);

	        int sp = (int) Math.abs(numPartitions * 0.4);
	        System.out.println("sp = " + sp);

	        int p = 0;

			if ((keyBytes == null) || (!(key instanceof String)))
	            throw new NullPointerException();

	        if (((String) key).equals(myclass)) {
	            p = Utils.toPositive(Utils.murmur2(valueBytes)) % sp;
	        }else {
	            p = Utils.toPositive(Utils.murmur2(valueBytes)) % (numPartitions - sp) + sp;
	        }
	        System.out.println("Key = " +  key.toString() + " Partition = " + p);
		
		return p;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
