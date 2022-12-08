package com.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Slf4j
@Service
@Deprecated(since="02/26/2022")
public class DatabaseService {

  @Value("${database}")
  private String database;

  @Value("${spring.datasource.driver-class-name}")
  private String driver;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  private final Properties properties = new Properties();

  @PostConstruct
  public void init() {
    properties.setProperty("javax.persistence.jdbc.driver", driver);
    properties.setProperty("javax.persistence.jdbc.url", url);
    properties.setProperty("javax.persistence.jdbc.user", user);
    properties.setProperty("javax.persistence.jdbc.password", password);
  }

  public String getDatabase() {
    this.init();
    return database;
  }

  public Properties getProperties() {
    this.init();
    return properties;
  }

}
