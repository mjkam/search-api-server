package com.mjkam.search.api.ranking;

import com.mjkam.search.api.repository.KeywordCountJpaRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class Consumer extends Thread {
    private final KeywordCountJpaRepository keywordCountJpaRepository;
    private final LinkedBlockingQueue<String> queue;

    public Consumer(KeywordCountJpaRepository keywordCountJpaRepository, LinkedBlockingQueue<String> queue) {
        this.keywordCountJpaRepository = keywordCountJpaRepository;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!queue.isEmpty()) {
                keywordCountJpaRepository.incrementCount(queue.take());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
