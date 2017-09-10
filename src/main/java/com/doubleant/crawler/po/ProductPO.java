package com.doubleant.crawler.po;

import lombok.Data;

/**
 * Created by Administrator
 * on 2017/9/3.
 */
@Data
public class ProductPO extends BasePO{

    private String productName;

    private String productUrl;
    /**
     * 官网
     */
    private String officialWebsite;
}
