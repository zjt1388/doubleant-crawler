package com.doubleant.crawler.dao;


import com.doubleant.crawler.po.ProductDetailPO;
import com.doubleant.crawler.po.ProductPO;

import java.util.List;

public interface ProductDetailDao {
	
    int insert(ProductDetailPO detailPO);
}
