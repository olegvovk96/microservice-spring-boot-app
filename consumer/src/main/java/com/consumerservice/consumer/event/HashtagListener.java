package com.consumerservice.consumer.event;

import com.consumerservice.consumer.model.Hashtag;
import com.consumerservice.consumer.service.DataService;
import com.consumerservice.consumer.service.HashtagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HashtagListener {
    private final DataService dataService;

    private final HashtagService hashtagService;

    @Getter
    private Map<String, Integer> hashtagById;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeMapHashtagById() {
        hashtagById = new HashMap<>();
        hashtagService.fillTableHashtag();
    }

    @TransactionalEventListener(AfterCommitEvent.class)
    public void updateHashtagById() {
        List<Hashtag> hashtags = dataService.getAllHashtags();
        hashtags.forEach(hashtag -> hashtagById.put(hashtag.getHashtag(), hashtag.getId()));
    }
}
