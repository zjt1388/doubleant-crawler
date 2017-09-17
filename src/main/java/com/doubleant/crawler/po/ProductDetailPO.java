package com.doubleant.crawler.po;

/**
 * Created by Administrator
 * on 2017/9/17.
 */
public class ProductDetailPO extends BasePO{

    private String productName;

    private String imgUrl;

    private String productDesc;
    /**
     * 作用
     */
    private String effect;
    /**
     * 成分
     */
    private String component;
    /**
     * 配料
     */
    private String ingredients;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
