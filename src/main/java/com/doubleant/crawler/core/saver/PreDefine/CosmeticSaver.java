package com.doubleant.crawler.core.saver.PreDefine;

import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.dao.ProductDao;
import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.po.ProductPO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * http://www.cosmetic-ingredients.net/
 * 网站品牌内容抓取
 * Created by shilei
 * on 2017/4/11.
 */
@Service("cosmeticSaver")
public class CosmeticSaver implements Saver {

    @Autowired
    private ProductDao productDao;

    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();

        //map.forEach((k, v) -> System.out.println(k + " : " + v));

        Document document = page.getDocument();
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            //System.out.println(linkHref + "---" + linkText);
        }
        Element element = document.getElementsByTag("select").first();
        if(element != null) {
            System.out.println("content_zong" + "===" + element.text());
            Elements cLinks = element.getElementsByTag("option");
            if (!cLinks.isEmpty()) {
                for (Element link : cLinks) {
                    String linkText = link.text();
                    String value = page.getUrlSeed().getUrl() +link.val();
                    System.out.println(value + "---" + linkText);
                    ProductPO productPO = new ProductPO();
                    productPO.setProductName(linkText);
                    productPO.setProductUrl(value);
                    productDao.add(productPO);
                }
            }
        }
        try {
            Thread.sleep(10000L);
        }catch (Exception e){

        }
        return page;
    }

}
