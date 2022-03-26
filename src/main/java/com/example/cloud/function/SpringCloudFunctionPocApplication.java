package com.example.cloud.function;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.example.cloud.funciton.model.Hello;

//@SpringBootConfiguration
@SpringBootApplication
public class SpringCloudFunctionPocApplication
//implements ApplicationContextInitializer<GenericApplicationContext>  
{

	//Static and instance members are instantiayed only once per instance of a function.
	private int random = ThreadLocalRandom.current().nextInt();

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudFunctionPocApplication.class, args);
	}

	@Bean
	public Function<String, String> hello() {
		System.out.println(
				"=============== INSIDE hello ================>" + environment.getProperty("application.name"));
		return (str) -> "Welcome to Spring Cloud Function";
	}

	// Return Message Type to work with --handler SpringBootApiGatewayRequestHandler behind
	// AWS Api Gateway. Marshalling and Unmarshalling will be done automatically.
	@Bean
	public Function<Message<String>, Message<Hello>> welcome() {
		System.out.println("=============== INSIDE welcome ================");
		return (str) -> MessageBuilder.withPayload(new Hello(random, "Welcome to Spring Cloud New Welcome Function"))
				.build();
	}

	/*
	 * @Bean public MessageRoutingCallback customRouter() { return new
	 * MessageRoutingCallback() {
	 * 
	 * @Override public FunctionRoutingResult routingResult(Message<?> message) {
	 * return new FunctionRoutingResult((String)
	 * message.getHeaders().get("func_name")); } }; }
	 */

	// ApplicationContextInitializer is faster than @Bean function declarations.
	/*
	 * @Override public void initialize(GenericApplicationContext context) {
	 * System.out.println("=============== INSIDE initialize ================");
	 * context.registerBean("hello", FunctionRegistration.class, () -> new
	 * FunctionRegistration<Function<String, String>>(hello())
	 * .type(FunctionType.from(String.class).to(String.class).getType())); }
	 */

}
