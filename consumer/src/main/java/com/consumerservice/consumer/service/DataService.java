package com.consumerservice.consumer.service;

import com.consumerservice.consumer.model.Hashtag;
import com.consumerservice.consumer.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataService {
    private final HashtagRepository hashtagRepository;

    public List<Hashtag> getAllHashtags() {
        return hashtagRepository.findAll();
    }

    public void saveHashtags(Map<String, Integer> hashtagById) {
        List<Hashtag> hashtags = getHashtags(hashtagById);
        hashtags.removeAll(getAllHashtags());
        hashtagRepository.saveAll(hashtags);
    }

    private List<Hashtag> getHashtags(Map<String, Integer> hashtagById) {
        List<Hashtag> hashtags = new ArrayList<>();
        hashtagById.forEach((k, v) -> {
            Hashtag hashtag = new Hashtag(k);
            hashtags.add(hashtag);
        });
        return hashtags;
    }
}
