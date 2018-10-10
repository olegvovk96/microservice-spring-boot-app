package com.tweetservice.service;

import com.tweetservice.model.Tweet;
import com.tweetservice.properties.ClientTwitterProperties;
import com.tweetservice.properties.HashtagProperties;
import com.tweetservice.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final ClientTwitterProperties twitterProperties;
    private final HashtagProperties hashtagProperties;

    private Twitter connectTwitter() {
        return new TwitterFactory(new ConfigurationBuilder()
                .setOAuthConsumerKey(twitterProperties.getApiKey())
                .setOAuthConsumerSecret(twitterProperties.getApiSecretKey())
                .setOAuthAccessToken(twitterProperties.getAccessToken())
                .setOAuthAccessTokenSecret(twitterProperties.getAccessTokenSecret())
                .setTweetModeExtended(true)
                .build()).getInstance();
    }

    public List<Tweet> getAll() {
        return tweetRepository.findAll();
    }

    private Set<Tweet> searchTweetsByHashtags(List<String> hashtags) throws TwitterException {
        Twitter twitter = connectTwitter();
        Set<Tweet> tweets = new HashSet<>();
        for (String hashtag : hashtags) {
            tweets.addAll(buildTweets(twitter, hashtag));
        }
        return tweets;
    }

    private Set<Tweet> buildTweets(Twitter twitter, String hashtag) throws TwitterException {
        Set<Tweet> tweets = new HashSet<>();
        Query query = new Query(format("%s %s", hashtag, "+exclude:retweets"));
        query.setCount(100);
        QueryResult queryResult = twitter.search(query);
        List<Status> statuses = queryResult.getTweets();
        statuses.stream()
                .filter(status -> convertDateToLocalDateTime(status.getCreatedAt())
                        .isAfter(LocalDateTime.now().minusMinutes(15)))
                .forEach(status -> {
                    Tweet tweet = Tweet.builder()
                            .tweetId(status.getId())
                            .text(status.getText())
                            .authorOfTweet(status.getUser().getName())
                            .date(convertDateToLocalDateTime(status.getCreatedAt()))
                            .hashtag(hashtag)
                            .build();
                    tweets.add(tweet);
                });
        return tweets;
    }

    private LocalDateTime convertDateToLocalDateTime(Date tweetDate){
        return tweetDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Scheduled(cron = "0 0/15 * * * ?")
    public void saveTweets() throws TwitterException {
        tweetRepository.saveAll(searchTweetsByHashtags(hashtagProperties.getHashtags()));
    }
}
