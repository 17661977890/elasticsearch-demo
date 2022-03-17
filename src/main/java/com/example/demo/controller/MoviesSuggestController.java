package com.example.demo.controller;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/movies-suggest")
@RestController
public class MoviesSuggestController {

    /**
     * ElasticsearchRestTemplate(对应的就是restHighLevelClient)
     */
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 4.0版本的 spring data elasticsearch 已经弃用
     */
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 老版本 使用 elasticsearchTemplate
     * {
     *   "_source": false,
     *   "suggest": {
     *     "title_suggest": {
     *       "prefix": "lo",
     *       "completion":{
     *         "field":"suggest",
     *         "skip_duplicates": true,
     *         "size":2
     *       }
     *     }
     *   }
     * }
     *
     * "suggest" : {
     *     "title_suggest" : [
     *       {
     *         "text" : "lo",
     *         "offset" : 0,
     *         "length" : 2,
     *         "options" : [
     *           {
     *             "text" : "love",
     *             "_index" : "movies",
     *             "_type" : "_doc",
     *             "_id" : "14",
     *             "_score" : 1.0
     *           }
     *         ]
     *       }
     *     ]
     *   }
     * @param prefix
     * @return
     */
    @GetMapping("/suggest")
    public List<String> suggest(String prefix){
        List<String> list = new ArrayList<>();
//
//        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder("suggest")
//                .prefix(prefix)
//                .skipDuplicates(true)
//                .size(10);
//        SuggestBuilder suggestBuilder = new SuggestBuilder()
//                .addSuggestion("title_suggest",completionSuggestionBuilder);
//
//        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch("movies")
//                .suggest(suggestBuilder)
//                .setFetchSource(false);
//
//        SearchResponse searchResponse = searchRequestBuilder.get();
//
//        Suggest suggest = searchResponse.getSuggest();
//        Suggest.Suggestion suggestion = suggest.getSuggestion("title_suggest");
//
//        List<CompletionSuggestion.Entry> entries = suggestion.getEntries();
//        entries.forEach(entry -> {
//            List<CompletionSuggestion.Entry.Option> options = entry.getOptions();
//            options.forEach(op->{
//                list.add(op.getText().toString());
//            });
//        });
        return list;

    }

    /**
     * 使用 restHighLevelClient 完成 自动补全功能
     * {
     *   "_source": false,
     *   "suggest": {
     *     "title_prefix_suggest": {
     *       "prefix": "lo",
     *       "completion":{
     *         "field":"suggest",
     *         "skip_duplicates": true,
     *         "size":2
     *       }
     *     }
     *   }
     * }
     * @param prefix
     * @return
     */
    @GetMapping("/suggest2")
    public List<String> suggest2(String prefix){
        //指定在哪个字段搜索
        String suggestField = "suggest";
//        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        // 构建自动补全的配置
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder(suggestField)
                .prefix(prefix)
                .skipDuplicates(true)
                .size(10);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("my_suggest_document", completionSuggestionBuilder );

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);

        // 构建request
        SearchRequest searchRequest = new SearchRequest("movies");
        searchRequest.source(searchSourceBuilder);

        // 请求获取响应
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert response != null;
        Suggest suggest = response.getSuggest();

        // 获取自动补全的结果
        List<String> keywords = null;
        if (suggest != null) {
            keywords = new ArrayList<>();
            List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries =
                    suggest.getSuggestion("my_suggest_document").getEntries();
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry: entries) {
                for (Suggest.Suggestion.Entry.Option option: entry.getOptions()) {
                    String keyword = option.getText().string();
                    if (!StringUtils.isEmpty(keyword)) {
                        if (keywords.contains(keyword)) {
                            continue;
                        }
                        keywords.add(keyword);
                        if (keywords.size() >= 9) {
                            break;
                        }
                    }
                }
            }
        }
        return keywords;

    }

}
