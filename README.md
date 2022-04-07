# elasticsearch-demo
es搜索引擎实现 仿jd 数据爬虫到es、 高亮检索 、前缀自动补全建议搜索、 拼音分词自动补全等功能

## 环境搭建并启动：
* es 7.6.1 （官网下载）
* kibanan 7.6.1 （官网下载）
* elasticsearch-head插件
```
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
前台启动：npm run start 
后台启动：nohup npm run-script start &
open http://localhost:9100/
```
* IK中文分词器
```
1、github 下载：
https://github.com/medcl/elasticsearch-analysis-ik
2、放入es种插件目录plugins下，创建一个ik目录，ik下解压
3、重启es即可,看日志
[INFO ][o.e.p.PluginsService     ] [node-0] loaded plugin [analysis-ik]
```

# 代码框架
* 后段代码实现 依赖：springboot 2.3.0.RELEASE版本 默认版本对应： spring-data-elasticsearch4.0、es7.6.2 
  * 版本关系：https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.requirements
  * API参考： https://docs.spring.io/spring-data/elasticsearch/docs/4.0.0.RELEASE/reference/html/#elasticsearch.query-methods
* thymeleaf 提供页面支持，实现仿jd web商品检索页面

## 代码实现功能：
* 1、利用jsoup爬虫jd数据同步至es索引库
* 2、基于spring-data-elasticsearch的基础检索（jpa方式开发）
* 3、基于原生高级客户端 HLRC 实现高亮检索、compleation-suggest 自动补全建议搜索功能（没定义分词器 简单的前缀补全功能） 接口：/suggest2
* 4、自定义拼音分词器 + 自动补全功能 接口：/suggest3 （包含上一个功能）
* 5、前端展示模拟jd搜索首页、高亮搜索、自动补全建议搜索、拼音自动补全建议搜索 功能演示。
    * 访问搜索首页： localhost:8081/index
    * 高亮搜索测试： 输入框输入 java 点击搜索按钮
    * 自动补全测试：在输入框输入 movies索引或者stars 索引中相关的 title和name的信息即可
```
    因为自动补全功能使用的是不同的索引数据，所以接口代码未整合。测试演示时候需要替换请求接口来观察演示效果。
    当你需要测试简单的自动补全功能时候： 调用接口：/suggest2
    当你需要测试简单的自动补全功能时候： 调用接口： /suggest3
```

## 演示效果图 查看image文件夹 

## 仿京东电商搜索数据准备
* 数据建模 （数据来源通过爬虫，爬虫代码比较简易，需要特点数据可能需要单独测试修改）
```
# 自定义 analyzer
PUT jd-goods
{
  "settings": {
    "analysis": {
      "analyzer": {
        "goods_tags_analyzer":{
          "char_filter":["html_strip"],
          "tokenizer":"keyword",
          "filter":"goods_tags_filter"
        }
      },
      "filter": {
        "goods_tags_filter":{
          "type" : "pinyin",
          "keep_joined_full_pinyin":true,
          "keep_full_pinyin": false,
          "keep_original": true
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "img":{
        "type": "keyword"
      },
      "price":{
        "type": "double"
      },
      "title":{
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "shop":{
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "suggestTags":{
        "type": "completion",
        "analyzer": "goods_tags_analyzer",
        "search_analyzer": "keyword"
      },
      "goodsInfoUrl":{
        "type": "keyword"
      }
    }
  }
}
```


