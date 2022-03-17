# elasticsearch-demo
es搜索引擎实现 仿jd 数据爬虫到es、 高亮检索 、前缀自动补全建议搜索等功能

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
* 3、基于原生高级客户端 HLRC 实现高亮检索、compleation-suggest 自动补全建议搜索功能
