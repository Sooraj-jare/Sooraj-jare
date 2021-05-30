package com.wipro.messagePublisher;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.wipro.messagePublisher.controller.MessageConsumerController;


@SpringBootApplication
public class MessagePublisherApplication {

	@Autowired
	MessageConsumerController messageConsumerController;
	
	public static void main(String[] args) {
		SpringApplication.run(MessagePublisherApplication.class, args);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName("QUEUE_1");
		return template;
	}

	@Bean
	public MessageConverter messageConverter() {
		return new SimpleMessageConverter();
	}
	
	//---------------------
	

	@Bean
	public MessageListenerContainer getMessageListenerContainer() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setDestinationName("QUEUE_1");
		container.setMessageListener(messageConsumerController);
		return container;
	}
}
