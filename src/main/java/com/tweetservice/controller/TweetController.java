package com.tweetservice.controller;

import com.tweetservice.model.Tweet;
import com.tweetservice.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets(){
        return tweetService.getAll();
    }
}
