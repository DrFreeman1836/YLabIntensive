package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.messagefilter.util.DbClient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFilterImpl implements MessageFilter {

  private final DbClient dbClient;

  @Autowired
  public MessageFilterImpl(DbClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public String handlerInputMessage(String input) {
    String output = input;
    List<String> inputArray = List.of(input.split(" "));
    for (String word : inputArray) {
      String expression = word.replaceAll("[^А-Яа-яA-Za-z]", "");
      if (isCensorship(expression.toLowerCase())) {
        output = modifyWord(output, expression);
      }
    }
    return output;
  }

  private String modifyWord(String input, String expression) {
    StringBuilder res  = new StringBuilder();
    for (int i = 0; i < expression.length(); i++) {
      if (i == 0 || i == expression.length() - 1) {
        res.append(expression.charAt(i));
      } else {
        res.append('*');
      }
    }
    return input.replaceAll(expression, res.toString());
  }

  private static final String QUERY_CHECK = "select word from censorship where word = ?";
  private boolean isCensorship(String word) {
    try (PreparedStatement preparedStatement = dbClient.getConnection().prepareStatement(QUERY_CHECK)) {
      preparedStatement.setString(1, word);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        return true;
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

}
