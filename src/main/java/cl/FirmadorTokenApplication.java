package cl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class FirmadorTokenApplication {

    public static void main(String[] args) {
            SpringApplication.run(FirmadorTokenApplication.class, args);
    }
    @PostConstruct
    void started() {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Santiago"));
    }
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**");
            }

//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/").setViewName(
//                        "forward:/dist/index.html");
//            }
        };
    }

}
