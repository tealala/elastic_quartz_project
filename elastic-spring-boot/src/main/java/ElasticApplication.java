import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhuxiaoyu@digidite.com
 * @date 2020/5/7
 */
@ComponentScan(basePackages = "com.tealala")
@SpringBootApplication
public class ElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticApplication.class, args);
    }
}
