package com.example.demo.controller;

import com.example.demo.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/search")
@RestController
public class SearchController {


    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/addDataToEs/{keyWord}",method = RequestMethod.GET)
    public Boolean addDataToEs(@PathVariable("keyWord") String keyWord){
        Boolean result = searchService.addDataToEs(keyWord);
        return result;
    }



    @RequestMapping(value = "/searchHighLight/{keyWord}/{page}/{pageSize}",method = RequestMethod.GET)
    public List<Map<String,Object>> searchHighLight(@PathVariable("keyWord") String keyWord, @PathVariable("page")int page, @PathVariable("pageSize")int pageSize) throws IOException {
        List<Map<String,Object>> result = searchService.searchHighLight(keyWord,page,pageSize);
        return result;
    }


}
