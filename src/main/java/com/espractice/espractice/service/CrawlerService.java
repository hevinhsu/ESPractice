package com.espractice.espractice.service;

import com.espractice.espractice.entity.IthomeInfo;
import java.util.List;

public interface CrawlerService {
  String getHtml(String url);

  List<IthomeInfo> getItHomeTechInfo(Integer page);
}
