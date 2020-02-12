package exam;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping("/request")
	public String request(@RequestParam String cnt) {
		System.out.println("Send: send!");
		
		int cnti = Integer.parseInt(cnt);
		
		for(int i=0;i<cnti;i++) {
//			rabbitTemplate.convertAndSend(RabbitMqConfig.topicRequest, "foo.bar.baz", "job-"+i);
			rabbitTemplate.convertAndSend(RabbitMqConfig.topicRequest, "foo.bar.baz", new Job("key"+i, i));
		}
		
		return "TEST";
	}
}
