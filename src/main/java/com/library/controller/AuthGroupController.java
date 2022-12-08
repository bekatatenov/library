package com.library.controller;

import com.library.service.AuthGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AuthGroupController {

  private final AuthGroupService authGroupService;

  @Autowired
  public AuthGroupController(AuthGroupService authGroupService) {
    this.authGroupService = authGroupService;
  }

}
