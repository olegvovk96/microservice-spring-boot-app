package com.consumerservice.consumer.controller;

import com.consumerservice.consumer.event.HashtagListener;
import com.consumerservice.consumer.model.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.consumerservice.consumer.constants.Constants.*;
import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final RestTemplate restTemplate;

    private final HashtagListener hashtagListener;


    @GetMapping("/consumerTweet/pairHashtags")
    public Map<String, Integer> getPairHashtags() {
        return hashtagListener.getHashtagById();
    }

    @GetMapping("/consumerTweet/limitTweets")
    public List<TweetMapper> getLimitTweets() {
        ResponseEntity<List<TweetMapper>> tweetResponse = restTemplate.exchange("http://tweet-service//tweets/limitTweets",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TweetMapper>>() {
                });
        return tweetResponse.getBody();
    }

    @GetMapping("/consumerTweet/limitTweets/{countOfLimitElements}")
    public List<TweetMapper> getLimitTweets(@PathVariable Integer countOfLimitElements) {
        ResponseEntity<List<TweetMapper>> tweetResponse = restTemplate.exchange(format(LIMIT_TWEETS_ENDPOINT_URL, "http://tweet-service//tweets/limitTweets/", countOfLimitElements),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TweetMapper>>() {
                });
        return tweetResponse.getBody();
    }

    @GetMapping("/consumerTweet/percent/{hashtagIds}")
    public Double getPercentOfTweetByHashtagIds(@PathVariable List<Integer> hashtagIds) {
        return restTemplate.getForObject(format(PERCENT_ENDPOINT_URL, "http://tweet-service//tweets/percent/",
                replaceCharactersForHashtagIds(hashtagIds.toString())), Double.class);
    }

    private String replaceCharactersForHashtagIds(String hashtagIds) {
        return hashtagIds.replaceAll("[\\[\\] ]", "");
    }
}
