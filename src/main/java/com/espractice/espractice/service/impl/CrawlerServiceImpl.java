package com.espractice.espractice.service.impl;

import com.espractice.espractice.entity.IthomeInfo;
import com.espractice.espractice.service.CrawlerService;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrawlerServiceImpl implements CrawlerService {

  @Override
  public String getHtml(String url) {
    try {
      Response response = Jsoup.connect(url).execute();
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println(response.body());
      System.out.println("------------------------------------------------------------------------------------");
      Document doc = Jsoup.parse(response.body());
      Elements elements = doc.select("a.qa-list__title-link");
      List<String> list = elements.stream().map(e -> e.text()).collect(Collectors.toList());
      StringBuilder sb = new StringBuilder();
      list.forEach(s->{
        sb.append(s);
        sb.append("<br>");
      });
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      log.error("fail to crawler");
    }
    return null;
  }
  @Override
  public List<IthomeInfo> getItHomeTechInfo(Integer page) {
    try {
      Response response = Jsoup.connect("https://ithelp.ithome.com.tw/articles?tab=tech&page="+page).execute();
      Document doc = Jsoup.parse(response.body());
//      Elements elements = doc.select("a.qa-list__title-link");
      Elements elements = doc.select("div.qa-list");
      List<IthomeInfo> list = elements.stream().map(e -> new IthomeInfo(e.select("h3.qa-list__title").text(),e.select("p.qa-list__desc").text())).collect(Collectors.toList());
      return list;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("fail to crawler");
    }
    return Collections.EMPTY_LIST;
  }
}
