package exam;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	static final String queueRequest = "request-queue";
	static final String topicRequest = "request-topic";
	
	static final String queueResult = "result-queue";
	static final String topicResult = "result-topic";

	@Bean
	Queue queue() {
		return new Queue(queueRequest, false);
	}
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicRequest);
	}
	@Bean
	Binding binding(@Qualifier("queue") Queue queue, @Qualifier("exchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}
	
//	@Bean
//	Queue queue2() {
//		return new Queue(queueResult, false);
//	}
//	@Bean
//	TopicExchange exchange2() {
//		return new TopicExchange(topicResult);
//	}
//	@Bean
//	Binding binding2(@Qualifier("queue2") Queue queue, @Qualifier("exchange2") TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
//	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueRequest);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RecvReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

}