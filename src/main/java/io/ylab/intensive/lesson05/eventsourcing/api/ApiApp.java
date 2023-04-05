package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import java.util.Scanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApiImpl personApi = applicationContext.getBean(PersonApiImpl.class);
    // пишем взаимодействие с PersonApi

    Scanner scanner = new Scanner(System.in);
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
