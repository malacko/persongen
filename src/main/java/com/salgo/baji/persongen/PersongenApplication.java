package com.salgo.baji.persongen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersongenApplication {

  private static Logger LOG = LoggerFactory.getLogger(PersongenApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(PersongenApplication.class, args);
  }
}
