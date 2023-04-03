package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import java.util.Scanner;
import javax.sql.DataSource;

public class ApiApp {

  private static final String EXCHANGE_NAME = "exchange";
  private static final String QUEUE_NAME = "queue";

  private static final String KEY = "key";

  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    DataSource dataSource = DbUtil.buildDataSource();

    try (Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        Scanner scanner = new Scanner(System.in)) {

      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, KEY);
      PersonApi personApi = new PersonApiImpl(dataSource, channel);

      while (true) {
        String mes = scanner.nextLine();
        String[] mesSplit = mes.split(" ");

        if (mesSplit[0].equals("save") && mesSplit.length == 5) {// добавление
          personApi.savePerson(Long.valueOf(mesSplit[1]), mesSplit[2], mesSplit[3], mesSplit[4]);
        } else if (mesSplit[0].equals("delete") && mesSplit.length == 2) {// удаление
          personApi.deletePerson(Long.valueOf(mesSplit[1]));
        } else if (mesSplit[0].equals("id") && mesSplit.length == 2) {// поиск по id
          Person person = personApi.findPerson(Long.valueOf(mesSplit[1]));
          System.out.println(person);
        } else if (mesSplit[0].equals("find") && mesSplit.length == 1) {// вывод всех
          personApi.findAll().forEach(System.out::println);
        }

        if (mes.equals("break")) {
          break;
        }
      }
    }

  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
