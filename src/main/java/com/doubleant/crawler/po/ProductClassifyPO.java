package com.doubleant.crawler.po;

/**
 * Created by zhoujt
 * on 2017/9/17.
 */
public class ProductClassifyPO extends BasePO{

    private String typeName;

    private String typeUrl;

    private String typeNameZh;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public String getTypeNameZh() {
        return typeNameZh;
    }

    public void setTypeNameZh(String typeNameZh) {
        this.typeNameZh = typeNameZh;
    }
}
