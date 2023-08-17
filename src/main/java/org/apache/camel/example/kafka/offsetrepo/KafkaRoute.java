package org.apache.camel.example.kafka.offsetrepo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("kafka:{{consumer.topic}}?groupId={{consumer.group}}")
			.log("consumer >>> ${body}");
		
		
		from("timer://foo?period={{period}}")
			.setBody(simple("This is first producer: ${id}"))
			.to("kafka:{{producer.topic}}?partitionKey={{partitionValue}}&key=${id}")
			.log("producer >>> ${body}")
			.process(new KafkaProcessor());
	}
}
