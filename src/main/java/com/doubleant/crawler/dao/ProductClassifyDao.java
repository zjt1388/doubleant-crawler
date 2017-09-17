package com.doubleant.crawler.dao;


import com.doubleant.crawler.po.ProductClassifyPO;

import java.util.List;

public interface ProductClassifyDao {
	
    int insert(ProductClassifyPO classifyPO);

    /**
     * 查询所有数据
     * @return
     */
    List<ProductClassifyPO> getAllClassify();
}
