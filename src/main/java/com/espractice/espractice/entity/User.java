package com.espractice.espractice.entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@ToString
public class User {
  @NonNull
  private String username;
  @NonNull
  private Short age;
  private String message;

  public User(@NonNull String username, @NonNull Short age) {
    this.username = username;
    this.age = age;
    this.message = "Ahoy, I'm " + username +". I'm " + age + " years old.";
  }

  public Map<String, String> toMap(){
    Map<String, String> map = new HashMap<>();
    map.put("username", this.username);
    map.put("age", this.age.toString());
    map.put("message", this.message);
    return map;
  }
}
