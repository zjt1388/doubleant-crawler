package com.doubleant.crawler.core.downloader;

import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.model.UrlSeed;

/**
 * Created by shilei on 2017/4/10.
 * 下载器
 */
public interface Downloader {

    /**
     * @param urlSeed 下载url页面
     */
    public Page download(UrlSeed urlSeed);
}
