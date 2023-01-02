package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //현재 패키지부터 하위패키지까지 쭉 서블릿으로 등록시켜줌
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
    }

    /**
     * application.properties에서 설정해놓으면 자동으로 springboot가 해줌
     */
/*    @Bean
    ViewResolver InternalResourceViewResolver() {
        return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
    }*/

}
