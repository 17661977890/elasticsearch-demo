package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movies {

    private Integer id;

    /**
     * FieldType.Keyword: 不会进行分词处理（不会转小写）
     */
    @Field(type = FieldType.Keyword)
    private String title;

    /**
     * 搜索建议 自动补全（前缀）的字段 和title内容一直 completion 类型
     */
    private String suggest;

    private Integer year;

    private String country;
}
