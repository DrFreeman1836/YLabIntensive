package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.util.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.api.util.MQClient;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonApiImpl implements PersonApi {

  private final DbClient dbClient;
  private final MQClient mqClient;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final String EXCHANGE_NAME = "exchange";
  private static final String KEY = "key";

  @Autowired
  public PersonApiImpl(DbClient dbClient, MQClient mqClient) {
    this.dbClient = dbClient;
    this.mqClient = mqClient;
  }


  @Override
  public void deletePerson(Long personId) {
    String mes = String.valueOf(personId);
    try {
      mqClient.getChannel().basicPublish(EXCHANGE_NAME, KEY, null, mes.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    Person person = new Person(personId, firstName, lastName, middleName);
    try {
      String mes = mapper.writeValueAsString(person);
      mqClient.getChannel().basicPublish(EXCHANGE_NAME, KEY, null, mes.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static final String FIND_BY_ID = "select * from person where person_id = ?";
  @Override
  public Person findPerson(Long personId) {
    try (PreparedStatement preparedStatement = dbClient.getConnection().prepareStatement(FIND_BY_ID)) {
      preparedStatement.setLong(1, personId);
      ResultSet rs = preparedStatement.executeQuery();

      Person person = null;
      if (rs.next()) {
        person = new Person(rs.getLong(1), rs.getString(2),
            rs.getString(3), rs.getString(4));
      }

      rs.close();
      return person;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static final String FIND_ALL = "select * from person";
  @Override
  public List<Person> findAll() {
    try (PreparedStatement preparedStatement = dbClient.getConnection().prepareStatement(FIND_ALL);
        ResultSet rs = preparedStatement.executeQuery()) {

      List<Person> personList = new ArrayList<>();
      while (rs.next()) {
        personList.add(new Person(rs.getLong(1), rs.getString(2),
            rs.getString(3), rs.getString(4)));
      }

      return personList;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
