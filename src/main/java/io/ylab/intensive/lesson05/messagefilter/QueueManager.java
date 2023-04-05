package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.util.MQClient;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueManager {

  private final MQClient mqClient;
  @Qualifier("messageFilterImpl")
  private final MessageFilter messageFilter;
  private static final String EXCHANGE_OUT = "exchangeOut";
  private static final String QUEUE_INPUT = "input";
  private static final String KEY = "key";

  @Autowired
  public QueueManager(MQClient mqClient, MessageFilter messageFilter) {
    this.mqClient = mqClient;
    this.messageFilter = messageFilter;
  }


  public void readInput() throws Exception {
    while (true) {
      GetResponse response = mqClient.getChannelIn().basicGet(QUEUE_INPUT, true);
      if (response != null) {
        publishOutput(messageFilter.handlerInputMessage(new String(response.getBody())));
      }
    }
  }

  public void publishOutput(String text) {
    try {
      mqClient.getChannelIn().basicPublish(EXCHANGE_OUT, KEY, null, text.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
