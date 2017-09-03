package com.doubleant.crawler.core.saver.PreDefine;

import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.model.Page;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by shilei
 * on 2017/4/11.
 */
//@Service("consoleSaver")
public class ConsoleSaver implements Saver {
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
        Element element = document.getElementById("vsb_newscontent");
        if(element != null) {
            System.out.println("content_zong" + "===" + element.text());
            Elements cLinks = element.getElementsByTag("p");
            if (!cLinks.isEmpty()) {
                for (Element link : cLinks) {
                    String linkText = link.text();
                    System.out.println("content" + "---" + linkText);
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
