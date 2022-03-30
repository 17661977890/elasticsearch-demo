package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * es 搜索商品信息实体
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodInfo {

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品标题 ---- 条件搜素
     */
    private String title;

    /**
     * 商品所属店铺名称
     */
    private String shop;

    /**
     * 商品标题额外字段 --- 自动补全、拼音分词
     */
    private Object[] suggestTags;

    /**
     * 商品详情url
     */
    private String goodsInfoUrl;
}
