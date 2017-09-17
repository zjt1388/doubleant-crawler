package com.doubleant.crawler.core.saver.impl;

import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.dao.ProductDao;
import com.doubleant.crawler.dao.ProductDetailDao;
import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.po.ProductDetailPO;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * http://www.cosmetic-ingredients.net/
 * 产品详情内容抓取
 * Created by zhoujt
 * on 2017/4/11.
 */
@Service("cosmeticDetailSaver")
public class CosmeticDetailSaver implements Saver {

    @Autowired
    private ProductDetailDao productDetailDao;

    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();

        //map.forEach((k, v) -> System.out.println(k + " : " + v));

        Document document = page.getDocument();
        Elements links = document.getElementsByClass("ProductTable");
        for (Element link : links) {
            ProductDetailPO detailPO = new ProductDetailPO();
            Element imageInfo = link.getElementsByClass("FigureImage").get(0);
            String imgUrl = imageInfo.attr("src");
            detailPO.setImgUrl("http://www.cosmetic-ingredients.net/" + imgUrl);
            System.out.println("http://www.cosmetic-ingredients.net/" + imgUrl);
            //取产品名称
            Element productName = link.getElementsByTag("productname").get(0).getElementsByTag("a").get(0);
                    //.get(0).getElementsByTag("font").get(0).getElementsByTag("font").get(0);
            System.out.println(productName.text());
            detailPO.setProductName(productName.text());
            //产品描述
            Element desc = link.getElementsByTag("value").get(0);
            System.out.println(desc.text());
            detailPO.setProductDesc(desc.text());
            //属性
            StringBuilder effect = new StringBuilder();
            Elements PropertyValues = link.getElementsByClass("PropertyValue");
            for(Element propertyValue : PropertyValues){
                String imgValue = propertyValue.getElementsByTag("img").get(0).attr("src");
                effect.append(imgValue).append(",");
            }
            System.out.println(effect.toString().substring(0,effect.toString().length() - 1));
            detailPO.setEffect(effect.toString().substring(0,effect.toString().length() - 1));
            //成分
            String component = link.getElementsByClass("ActiveIngredientList").get(0).getElementsByTag("value").get(0).text();
            System.out.println(component);
            detailPO.setComponent(component);
            //配料
            String ingredients = link.getElementsByClass("IngredientList").get(0).getElementsByTag("value").text();
            System.out.println(ingredients);
            detailPO.setIngredients(ingredients);

            //保存
            productDetailDao.insert(detailPO);
        }

        try {
            Thread.sleep(10000L);
        }catch (Exception e){

        }
        return page;
    }

}
