package fr.gdc.imaker.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaImakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaImakerApplication.class, args);
    }

}
