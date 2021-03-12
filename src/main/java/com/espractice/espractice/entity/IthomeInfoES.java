package com.espractice.espractice.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@ToString
@Document(indexName = "ithome_spring_data")
public class IthomeInfoES {
  @Id
  private  Long id;
  private String title;
  private String description;
  private LocalDateTime time;

  public IthomeInfoES(String title, String description) {
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
