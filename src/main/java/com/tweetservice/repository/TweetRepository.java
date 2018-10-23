package com.tweetservice.repository;

import com.tweetservice.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<Tweet, Long> {
}
