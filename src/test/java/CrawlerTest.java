import com.doubleant.crawler.core.Spider;
import org.junit.Test;

/**
 * Created by Administrator
 * on 2017/8/12.
 */
public class CrawlerTest {

    @Test
    public void cTest(){
        Spider.build()
                .addUrlSeed("http://www.cosmetic-ingredients.net/")
                //.addRegexRule("+http://news.xjtu.edu.cn/.*") //限制爬取《交大新闻网》以外的其他站点的信息
                .run();
    }
}
