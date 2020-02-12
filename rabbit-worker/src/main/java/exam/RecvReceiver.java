package exam;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecvReceiver {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private AtomicInteger ai = new AtomicInteger(1);

	public void receiveMessage(Job message) throws InterruptedException {
		System.out.println("Recv" + ai.getAndIncrement() + " : Received <" + message + ">");

		Thread.sleep(2000);
		
		rabbitTemplate.convertAndSend(RabbitMqConfig.topicResult, "foo.bar.baz", "result-"+message);
	}

}