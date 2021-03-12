package com.espractice.espractice.controller;

import com.espractice.espractice.entity.IthomeInfo;
import com.espractice.espractice.service.CrawlerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {

  @Autowired
  CrawlerService crawlerService;

  @GetMapping("/test")
  public String start(){

//    return crawlerService.getHtml("https://tw.chowsangsang.com/tc/product/Gold-Bycategory-Charme?page=1");
    return crawlerService.getHtml("https://ithelp.ithome.com.tw/articles?tab=tech");
  }

  @GetMapping("/ithome")
  public String ithome(@RequestParam(required = false,defaultValue = "1") Integer page){
    List<IthomeInfo> list =crawlerService.getItHomeTechInfo(page);
    StringBuilder sb = new StringBuilder();
    list.forEach(s->{
      sb.append("Title: "+s.getTitle() + "\tDesc: " + s.getDescription() + "\t CreateTime: " + s.getTime().toString());
      sb.append("<br>");
      System.out.println(s.toString());
    });
    return sb.toString();
  }
}
