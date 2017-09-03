package com.doubleant.crawler.po;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator
 * on 2017/9/3.
 */
@Data
public class BasePO {

    private Long id;

    private Date createTime;

    private Date updateTime;
}
