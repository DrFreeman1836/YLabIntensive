package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.db.util.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.db.util.MQClient;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandlerMessage {

  private final MQClient mqClient;
  private final DbClient dbClient;
  private static final String QUEUE_NAME = "queue";
  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public HandlerMessage(MQClient mqClient, DbClient dbClient) {
    this.mqClient = mqClient;
    this.dbClient = dbClient;
  }

  public void run() throws Exception {
    Channel channel = mqClient.getChannel();
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

  private void handlerMessage(String text) {
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

  private void savePerson(Person person) {
    try (PreparedStatement preparedStatement = dbClient.getConnection().prepareStatement(INSERT_ON_CONFLICT)) {
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

  private void deletePerson(Long idPerson) {
    try (PreparedStatement preparedStatement = dbClient.getConnection().prepareStatement(DELETE)) {
      preparedStatement.setLong(1, idPerson);
      int res = preparedStatement.executeUpdate();
      if (res == 0) {
        System.out.printf("id \"%s\" не найден в базе\n", idPerson);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
