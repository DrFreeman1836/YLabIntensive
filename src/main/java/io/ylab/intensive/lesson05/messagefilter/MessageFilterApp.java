package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.util.MQClient;
import java.io.IOException;
import java.util.Scanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        Config.class);
    applicationContext.start();
    MQClient mq = applicationContext.getBean(MQClient.class);
    QueueManager manager = applicationContext.getBean(QueueManager.class);

    Runnable task = () -> {
      try {
        manager.readInput();
      } catch (Exception e) {
        e.printStackTrace();
      }
    };
    Thread thread = new Thread(task);
    thread.start();

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        String mes = scanner.nextLine();
        mq.getChannelIn().basicPublish("exchangeIn", "key", null, mes.getBytes());

        Thread.sleep(500);
        GetResponse response = mq.getChannelOut().basicGet("output", true);
        if (response != null) {
          System.out.println("res = " + new String(response.getBody()));
        }

      }
    }

  }
}
