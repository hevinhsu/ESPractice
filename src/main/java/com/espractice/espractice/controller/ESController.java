package com.espractice.espractice.controller;

import com.espractice.espractice.entity.User;
import com.espractice.espractice.service.CrawlerService;
import com.espractice.espractice.service.ElasticService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/elastic")
public class ESController {

  @Autowired
  RestHighLevelClient client;
  @Autowired
  CrawlerService crawlerService;
  @Autowired
  ElasticService elasticService;

  String connectionErrorMsg = "exception throwing when connecting to Elastic";
  private String index = "user_test";

  @GetMapping("/search")
  public ResponseEntity search(@RequestParam @NonNull String label, @RequestParam @NonNull String name){
    log.info(String.format("user input label:%s, search target: %s .",label, name));
    // create search queries
    QueryBuilder queryBuilder = QueryBuilders.matchQuery(label, name);
    // add search queries into search source builder
    SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(queryBuilder);
    // create search request and add source into request
    SearchRequest req = new SearchRequest();
    req.source(builder);
    SearchResponse response = null;
    // using high level client to send request , it will return response
    try {
      response = client.search(req, RequestOptions.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
      log.error(connectionErrorMsg);
    }
    // parsing result. this structure is boxing by json string. so parsing on your own.
    SearchHit[] searchHits = response.getHits().getHits();  // what you see on kibana
    Collection result = Arrays.asList(searchHits).stream().map(SearchHit::getSourceAsString).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/createUser")
  public ResponseEntity create(@RequestParam @NonNull String username, @RequestParam @NonNull short age){
    log.info(String.format("create user. user input name = %s", username));
    User user = new User(username, age);
    log.info(String.format("create user = %s",user.toString()));
    IndexRequest request = new IndexRequest(index).source(user.toMap());
    try {
      IndexResponse response = client.index(request, RequestOptions.DEFAULT);
      System.out.println(response.toString());
    } catch (IOException e) {
      e.printStackTrace();
      log.error(connectionErrorMsg);
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/ithome")
  public ResponseEntity ithome(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
    log.info(String.format("climb iT幫幫忙 技術文章. page = %s and put data into Elastic search",page));
    boolean result = elasticService.insertItHomeInfo(page);
    return result ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/springData")
  public ResponseEntity springData(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
    log.info(String.format("climb iT幫幫忙 最新技術文章. page = %s and put data into Elastic search",page));
    boolean result = elasticService.insertItHomeInfoBySpringData(page);
    return result ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
