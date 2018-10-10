package com.tweetservice;

import com.tweetservice.properties.ClientTwitterProperties;
import com.tweetservice.properties.HashtagProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties({ClientTwitterProperties.class, HashtagProperties.class})
@EnableScheduling
@SpringBootApplication
public class TweetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetServiceApplication.class, args);
    }
}
