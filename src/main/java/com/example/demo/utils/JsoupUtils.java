package com.example.demo.utils;

import com.example.demo.entity.GoodInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;

/**
 * 爬虫工具类
 * @author bin
 * @data 2020-08-01
 */
public class JsoupUtils {

    public static void main(String[] args) throws Exception {
//        String url = "https://search.jd.com/Search?keyword=java";
//        // 获取document对象
//        Document document = Jsoup.parse(new URL(url), 30000);
//        //获取商品的 element 对象
//        Element j_goodsList = document.getElementById("J_goodsList");
//
////        System.out.println(j_goodsList.html());
//        //获取li元素
//        Elements li = j_goodsList.getElementsByTag("li");
//
//        for (Element e:li) {
//            String img = e.getElementsByTag("img").eq(0).attr("src");
//            String price = e.getElementsByClass("p-price").eq(0).text();
//            String title = e.getElementsByClass("p-name").eq(0).text();
//
//            System.out.println(img);
//            System.out.println(price);
//            System.out.println(title);
//        }


        new JsoupUtils().parseToData("洗面奶").forEach(System.out::println);

//        Map<String, String> header = new HashMap<String, String>();
//
//        header.put("cookie", "shshshfpa=5e54c70c-16cc-1bdc-f9ce-738a9313f3c6-1596262788; shshshfpb=rcrgM0vUGc8S6%2FSV%2FrzpMxQ%3D%3D; unpl=JF8EAK1nNSttWxtdARwLSRIQGVxXWwkBHB8KP2dXBFwNTldSTAUbE0R7XlVdXhRKFR9vbhRUX1NOUQ4YBSsSEXteXVdZDEsWC2tXVgQFDQ8VXURJQlZAFDNVCV9dSRZRZjJWBFtdT1xWSAYYRRMfDlAKDlhCR1FpMjVkXlh7VAQrBR4TEEpeUV1eOHsQM19XAFNbWUlRNRoyGiJSHwFXWlwKTRNOaGIEVFxbTlcGKwMrEQ; __jdv=76161171|baidu-pinzhuan|t_288551095_baidupinzhuan|cpc|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_2a8568c11c024e8f98a1ca0d42ff610e|1647582267346; mt_xid=V2_52007VwQXU11ZUV8cSildAWYCRlsIDU5YGUxNQAA0ARZOVV1aCANAGVoHZgdCWl5dVQ8vShhfBnsCEE5dUUNaHUIcWQ5kCiJSbVhiXR9NH1oDYQIQVG1cVlkd; retina=0; cid=3; webp=1; visitkey=50570813780130897; rurl=https%3A%2F%2Fst.jingxi.com%2Fpingou%2Faccount%2Findex.html%3Fjxsid%3D16482785213080418567%26ptag%3D138631.36.4; mba_muid=640102422; __jd_ref_cls=MLoginRegister_SMSVerificationAppear; __jda=122270672.16486312660421344013177.1648631266.1648631266.1648631266.1; __jdc=122270672; __jdu=16486312660421344013177; shshshfp=f73d14f1fbd3afcbef961c731d46f40d; areaId=12; wlfstk_smdl=awzhgysf5lu2dvp3ah4uoyeabymmaygk; TrackID=1P5RSeURU34aUhPApl_RuZ_daaJEYUg0eMGyRWSoqcI7nXg3-RPiQOv34_Vosg9RhDH4-RAVmMxbfUeW30E6w9-aySENa5fZzQtill9yUc_OtK1HSbpSUnFZ9rsCOFfVK; thor=D10DC5656D8EF3EBAF3BAD7C891B7159789B5B5A92ADB60EFDCC74511B8F85F7B6F232C0541C03075A3EFAB95670EE14BEE3083F2162658EAB7082A94329DE17F51ACF63D43C0D7F019A40BFAC670D25A2330FB6A5366BE46680E04950E551ABEDD8FAF8FE3B4B95FB2203B77DD10429CF2E876535A449B8C1672DFFE098F87B4C68FB99842DCA015F5BB658663D577247D71501391EE1A5EE814EE9471DBFE7; pinId=EUoAXJ0YVEc-cIifoog9hLV9-x-f3wj7; pin=jd_50dfd5f2ce9b9; unick=jd_176619heb; ceshi3.com=000; _tp=s50%2BX2pi%2BDFeML5ATY7J2n4ZgBoPb2GcWg%2BJ%2B5Xqhb4%3D; _pst=jd_50dfd5f2ce9b9; token=4b2b544b12af89a5112cd883fa157686,3,915906; __tk=JUJqJpS5jpGxJpPpJUkTIzqxjUqzlUtzksPpjcnwjzS,3,915906; shshshsID=b35f7cfba850c93f6ca63bd5f1c73043_2_1648631488025; __jdb=122270672.8.16486312660421344013177|1.1648631266; ip_cityCode=988; ipLoc-djd=12-988-993-58088; 3AB9D23F7A4B3C9B=3ZN4WD6XQJDQ22PSIVOH3MMFNWFZFBXNT6NZKPCXA5YFQZBDGYV6OO5RPGDH6FROR6AOZAVHP7RXHSISPB5M7XXFOY");
//
//        Document doc = Jsoup.connect("https://item.jd.com/4702894.html").headers(header).get();
//        System.out.println(doc.html());


    }

