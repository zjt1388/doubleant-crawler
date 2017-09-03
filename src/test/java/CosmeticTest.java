import com.doubleant.crawler.core.Spider;
import com.doubleant.crawler.core.saver.Saver;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by Administrator
 * on 2017/8/12.
 */
public class CosmeticTest extends BaseTest{

    @Autowired
    private Saver cosmeticSaver;

    @Test
    public void cTest(){
        Spider.build()
                .addUrlSeed("http://www.cosmetic-ingredients.net/")
                .addRegexRule("+http://www.cosmetic-ingredients.net/.*") //限制爬取《交大新闻网》以外的其他站点的信息
                .setSaver(cosmeticSaver)
                .run();
    }
}
