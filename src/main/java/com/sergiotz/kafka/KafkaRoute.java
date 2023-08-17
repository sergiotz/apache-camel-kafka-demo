package com.sergiotz.kafka;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("kafka:{{consumer.topic}}?groupId={{consumer.group}}")
			.routeId("consumer route")
			.log("consumer >>> ${body}");


		from("timer://foo?period={{period}}")
			.routeId("producer route")
			.setBody(simple("This is first producer: ${id}"))
			.to("kafka:{{producer.topic}}?partitionKey={{partitionValue}}&key=${id}")
			.log("producer >>> ${body}");
	}
}
