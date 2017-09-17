import com.doubleant.crawler.dao.ArticleDao;
import com.doubleant.crawler.dao.ProductClassifyDao;
import com.doubleant.crawler.dao.ProductDao;
import com.doubleant.crawler.dao.ProductDetailDao;
import com.doubleant.crawler.po.ProductDetailPO;
import com.doubleant.crawler.po.ProductPO;
import com.doubleant.crawler.po.WacaiArticlePO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * User: cairne
 * Date: 13-5-13
 * Time: 下午8:33
 */

public class ProductTest extends BaseTest{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductDetailDao productDetailDao;
    @Autowired
    private ProductClassifyDao productClassifyDao;

    @Test
    public void test() {
        ProductPO productPO = new ProductPO();
        productPO.setProductName("test1");
        productPO.setProductUrl("http://www.baidu.com");
        productDao.add(productPO);
    }

    @Test
    public void saveDetailTest(){
        ProductDetailPO detailPO = new ProductDetailPO();
        detailPO.setProductName("222");
        detailPO.setImgUrl("111");
        detailPO.setProductDesc("111");
        detailPO.setEffect("effect");
        detailPO.setComponent("component");
        detailPO.setIngredients("Ingredients");
        productDetailDao.insert(detailPO);
    }

    @Test
    public void getAllClassifyTest(){
        System.out.println(productClassifyDao.getAllClassify().size());
    }
}
