package me.titatic.hackatonpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HackatonpracticeApplication {

    // public static final String APPLICATION_LOCATIONS = "spring.config.location="
    //     + "classpath:application.yml,"
    //     + "classpath:aws.yml";
    //
    // public static void main(String[] args) {
    //     new SpringApplicationBuilder(HackatonpracticeApplication.class)
    //         .properties(APPLICATION_LOCATIONS)
    //         .run(args);
    // }

    public static void main(String[] args) {
        SpringApplication.run(HackatonpracticeApplication.class, args);
    }
}
