import com.doubleant.crawler.core.Spider;
import com.doubleant.crawler.core.saver.Saver;
import com.doubleant.crawler.dao.ProductDao;
import com.doubleant.crawler.po.ProductPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator
 * on 2017/8/12.
 */
public class CosmeticTest extends BaseTest{

    @Autowired
    private Saver cosmeticSaver;
    @Autowired
    private Saver officialWebsiteSaver;
    @Autowired
    private ProductDao productDao;

    @Test
    public void productTest(){
        //抓取化妆品产品信息
        Spider.build()
                .addUrlSeed("http://www.cosmetic-ingredients.net/")
                .addRegexRule("+http://www.cosmetic-ingredients.net/.*") //限制爬取《交大新闻网》以外的其他站点的信息
                .setSaver(cosmeticSaver)
                .run();
    }

    /**
     * 补全官网信息
     */
    @Test
    public void officialWebsiteTest(){
        List<ProductPO> productPOS = productDao.getAllProducts();
        for(ProductPO productPO : productPOS) {
            //抓取化妆品产品信息
            Spider.build()
                    .addUrlSeed(productPO.getProductUrl())
                    .addRegexRule("+http://www.cosmetic-ingredients.net/.*")
                    .thread(1)
                    .setSaver(officialWebsiteSaver)
                    .run();
        }
    }
}
