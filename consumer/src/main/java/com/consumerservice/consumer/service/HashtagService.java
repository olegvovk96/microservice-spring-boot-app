package com.consumerservice.consumer.service;

import com.consumerservice.consumer.event.AfterCommitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final DataService dataService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 0/15 * * * ?")
    @Transactional
    public void fillTableHashtag() {
        Map<String, Integer> mapperHashtags = getMapperHashtags();
        dataService.saveHashtags(mapperHashtags);
        applicationEventPublisher.publishEvent(new AfterCommitEvent(this));
    }

    private Map<String, Integer> getMapperHashtags() {
        ResponseEntity<Map<String, Integer>> hashtagsResponse = restTemplate.exchange("http://tweet-service/pairHashtags",
                HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Integer>>() {
                });
        return hashtagsResponse.getBody();
    }
}
