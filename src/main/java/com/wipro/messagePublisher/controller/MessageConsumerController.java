package com.wipro.messagePublisher.controller;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.prowidesoftware.swift.model.field.Field95P;
import com.prowidesoftware.swift.model.field.Field97A;
import com.prowidesoftware.swift.model.mt.mt5xx.MT540;
import com.wipro.messagePublisher.pojo.ExchangeMessage;
import com.wipro.messagePublisher.pojo.MessageData;

@Component
public class MessageConsumerController implements MessageListener {

	@Autowired
	MessageConverter messageConverter;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	

	@Override
	public void onMessage(Message message) {
		try {
			String fromMessage = (String) messageConverter.fromMessage(message);

			JAXBContext jaxbContext = JAXBContext.newInstance(MessageData.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(fromMessage);
			MessageData messageData = (MessageData) unmarshaller.unmarshal(reader);

			ExchangeMessage exMessage = new ExchangeMessage();
			exMessage.setDeliveringAgent(messageData.getDeliveringAgent());
			exMessage.setPlaceOfSafekeepingAccount(messageData.getPlaceOfSafekeepingAccount());
			exMessage.setPlaceOfSettlement(messageData.getPlaceOfSettlement());
			exMessage.setSafekeepingAccount(messageData.getSafekeepingAccount());
			exMessage.setSeller(messageData.getSeller());
			
			System.out.println("MESSAGE RECIVED CONSUMER: " + fromMessage);
			System.out.println("OBJ CONSUMER:   " + messageData);
			
			
			pushSwiftMessage(parseSwift(exMessage));
		} catch (MessageConversionException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private ResponseEntity<Boolean> pushSwiftMessage(String swiftMessage) {
		System.out.println("MSG DATA: SWIFT   : " + swiftMessage);
		jmsTemplate.setDefaultDestinationName("QUEUE_2");
		jmsTemplate.send(new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage(swiftMessage);
				return objectMessage;
			}
		});

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	private String parseSwift(ExchangeMessage exchangeMessage) {
		MT540 mt540 = new MT540();

		Field95P p95D = new Field95P();
		p95D.setQualifier("DEAG");
		p95D.setBIC(exchangeMessage.getDeliveringAgent());
		mt540.addField(p95D);

		Field95P p95CD = new Field95P();
		p95CD.setQualifier("SELL");
		p95CD.setBIC(exchangeMessage.getSeller());
		mt540.addField(p95CD);

		Field97A a97 = new Field97A();
		a97.setQualifier("SAFE");
		a97.setAccount(exchangeMessage.getSafekeepingAccount());
		mt540.addField(a97);

		Field95P p95 = new Field95P("P/Q");
		p95.setQualifier("PSET");
		p95.setBIC(exchangeMessage.getPlaceOfSettlement());
		mt540.addField(p95);

		Field95P p95S = new Field95P();
		p95S.setQualifier("SAFE//CUST");
		p95S.setBIC(exchangeMessage.getPlaceOfSafekeepingAccount());
		mt540.addField(p95S);

		return mt540.message().toString();
	}
}




