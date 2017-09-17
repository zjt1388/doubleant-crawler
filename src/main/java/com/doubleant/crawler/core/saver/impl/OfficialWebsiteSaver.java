package com.doubleant.crawler.core.saver.impl;

import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.dao.ProductDao;
import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.po.ProductPO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * http://www.cosmetic-ingredients.net/
 * 网站品牌内容抓取
 * Created by shilei
 * on 2017/4/11.
 */
@Service("officialWebsiteSaver")
public class OfficialWebsiteSaver implements Saver {

    private static Logger logger = LoggerFactory.getLogger(OfficialWebsiteSaver.class);

    @Autowired
    private ProductDao productDao;

    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();
        logger.info("抓取url为：" + page.getUrlSeed().getUrl());
        ProductPO productPO = productDao.getByProductUrl(page.getUrlSeed().getUrl());
        Document document = page.getDocument();
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            if(linkText.contains("http")) {
                logger.info(linkHref + "---" + linkText);
                productPO.setOfficialWebsite(linkHref);
                productDao.updateOfficialWebsite(productPO);
            }
        }
        try {
            //Thread.sleep(10000L);
        }catch (Exception e){

        }
        return page;
    }

}
