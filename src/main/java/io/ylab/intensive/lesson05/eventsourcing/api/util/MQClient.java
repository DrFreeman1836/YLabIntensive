package io.ylab.intensive.lesson05.eventsourcing.api.util;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQClient {

  private final ConnectionFactory connectionFactory;
  private final Connection connection;
  private final Channel channel;
  private static final String EXCHANGE_NAME = "exchange";
  private static final String QUEUE_NAME = "queue";
  private static final String KEY = "key";

  @Autowired
  public MQClient(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
    this.connectionFactory = connectionFactory;
    this.connection = connectionFactory.newConnection();
    this.channel = connection.createChannel();
  }

  @PostConstruct
  private void initQueue() throws IOException {
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, KEY);
  }

  @PreDestroy
  private void close() throws IOException, TimeoutException {
    connection.close();
    channel.close();
  }

  public Channel getChannel() {
    return this.channel;
  }

}
