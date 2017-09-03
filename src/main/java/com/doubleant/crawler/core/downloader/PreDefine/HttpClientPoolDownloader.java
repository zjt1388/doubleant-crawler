package com.doubleant.crawler.core.downloader.PreDefine;

import com.doubleant.crawler.core.downloader.Downloader;
import com.doubleant.crawler.model.Page;
import com.doubleant.crawler.model.UrlSeed;
import com.doubleant.crawler.utils.HttpUtils;

/**
 * Created by shilei
 * on 2017/4/10.
 */
public class HttpClientPoolDownloader implements Downloader {

    public Page download(UrlSeed urlSeed) {
        String html = HttpUtils.getInstance().get(urlSeed.getUrl());
        Page page = new Page(urlSeed, html);
        return page;
    }
}
