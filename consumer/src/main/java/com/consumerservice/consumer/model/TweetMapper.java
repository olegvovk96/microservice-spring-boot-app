package com.consumerservice.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter @Setter
public class TweetMapper {
    private Long tweetId;
    private String text;
    private LocalDateTime date;
}
