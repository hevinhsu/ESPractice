package com.espractice.espractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/")
  public ResponseEntity<String> helloWorld(){
    log.info(String.format("hello world"));
    return ResponseEntity.status(HttpStatus.OK).body("hello world!");
  }
}
