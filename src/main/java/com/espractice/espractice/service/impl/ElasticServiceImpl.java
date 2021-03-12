package com.espractice.espractice.service.impl;

import com.espractice.espractice.elasticRepository.IthomeInfoESRepository;
import com.espractice.espractice.entity.IthomeInfo;
import com.espractice.espractice.entity.IthomeInfoES;
import com.espractice.espractice.service.CrawlerService;
import com.espractice.espractice.service.ElasticService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService {

  @Autowired
  CrawlerService crawlerService;
  @Autowired
  RestHighLevelClient client;
  @Autowired
  IthomeInfoESRepository ithomeInfoESRepository;

  @Override
  public boolean insertItHomeInfo(int page) {
    List<IthomeInfo> list = crawlerService.getItHomeTechInfo(page);

    BulkRequest request = new BulkRequest();
    list.forEach(obj -> {
      request.add(new IndexRequest("ithome_test").source(obj.toMap()));
    });
    try {
      BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
      System.out.println(response.toString());
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("create ithome info to Elasticsearch failed. ");
    }
    return false;
  }

  @Override
  public boolean insertItHomeInfoBySpringData(Integer page) {
    List<IthomeInfo> list = crawlerService.getItHomeTechInfo(page);
    List<IthomeInfoES> parsedList = list.stream()
        .map(ithomeInfo -> new IthomeInfoES(ithomeInfo.getTitle(), ithomeInfo.getDescription()))
        .collect(Collectors.toList());
    ithomeInfoESRepository.saveAll(parsedList);
    return true;
  }
}
