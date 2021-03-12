package com.espractice.espractice.elasticRepository;

import com.espractice.espractice.entity.IthomeInfoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IthomeInfoESRepository extends ElasticsearchRepository<IthomeInfoES, String> {

}
