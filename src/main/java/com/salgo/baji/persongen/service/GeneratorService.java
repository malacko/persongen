package com.salgo.baji.persongen.service;

import com.salgo.baji.persongen.pojo.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class GeneratorService {
  private static final Logger logger = LogManager.getLogger(GeneratorService.class);
  @Autowired ResourceProvider resourceProvider;

  @Value("${request.uri}")
  private String url;

  @Value("${app.username}")
  private String username;

  @Value("${app.password}")
  private String password;

  private ExecutorService executorService;

  private List<Future<Long>> results;

  @PostConstruct
  private void init() {
    executorService = Executors.newFixedThreadPool(20);
    results = new ArrayList<>();
  }

  public void start() {
    List<Person> people = resourceProvider.createPeople();
    people.forEach(this::request);
  }

  private void request(Person person) {
    Callable<Long> messagePump = new MessagePump(person, url, username, password);
    Future<Long> result = executorService.submit(messagePump);
    results.add(result);
    logger.debug("added person to queue: {} {}", person.getFirstName(), person.getLastName());
  }

  public void report() {
    while (!results.isEmpty()) {
      List<Future<Long>> finished =
          results.stream().filter(Future::isDone).collect(Collectors.toList());
      results.removeAll(finished);

      finished.forEach(
          f -> {
            try {
              logger.info("finished request with id {} ", f.get());
            } catch (Exception e) {
              logger.error("a request is failed");
            }
          });
    }
  }
}
