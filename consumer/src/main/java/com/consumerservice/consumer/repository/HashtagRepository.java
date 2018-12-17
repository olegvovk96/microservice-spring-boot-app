package com.consumerservice.consumer.repository;

import com.consumerservice.consumer.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
}
