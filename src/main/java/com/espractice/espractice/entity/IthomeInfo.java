package com.espractice.espractice.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@ToString
public class IthomeInfo {
  private String title;
  private String description;
  private LocalDateTime time;

  public IthomeInfo(String title, String description) {
    this.title = title;
    this.description = description;
    this.time =  LocalDateTime.now();
  }

  public Map<String, String> toMap(){
    Map<String, String> map = new HashMap<>();
    map.put("title", title);
    map.put("description", description);
    map.put("time", time.toString());
    return map;
  }
}