## 其他相关索引的定义和数据准备：(自动补全的功能)
* /suggest2 接口请求 对应的是 movies索引
```

# 创建索引
PUT movies
{
  "mappings": {
    "properties": {
      "id":{
        "type": "integer"
      },
      "title":{
        "type": "keyword"
      },
      "suggest":{
        "type": "completion"
      },
      "year":{
        "type": "integer"
      },
      "country":{
        "type": "keyword"
      }
    }
  }
}

# 批量添加数据
PUT movies/_bulk
{"index":{"_id":1}}
{"id":1,"title":"星际穿越","suggest":"星际穿越","year":2000,"country":"USA"}
{"index":{"_id":2}}
{"id":2,"title":"星际迷航","suggest":"星际迷航","year":2011,"country":"USA"}
{"index":{"_id":3}}
{"id":3,"title":"复仇者联盟1","suggest":"复仇者联盟1","year":2012,"country":"USA"}
{"index":{"_id":4}}
{"id":4,"title":"复仇者联盟2","suggest":"复仇者联盟2","year":2014,"country":"USA"}
{"index":{"_id":5}}
{"id":5,"title":"爱乐之城","suggest":"爱乐之城","year":2017,"country":"USA"}
{"index":{"_id":6}}
{"id":6,"title":"当幸福来敲门","suggest":"当幸福来敲门","year":2010,"country":"USA"}
{"index":{"_id":7}}
{"id":7,"title":"战狼1","suggest":"战狼1","year":2010,"country":"ZH"}
{"index":{"_id":8}}
{"id":8,"title":"战狼2","suggest":"战狼2","year":2013,"country":"ZH"}
{"index":{"_id":9}}
{"id":9,"title":"战狼3","suggest":"战狼3","year":2010,"country":"ZH"}
{"index":{"_id":10}}
{"id":10,"title":"星球大战","suggest":"星球大战","year":2008,"country":"USA"}
{"index":{"_id":11}}
{"id":11,"title":"电流大战","suggest":"电流大战","year":1998,"country":"USA"}
{"index":{"_id":12}}
{"id":12,"title":"肖申克的救赎","suggest":"肖申克的救赎","year":2006,"country":"USA"}
{"index":{"_id":13}}
{"id":13,"title":"beautify","suggest":"beautify","year":2019,"country":"USA"}
{"index":{"_id":14}}
{"id":14,"title":"love","suggest":"love","year":2018,"country":"USA"}

# 自动补全搜索测试:  prefix：前缀搜索
GET movies/_search
{
  "suggest": {
    "title_prefix_suggest": {
      "prefix": "战",
      "completion":{
        "field":"suggest"
      }
    }
  }
}

```

* 拼音分词器定制+自动补全功能接口 /suggest3 对应的索引 stars
```
PUT stars
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_pinyin_analyzer":{
          "char_filter":["html_strip"],
          "tokenizer":"keyword",
          "filter":"my_pinyin_filter"
        }
      },
      "filter": {
        "my_pinyin_filter":{
          "type" : "pinyin",
          "keep_joined_full_pinyin":true,
          "keep_full_pinyin": false,
          "keep_original": true
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name":{
        "type": "completion",
        "analyzer": "my_pinyin_analyzer",
        "search_analyzer": "keyword"
      }
    }
  }
}

# 批量添加测试数据
POST stars/_bulk
{"index":{}}
{"name":"张学友"}
{"index":{}}
{"name":"刘德华"}
{"index":{}}
{"name":"柳岩"}
{"index":{}}
{"name":"李易峰"}
{"index":{}}
{"name":"黄晓明"}
{"index":{}}
{"name":"刘青云"}
{"index":{}}
{"name":"易中天"}
{"index":{}}
{"name":"李小璐"}
{"index":{}}
{"name":"张飞"}
{"index":{}}
{"name":"柳真"}
{"index":{}}
{"name":"柳宗元"}
{"index":{}}
{"name":"古天乐"}
{"index":{}}
{"name":"李思思"}
{"index":{}}
{"name":"陈赫"}
{"index":{}}
{"name":"李晨"}
{"index":{}}
{"name":"王宝强"}
{"index":{}}
{"name":"庄媛媛"}
{"index":{}}
{"name":"万茜"}
{"index":{}}
{"name":"黄磊"}
{"index":{}}
{"name":"黄渤"}
{"index":{}}
{"name":"郑恺"}
```
