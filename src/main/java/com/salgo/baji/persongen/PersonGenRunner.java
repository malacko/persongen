package com.salgo.baji.persongen;

import com.salgo.baji.persongen.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PersonGenRunner implements CommandLineRunner {
  @Autowired GeneratorService generatorService;

  @Autowired private ConfigurableApplicationContext context;

  @Override
  public void run(String... args) throws Exception {

    generatorService.start();
    generatorService.report();
    System.exit(SpringApplication.exit(context));
  }
}
