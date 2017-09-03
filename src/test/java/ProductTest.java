import com.doubleant.crawler.dao.ArticleDao;
import com.doubleant.crawler.dao.ProductDao;
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

    @Test
    public void test() {
        ProductPO productPO = new ProductPO();
        productPO.setProductName("test1");
        productPO.setProductUrl("http://www.baidu.com");
        productDao.add(productPO);
    }
}
