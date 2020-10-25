
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
    private KafkaConsumer<String, String> c;
    private String topic = "test";
    private String propertiesFilePath = "./src/main/resources/consumer.properties";

    public Consumer() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertiesFilePath));
        } catch (IOException e) {
            System.err.println("Failed to load " + propertiesFilePath);
        }
        c = new KafkaConsumer<>(props);
        assignTopic();
    }

    private void assignTopic() {
        c.subscribe(Arrays.asList(topic));
    }

    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = c.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                }
            }
        } finally {
            c.close();
        }
        
    }
}
