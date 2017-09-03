package com.doubleant.crawler.core.scheduler.PreDefine;

import com.doubleant.crawler.core.scheduler.Scheduler;
import com.doubleant.crawler.model.UrlSeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by shilei
 * on 2017/4/11.
 */
public class PriorityQueueScheduler implements Scheduler {

    public static final int defaultPriority = 5;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private PriorityBlockingQueue<UrlSeed> priorityQueue =
            new PriorityBlockingQueue<UrlSeed>(defaultPriority, new Comparator<UrlSeed>() {
                @Override
                public int compare(UrlSeed o1, UrlSeed o2) {
                    return -((o1.getPriority() < o2.getPriority()) ? -1 : ((o1.getPriority() == o2.getPriority()) ? 0 : 1));
                }
            });
    private Set<UrlSeed> urlSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void push(UrlSeed urlSeed) {
        if (urlSeed.getUrl() == null
                || urlSeed.getUrl().trim().equals("")
                || urlSeed.getUrl().trim().equals("#")
                || urlSeed.getUrl().trim().toLowerCase().contains("javascript:"))
            return;
        if (urlSet.add(urlSeed)) {
            priorityQueue.add(urlSeed);
        } else {
            //logger.info("UrlSeed重复:" + urlSeed.getUrl());

        }
    }

    @Override
    public UrlSeed poll() {
        return priorityQueue.poll();
    }

}
