import com.doubleant.crawler.dao.ArticleDao;
import com.doubleant.crawler.po.WacaiArticlePO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * User: cairne
 * Date: 13-5-13
 * Time: 下午8:33
 */

public class WebMagicTest extends BaseTest{
    @Autowired
    private ArticleDao articleDao;

    @Test
    public void test() {
    	WacaiArticlePO wacaiArticleModel = new WacaiArticlePO();
    	wacaiArticleModel.setReplyNum(1);
    	wacaiArticleModel.setTitle("测试");
    	wacaiArticleModel.setUrl("http://www.baidu.com");
    	wacaiArticleModel.setViewNum(2);
    	wacaiArticleModel.setSource(1);
    	wacaiArticleModel.setUrlMd5("1235");
    	System.err.println(wacaiArticleModel.toString());
        try {
            int add = articleDao.add(wacaiArticleModel);
            System.out.println(add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
