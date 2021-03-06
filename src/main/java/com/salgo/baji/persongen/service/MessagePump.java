package com.salgo.baji.persongen.service;

import com.salgo.baji.persongen.exception.RestTemplateErrorHandler;
import com.salgo.baji.persongen.pojo.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public class MessagePump implements Callable<Long> {
  private static final Logger logger = LogManager.getLogger(MessagePump.class);

  private String url;

  private String username;

  private String password;

  private RestTemplate restTemplate;

  private Person person;

  public MessagePump(Person person, String url, String username, String password) {
    this.person = person;
    this.url = url;
    this.username = username;
    this.password = password;
    this.restTemplate = new RestTemplate();
  }

  private Long request(Person person) {

    URI uri = null;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth(username, password);

    RequestEntity<Person> request =
        RequestEntity.post(uri)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .headers(headers)
            .body(person);
    ResponseEntity<Long> response = null;
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());
    response = restTemplate.exchange(url, HttpMethod.POST, request, Long.class);

    Long result = response.getBody();
    logger.debug("person created with id : {}", result);
    return result;
  }

  @Override
  public Long call() throws Exception {
    return request(person);
  }
}
