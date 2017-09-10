package com.doubleant.crawler.core;


import com.doubleant.crawler.core.downloader.Downloader;
import com.doubleant.crawler.core.downloader.PreDefine.HttpClientPoolDownloader;
import com.doubleant.crawler.core.pageprocesser.PageProcessor;
import com.doubleant.crawler.core.pageprocesser.PreDefine.TextPageProcessor;
import com.doubleant.crawler.core.saver.PreDefine.ConsoleSaver;
import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.core.scheduler.PreDefine.QueueScheduler;
import com.doubleant.crawler.core.scheduler.Scheduler;
import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.model.RegexRule;
import com.doubleant.crawler.model.UrlSeed;
import com.doubleant.crawler.utils.TimeSleep;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shilei
 * on 2017/4/10.
 */
public class Spider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Scheduler scheduler;
    private Downloader downloader;
    private PageProcessor pageProcessor;
    private Saver saver;

    //新种子的过滤器，只有通过正则的，才会加入到待爬取种子队列
    private RegexRule regexRule;

    private int threadNum = 5;//线程池大小。默认5个爬虫在进行。
    private ThreadPoolExecutor pool;

    /**
     * 最多几个爬虫在进行。
     * 默认5个。
     *
     * @param threadNum
     * @return 自己
     */
    public Spider thread(int threadNum) {
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            this.threadNum = 5;
        }
        pool = new ThreadPoolExecutor(threadNum, threadNum,
                1500L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        return this;
    }

    public static Spider build() {
        return new Spider();
    }

    public Spider() {
        setDefaultComponents();
        regexRule = new RegexRule();
    }

    public Spider setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Spider setDownloader(Downloader d) {
        this.downloader = d;
        return this;
    }

    public Spider setProcessor(PageProcessor p) {
        this.pageProcessor = p;
        return this;
    }

    public Spider setSaver(Saver s) {
        this.saver = s;
        return this;
    }

    /**
     * 添加初始化种子，可以多个
     *
     * @param url
     * @return Spider
     */
    public Spider addUrlSeed(String url) {
        scheduler.push(new UrlSeed(url));
        return this;
    }

    /**
     * 添加新种子需要满足的正则信息（正则规则有两种，正正则和反正则）
     * <p>
     * URL符合正则规则需要满足下面条件：
     * 1.至少能匹配一条正正则
     * 2.不能和任何反正则匹配
     * 举例：
     * 正正则示例：+a.*c是一条正正则，正则的内容为a.*c，起始加号表示正正则
     * 反正则示例：-a.*c时一条反正则，正则的内容为a.*c，起始减号表示反正则
     * 如果一个规则的起始字符不为加号且不为减号，则该正则为正正则，正则的内容为自身
     * 例如a.*c是一条正正则，正则的内容为a.*c
     *
     * @param regex 正则
     * @return Spider
     */
    public Spider addRegexRule(String regex) {
        regexRule.addRule(regex);
        return this;
    }

    private Spider setDefaultComponents() {

        thread(threadNum);

        if (scheduler == null) {
            scheduler = new QueueScheduler();
        }
        if (downloader == null) {
            downloader = new HttpClientPoolDownloader();
        }
        if (pageProcessor == null) {
            pageProcessor = new TextPageProcessor();
        }
        if (saver == null) {
            saver = new ConsoleSaver();
        }
        return this;
    }

    public void run() {

        logger.info("爬虫启动!");


        UrlSeed urlSeed = null;
        while (true) {
            logger.info("当前线程池" + "已完成:" + pool.getCompletedTaskCount() + "   运行中：" + pool.getActiveCount() + "  最大运行:" + pool.getPoolSize() + " 等待队列:" + pool.getQueue().size());
            if (pool.getQueue().size() > pool.getCorePoolSize()) {
                //如果等待队列大于了100.就暂停接收新的url。不然会影响优先级队列的使用。
                TimeSleep.sleep(1000);
                continue;
            }
            urlSeed = scheduler.poll();
            if (urlSeed == null && pool.getActiveCount() == 0) {
                pool.shutdown();
                try {
                    pool.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    logger.error("关闭线程池失败！", e);
                }
                logger.info("爬虫结束！");
                break;
            } else if (urlSeed == null) {
                //没有取到种子就等待!
                TimeSleep.sleep(1000);
            } else {
                logger.info("正在处理:" + urlSeed.getUrl() + "  优先级(默认:5):" + urlSeed.getPriority());
                pool.execute(new SpiderWork(urlSeed.clone()));
            }
        }

    }

    class SpiderWork implements Runnable {

        private UrlSeed urlSeed;

        SpiderWork(UrlSeed urlSeed) {

            this.urlSeed = urlSeed;
        }

        public void run() {

            logger.debug("线程:[" + Thread.currentThread().getName() + "]正在处理:" + urlSeed.getUrl());
            logger.debug("当前线程池" + "已完成:" + pool.getCompletedTaskCount() + "   运行中：" + pool.getActiveCount() + "  最大运行:" + pool.getPoolSize() + " 等待队列:" + pool.getQueue().size());

            //整个流程为:
            // (download下载) ->  (pageProcessor解析处理) ->  (save存储)

            Page nowPage = downloader.download(urlSeed);

            pageProcessor.process(nowPage);
            //正则处理
            List<UrlSeed> urlSeedList = nowPage.links();
            for (Iterator<UrlSeed> it = urlSeedList.iterator(); it.hasNext(); ) {
                UrlSeed seed = it.next();
                if (!regexRule.regex(seed.getUrl())) {
                    if(StringUtils.isNotBlank(seed.getUrl())) {
                        System.out.println("urlSeedList remove :" + seed.getUrl());
                    }
                    it.remove();
                }
            }
            nowPage.setNewUrlSeed(urlSeedList);
            pageProcessor.processNewUrlSeeds(nowPage);

            //nowPage.getNewUrlSeed().forEach(seed -> scheduler.push(seed));

            saver.save(nowPage);

        }
    }
}

