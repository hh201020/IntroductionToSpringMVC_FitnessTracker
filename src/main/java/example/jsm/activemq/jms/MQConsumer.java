package example.jsm.activemq.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MQConsumer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
		Session session = null;
		Connection connection = null;
		InitialContext context = null;

		try {
			context = new InitialContext();

			ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
			connection = cf.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) context.lookup("queueForDataToBeSent");
			MessageConsumer messageConsumer = session.createConsumer(destination);

			// messageConsumer.setMessageListener(new MQListener("Consumer"));
			// new Scanner(System.in).nextLine(); // Just to keep running
			while (true) {
				TextMessage message = (TextMessage) messageConsumer.receive();
				System.out.println(" queue " + message.getText());
			}

		} finally {
			session.close();
			connection.close();
			context.close();
		}
	}
}