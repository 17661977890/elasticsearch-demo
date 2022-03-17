package com.example.demo.controller;

import com.example.demo.entity.Movies;
import com.example.demo.repositry.MoviesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RequestMapping("/movies")
@RestController
public class MoviesController {

    @Autowired
    private MoviesRepository moviesRepository;


    /**
     * page 从0开始
     * @param title
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/getListByTitle")
    public List<Movies>  getPageData(@RequestParam String title,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Movies> moviesPage = moviesRepository.findByTitle(title,pageable);
        log.info("====={}",moviesPage);
        return moviesPage.getContent();

    }

    @GetMapping("/multiFiledQuery")
    public List<Movies>  multiFiledQuery(@RequestParam String title,
                                     @RequestParam Integer year,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Movies> moviesPage = moviesRepository.findByTitleAndYear(title,year,pageable);
        log.info("====={}",moviesPage);
        return moviesPage.getContent();

    }

    /**
     * http://localhost:8081/movies/findByTitleIn?titles=aaa&titles=bbb
     * @param titles
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findByTitleIn")
    public List<Movies>  findByTitleIn(@RequestParam String[] titles,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Movies> moviesPage = moviesRepository.findByTitleIn(Arrays.asList(titles),pageable);
        log.info("====={}",moviesPage);
        return moviesPage.getContent();

    }


    /**
     * http://localhost:8081/movies/findByTitleAndYearBetween?title=战狼&begin=1995&end=2002
     * @param title
     * @param begin
     * @param end
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findByTitleAndYearBetween")
    public List<Movies>  findByTitleAndYearBetween(@RequestParam String title,
                                       @RequestParam Integer begin,
                                       @RequestParam Integer end,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Movies> moviesPage = moviesRepository.findByTitleAndYearBetween(title,begin,end,pageable);
        log.info("====={}",moviesPage);
        return moviesPage.getContent();

    }


    @GetMapping("/getCustomEsSql")
    public List<Movies>  getCustomEsSql(@RequestParam String title,
                                                   @RequestParam Integer year,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Movies> moviesPage = moviesRepository.getCustomEsSql(title,year,pageable);
        log.info("====={}",moviesPage);
        return moviesPage.getContent();

    }
}
