package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DbApp {

  private static final String QUEUE_NAME = "queue";

  private static final ObjectMapper mapper = new ObjectMapper();

  private static DataSource dataSource;

  public static void main(String[] args) throws Exception {
    dataSource = initDb();
    ConnectionFactory connectionFactory = initMQ();

    try (Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel()) {

      while (true) {
        Thread.sleep(2000);
        GetResponse message = channel.basicGet(QUEUE_NAME, true);
        if (message == null) {
          continue;
        }
        String mesText = new String(message.getBody());
        handlerMessage(mesText);
      }

    }

  }

  private static void handlerMessage(String text) {
    try {
      Person person = mapper.readValue(text, Person.class);
      savePerson(person);
    } catch (JsonProcessingException e) {
      deletePerson(Long.valueOf(text));
    }
  }

  private static final String INSERT_ON_CONFLICT =
      "insert into person (person_id, first_name, last_name, middle_name) values(?, ?, ?, ?)"
          + "on conflict (person_id) do update set first_name ="
          + " excluded.first_name, last_name = excluded.last_name, middle_name = excluded.middle_name";
  private static void savePerson(Person person) {
    try (java.sql.Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ON_CONFLICT)) {
      preparedStatement.setLong(1, person.getId());
      preparedStatement.setString(2, person.getName());
      preparedStatement.setString(3, person.getLastName());
      preparedStatement.setString(4, person.getMiddleName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static final String DELETE = "delete from person where person_id = ?";
  private static void deletePerson(Long idPerson) {
    try (java.sql.Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
      preparedStatement.setLong(1, idPerson);
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }

  private static DataSource initDb() throws SQLException {
    String ddl = ""
        + "drop table if exists person;"
        + "create table if not exists person (\n"
        + "person_id bigint primary key,\n"
        + "first_name varchar,\n"
        + "last_name varchar,\n"
        + "middle_name varchar\n"
        + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
