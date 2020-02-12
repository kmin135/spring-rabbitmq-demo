package exam;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class SendReceiver {
	
	private AtomicInteger ai = new AtomicInteger(1);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">" + " / cnt : " + ai.getAndIncrement());
	}

}