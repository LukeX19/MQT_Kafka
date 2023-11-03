package producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class AvroProducer {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyProducer.class);

    private static final String OUR_BOOTSTRAP_SERVERS = ":9092";
    // private static final String OUR_BOOTSTRAP_SERVERS = "localhost:9092, localhost:9093, localhost:9094";
    private static final String OUR_CLIENT_ID = "firstProducer";

    private static Producer<String, AvroCompany> producer;

    public static void main(String[] args){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, OUR_BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, OUR_CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url","http://localhost:8081");

        props.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,1048576);

        producer = new KafkaProducer<>(props);

        send("events2");
    }

    public static void send(String topic){
        final int number = new Random().nextInt(10);
        AvroCompany my_avroComp = new AvroCompany();
        my_avroComp.setRegisteredName("Amazon");
        my_avroComp.setTradeNumber(275);

        ProducerRecord<String, AvroCompany> data = new ProducerRecord<>(topic, "key"+number, my_avroComp);
        try {
            RecordMetadata meta = producer.send(data).get();
            LOG.info("key = {}, value = {} ==> partition = {}, offset = {}", data.key(), data.value(), meta.partition(), meta.offset());
        }catch (InterruptedException | ExecutionException e){
            producer.flush();
        }
    }
}
