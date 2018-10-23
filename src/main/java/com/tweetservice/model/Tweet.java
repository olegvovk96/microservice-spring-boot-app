package com.tweetservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@AllArgsConstructor
@EqualsAndHashCode(of = {"text"})
@Builder
@Getter @Setter
@Document
public class Tweet {
    @Id
    private Long tweetId;
    private String text;
    private String authorOfTweet;
    private LocalDateTime date;
    private String hashtag;
}
