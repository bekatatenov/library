package com.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class RequestMessage {

  private Integer id;
  private String value;

}
