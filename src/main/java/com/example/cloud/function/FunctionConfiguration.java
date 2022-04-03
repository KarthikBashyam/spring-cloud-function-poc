package com.example.cloud.function;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.example.cloud.funciton.model.Hello;

//@Component
public class FunctionConfiguration {
	
	// Static and instance members are instantiated only once per instance of a
	// Function.
	private int random = ThreadLocalRandom.current().nextInt();
	
	@Autowired
	private Environment environment;

	@Bean
	public Function<Message<String>, Message<String>> hello() {
		System.out.println(
				"=============== INSIDE hello ================>" + environment.getProperty("application.name"));
		return (str) -> MessageBuilder.withPayload("Welcome to Spring Cloud Function").build();
	}

	/** 
	 	Return Message Type to work with --handler SpringBootApiGatewayRequestHandler
	 	behind
	 	AWS Api Gateway. Marshalling and Unmarshalling will be done automatically.
	 **/
	@Bean
	public Function<Message<String>, Message<Hello>> welcome() {
		System.out.println("=============== INSIDE welcome ================");
		return (str) -> MessageBuilder.withPayload(new Hello(random, "Welcome to Spring Cloud New Welcome Function"))
				.build();
	}
	
}
