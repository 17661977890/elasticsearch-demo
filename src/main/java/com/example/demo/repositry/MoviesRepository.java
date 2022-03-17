package com.example.demo.repositry;

import com.example.demo.entity.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 索引的基本查询方法定义（查询方法名的规则定义）---JPA
 * https://docs.spring.io/spring-data/elasticsearch/docs/4.0.0.RELEASE/reference/html/#elasticsearch.query-methods
 */
public interface MoviesRepository extends ElasticsearchRepository<Movies,Integer> {


    Page<Movies> findByTitle(String title, Pageable pageable);

    Page<Movies> findByTitleAndYear(String title,Integer year, Pageable pageable);

    Page<Movies> findByTitleIn(List<String> title, Pageable pageable);

    Page<Movies> findByTitleAndYearBetween(String title, Integer begin,Integer end,Pageable pageable);

    /**
     * 自定义查询语法 ?0 ?1 占位符
     * @return
     */
    @Query("{\n" +
            "        \"bool\": {\n" +
            "            \"must\": [\n" +
            "                {\n" +
            "                    \"query_string\": {\n" +
            "                        \"query\": \"?0\",\n" +
            "                        \"default_operator\": \"or\"\n" +
            "                    }\n" +
            "                },\n" +
            "                {\n" +
            "                    \"match\": {\n" +
            "                        \"year\": \"?1\"\n" +
            "                    }\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }")
    Page<Movies> getCustomEsSql(String title,Integer year,Pageable pageable);




}
