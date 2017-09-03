package com.doubleant.crawler.example;

import com.doubleant.crawler.core.Spider;
import com.doubleant.crawler.core.pageprocesser.PageProcessor;
import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.core.scheduler.PreDefine.RedisScheduler;
import com.doubleant.crawler.model.Page;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by shilei on 2017/4/12.
 */
public class RedisSpider {

    public static void main(String[] args) {

        Spider.build()
                .setScheduler(new RedisScheduler()) //配置个性化的ip等请使用 RedisScheduler(String ip, int port, int MaxActive(建议100))
                .setSaver(mySaver)
                .setProcessor(myPageProcessor)
                .thread(5)
                .addUrlSeed("http://news.xjtu.edu.cn")
                .addRegexRule("+http://news.xjtu.edu.cn/.*") //限制爬取《交大新闻网》以外的其他站点的信息
                .run();
    }


    /**
     * 一个输出到控制台的 存储器。
     * 您可以替换这段代码，将爬取到的结果放入到mongodb，mysql等等中！
     */
    static Saver mySaver = new Saver() {
        @Override
        public Page save(Page page) {
            Map<Object, Object> map = page.getItems();

            map.forEach((k, v) -> System.out.println(k + " : " + v));
            return page;
        }
    };

    /**
     * 实现自己逻辑的页面解析功能！将标题打印出来。
     * <p>
     * 这里是在一个文件里实现的，若果你的功能比较多，完全可以用新的class文件来生成，并在上面setPageProcessor即可！
     */
    static PageProcessor myPageProcessor = new PageProcessor() {

        @Override
        public Page process(Page page) {

            //如果不匹配，则不进行解析！
            if (!Pattern.matches("http://news.xjtu.edu.cn/info/.*htm", page.getUrlSeed().getUrl())) {
                return page;
            }

            Document htmldoc = page.getDocument();
            //select返回的是一个数组，所以需要first，相关语法请google“jsoup select语法”和“cssquery”
            try {
                String title = htmldoc.select(".d_title").first().text();

                //用来存放爬取到的信息，供之后存储！map类型的即可，可以自定义各种嵌套！
                Map<String, String> items = new HashMap<String, String>();
                items.put("title", title);
                items.put("url", page.getUrlSeed().getUrl());
                //放入items中，之后会自动保存（如果你自己实现了下载器，请自己操作它。如下我自定义了自己的下载器，并将它保存到了文本中！）！
                page.setItems(items);
            } catch (NullPointerException e) {
                System.out.println("该页面没有解析到相关东西！跳过");
            }
            return page;
        }

        /**
         * 新url种子进行额外的处理！（先进行了默认提供的正则处理！之后才进行这步。建议功能：在这里进行优先级的调整！）
         * <p>
         * redis实现了优先级的。这里我们不做展示。优先级的用法请到示范：PriorityQueueScheduler.java 中查看。
         * redis的默认优先级是5.并且只有三个优先级，高于5的，低于5的，等于5的。
         * @param page
         * @return 自己
         */
        @Override
        public Page processNewUrlSeeds(Page page) {
            return page;
        }
    };


}
