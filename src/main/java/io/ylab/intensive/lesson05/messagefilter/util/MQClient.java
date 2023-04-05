package io.ylab.intensive.lesson05.messagefilter.util;

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
  private final Channel channelIn;
  private final Channel channelOut;
  private static final String EXCHANGE_IN = "exchangeIn";
  private static final String EXCHANGE_OUT = "exchangeOut";
  private static final String QUEUE_INPUT = "input";
  private static final String QUEUE_OUTPUT = "output";
  private static final String KEY = "key";

  @Autowired
  public MQClient(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
    this.connectionFactory = connectionFactory;
    this.connection = connectionFactory.newConnection();
    this.channelIn = connection.createChannel();
    this.channelOut = connection.createChannel();
  }

  @PostConstruct
  private void initQueue() throws IOException {
    channelIn.exchangeDeclare(EXCHANGE_IN, BuiltinExchangeType.DIRECT);
    channelIn.queueDeclare(QUEUE_INPUT, true, false, false, null);
    channelIn.queueBind(QUEUE_INPUT, EXCHANGE_IN, KEY);

    channelIn.exchangeDeclare(EXCHANGE_OUT, BuiltinExchangeType.DIRECT);
    channelIn.queueDeclare(QUEUE_OUTPUT, true, false, false, null);
    channelIn.queueBind(QUEUE_OUTPUT, EXCHANGE_OUT, KEY);
  }

  @PreDestroy
  private void close() throws IOException, TimeoutException {
    connection.close();
    channelIn.close();
  }

  public Channel getChannelIn() {
    return this.channelIn;
  }

  public Channel getChannelOut() {
    return channelOut;
  }
}
