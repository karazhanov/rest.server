package ua.lg.karazhanov;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ua.lg.karazhanov")
@Slf4j
public class Starter {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ua.lg.karazhanov.Starter.class);
        Vertx vertx = Vertx.vertx();
        ServerVerticle serverVerticle = context.getBean(ServerVerticle.class);
        vertx.deployVerticle(serverVerticle, e ->  {
            if(e.failed()){
                log.error("Fail to deploy server verticle. Shutdown vertx.");
                vertx.close();
            }
        });
    }

}
