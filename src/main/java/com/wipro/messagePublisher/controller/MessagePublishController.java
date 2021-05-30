package com.wipro.messagePublisher.controller;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.messagePublisher.pojo.MessageData;

@RestController
@CrossOrigin(origins="*")
public class MessagePublishController {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	ArrayList<MessageData> msgArray=new ArrayList<>();
	
	private Boolean flag = false;

	@PostMapping("/publishToQueue")
	public Boolean publishMessage(@RequestBody MessageData messageData) {
		String message = jaxbObjectToXML(messageData);
		jmsTemplate.send(new MessageCreator() {

			@Override
			public Message createMessage(Session session) {
				ObjectMessage objectMessage = null;
				try {
					objectMessage = session.createObjectMessage(message);
					msgArray.add(messageData);
					flag = true;
				} catch (JMSException e) {
					e.printStackTrace();
				}
				return objectMessage;
			}
		});
		return flag;
	}
	
	private static String jaxbObjectToXML(MessageData messageData) 
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(MessageData.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(messageData, sw);
             
            String xmlContent = sw.toString();
            System.out.println("PUBLLISHER XML CONTEXT  1:  +"+ xmlContent );
            return xmlContent;
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
		return null;
    }
	
	@GetMapping("/getMessage")
	public ArrayList<MessageData> getMessages(){
	    
	    return msgArray;

	}
}
