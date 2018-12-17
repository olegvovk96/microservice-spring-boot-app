package com.consumerservice.consumer.event;

import org.springframework.context.ApplicationEvent;

public class AfterCommitEvent extends ApplicationEvent {
    public AfterCommitEvent(Object source) {
        super(source);
    }
}
