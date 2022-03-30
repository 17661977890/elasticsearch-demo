package com.example.demo.config;

import org.apache.http.HttpHost;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
public class TransportClientConfig {

  /**
   *  配置es rest 客户端，如果是集群，就配置多个httpHost
   * @return
   */
  @Bean
  public RestHighLevelClient restHighLevelClient(){
    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("192.168.50.23", 9200, "http")
//                        new HttpHost("localhost", 9200, "http"),
//                        new HttpHost("localhost", 9201, "http")
            ));
    return client;
  }



}