    /**
     * 爬虫 封装es 测试搜索数据
     * @param keyWord
     * @return
     * @throws Exception
     */
    public List<GoodInfo> parseToData(String keyWord) throws Exception {
        String url = "https://search.jd.com/Search?keyword="+keyWord;
        // 获取document对象
        Document document = Jsoup.parse(new URL(url), 30000);
        //获取商品的 element 对象
        Element j_goodsList = document.getElementById("J_goodsList");
        //获取li元素
        Elements li = j_goodsList.getElementsByClass("gl-item");
        List<GoodInfo> goodInfos = new ArrayList<>();
        for (Element e:li) {
            String img = e.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = e.getElementsByClass("p-price").eq(0).get(0).getElementsByTag("i").eq(0).text();
            String title = e.getElementsByClass("p-name").eq(0).get(0).getElementsByTag("em").eq(0).text();
            String shopName = e.getElementsByClass("curr-shop hd-shopname").eq(0).text();

            // 商品详情信息获取
            String goodsInfoUrl = e.getElementsByClass("p-img").eq(0).get(0).getElementsByTag("a").attr("href");
            String goodsInfoUrl2 = "https:"+goodsInfoUrl;
            Map<String, String> header = new HashMap<String, String>();
            header.put("cookie", "shshshfpa=5e54c70c-16cc-1bdc-f9ce-738a9313f3c6-1596262788; shshshfpb=rcrgM0vUGc8S6%2FSV%2FrzpMxQ%3D%3D; unpl=JF8EAK1nNSttWxtdARwLSRIQGVxXWwkBHB8KP2dXBFwNTldSTAUbE0R7XlVdXhRKFR9vbhRUX1NOUQ4YBSsSEXteXVdZDEsWC2tXVgQFDQ8VXURJQlZAFDNVCV9dSRZRZjJWBFtdT1xWSAYYRRMfDlAKDlhCR1FpMjVkXlh7VAQrBR4TEEpeUV1eOHsQM19XAFNbWUlRNRoyGiJSHwFXWlwKTRNOaGIEVFxbTlcGKwMrEQ; __jdv=76161171|baidu-pinzhuan|t_288551095_baidupinzhuan|cpc|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_2a8568c11c024e8f98a1ca0d42ff610e|1647582267346; mt_xid=V2_52007VwQXU11ZUV8cSildAWYCRlsIDU5YGUxNQAA0ARZOVV1aCANAGVoHZgdCWl5dVQ8vShhfBnsCEE5dUUNaHUIcWQ5kCiJSbVhiXR9NH1oDYQIQVG1cVlkd; __jdu=16486312660421344013177; areaId=12; pinId=EUoAXJ0YVEc-cIifoog9hLV9-x-f3wj7; pin=jd_50dfd5f2ce9b9; unick=jd_176619heb; ceshi3.com=000; _tp=s50%2BX2pi%2BDFeML5ATY7J2n4ZgBoPb2GcWg%2BJ%2B5Xqhb4%3D; _pst=jd_50dfd5f2ce9b9; ipLoc-djd=12-988-993-58088; TrackID=16g2uE0nPWPbWu0t1n6NO8QS2D_bd7sd1TLgEiouBmgY3uToykGHMme-leNAXNw72vEqXPXCEejwf049xvmsvDtDZHTYEfO2noGIVHvBvFtlp6SSBQbMtG9v9j3xU4z6X; thor=D10DC5656D8EF3EBAF3BAD7C891B7159789B5B5A92ADB60EFDCC74511B8F85F7EB53FC937798E45B5E559782DE2D3B86FCB6450DBDB24A2D8DADEC6E78BBEB75026D5E060F31CF6B544FE945BF96ECB26C6D610697E71DFE79CAC247B136CA3898B1B4B13AA2A9C31B2EEABBAC3A7AA3EDE6AD248B0A1F591DA04019C3BDE22FA42490F1ACF1891C28866902295F44A25F7F0473970C7EEFBC3065279C58665C; PCSYCityID=CN_320000_320500_0; token=2e3b3689902b01a312cc901350f3e5ad,3,915908; __tk=yBh3xVbKvBrAKJzaIgbeOg5izIK4uLzQyJbDwMbPJcd4uLdgIiOsug5TzJrAHLTgIghsva9C,3,915908; shshshfp=f73d14f1fbd3afcbef961c731d46f40d; shshshsID=8e685dc7f9b30eaed3c34015f7e6a956_3_1648635604093; __jda=122270672.16486312660421344013177.1648631266.1648631266.1648635570.2; __jdb=122270672.5.16486312660421344013177|2.1648635570; __jdc=122270672; ip_cityCode=988; 3AB9D23F7A4B3C9B=3ZN4WD6XQJDQ22PSIVOH3MMFNWFZFBXNT6NZKPCXA5YFQZBDGYV6OO5RPGDH6FROR6AOZAVHP7RXHSISPB5M7XXFOY");
            Document goodsInfoDocument = Jsoup.connect(goodsInfoUrl2).headers(header).get();
            // 获取document对象
//            Document goodsInfoDocument = Jsoup.parse(new URL(goodsInfoUrl2), 30000);
            //获取商品的 element 对象
            Thread.sleep(500);
            Element j_goodsBrand = goodsInfoDocument.getElementById("parameter-brand");
            String brand="";
            List<String> tags = new ArrayList<>();
            tags.add(keyWord);
            if(j_goodsBrand.getElementsByTag("a")!=null){
                brand = j_goodsBrand.getElementsByTag("a").eq(0).text();
                tags.add(brand);
            }
            GoodInfo goodInfo =new GoodInfo(img,Double.parseDouble(price),title,shopName, tags.toArray(),goodsInfoUrl);
            goodInfos.add(goodInfo);

        }
        return goodInfos;
    }
}
