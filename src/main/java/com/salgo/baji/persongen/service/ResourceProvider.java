package com.salgo.baji.persongen.service;

import com.salgo.baji.persongen.enums.FileType;
import com.salgo.baji.persongen.enums.Sex;
import com.salgo.baji.persongen.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceProvider {

  private static final Logger logger = LoggerFactory.getLogger(ResourceProvider.class);

  private final LocalDate date = LocalDate.of(1990, 1, 1);
  private Map<FileType, String> fileMap = new HashMap<>();

  private final String[] lastNames = {
    "Asta", "Barath", "Csikos", "Des", "Etelkozi", "Farkas", "Huber", "Molnar", "Osvath", "Porkolab"
  };

  private final String[] firstNames = {
    "Aladar", "Bela", "Cecilia", "Erika", "Krisztian", "Laszlo", "Viola", "Zita", "Zoltan", "Zsolt"
  };

  private final Sex[] sexes = {
    Sex.MALE,
    Sex.MALE,
    Sex.FEMALE,
    Sex.FEMALE,
    Sex.MALE,
    Sex.MALE,
    Sex.FEMALE,
    Sex.FEMALE,
    Sex.MALE,
    Sex.MALE
  };

  public ResourceProvider() {}

  @PostConstruct
  private void init() {
    fileMap = getFileMap();
  }

  public List<Person> createPeople() {
    List<Person> people = new ArrayList<>();
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        Person person = new Person();
        person.setFirstName(getFirstName(i));
        person.setLastName(getLastName(j));
        person.setEmail(getEmail(i, j));
        person.setSex(getSex(i));
        person.setBirthDate(getBirthDate(i, j));
        person.setPicturePath(fileMap.get(FileType.JPG));
        person.setMp3Path(fileMap.get(FileType.MP3));
        person.setWordPath(fileMap.get(FileType.WORD));
        people.add(person);
      }
    }
    return people;
  }

  private Map<FileType, String> getFileMap() {
    String word = "static/laca.doc";
    String jpg = "static/baji_laszlo.jpg";
    String mp3 = "static/almodtam_egy_vilagot.mp3";
    try {
      File wordFile = getFileFromResource(word);
      File mp3File = getFileFromResource(mp3);
      File jpgFile = getFileFromResource(jpg);
      fileMap.put(FileType.WORD, wordFile.getAbsolutePath());
      fileMap.put(FileType.JPG, jpgFile.getAbsolutePath());
      fileMap.put(FileType.MP3, mp3File.getAbsolutePath());
    } catch (URISyntaxException ex) {
      logger.error(ex.getMessage());
    }

    return fileMap;
  }

  private File getFileFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return new File(resource.toURI());
    }
  }

  private String getLastName(int index) {
    return lastNames[index];
  }

  private String getFirstName(int index) {
    return firstNames[index];
  }

  private Sex getSex(int index) {
    return sexes[index];
  }

  private String getEmail(int index1, int index2) {
    return firstNames[index1] + "." + lastNames[index2] + "@gmail.com";
  }

  private LocalDate getBirthDate(int index1, int index2) {
    return date.plusYears(index1).plusMonths(index2).plusDays(index1 + index2);
  }
}
