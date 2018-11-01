package com.tweetservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "search")
@Getter @Setter
public class HashtagProperties {
    List<String> hashtags;
}
