package com.mjkam.search.api.ranking;

import com.mjkam.search.api.repository.KeywordCountJpaRepository;

import java.util.concurrent.LinkedBlockingQueue;

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
            while(true) {
                String take = queue.take();
                keywordCountJpaRepository.incrementCount(take);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
