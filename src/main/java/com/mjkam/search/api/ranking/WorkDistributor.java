package com.mjkam.search.api.ranking;

import com.mjkam.search.api.repository.KeywordCountJpaRepository;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class WorkDistributor {
    private final Thread[] consumers;
    private final LinkedBlockingQueue<String>[] queues;
    private final KeywordCountJpaRepository keywordCountJpaRepository;

    public WorkDistributor(KeywordCountJpaRepository keywordCountJpaRepository, ConsumerConfiguration consumerConfiguration) {
        this.queues = new LinkedBlockingQueue[consumerConfiguration.getSize()];
        this.consumers = new Thread[consumerConfiguration.getSize()];
        this.keywordCountJpaRepository = keywordCountJpaRepository;
    }

    public void insert(String keyword) {
        int consumerIdx = getConsumerIdx(keyword);
        checkStatus(consumerIdx);
        insertQueue(consumerIdx, keyword);
    }

    private LinkedBlockingQueue<String> checkStatus(int consumerIdx) {
        if (consumers[consumerIdx] == null || !consumers[consumerIdx].isAlive()) {
            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

            queues[consumerIdx] = queue;
            consumers[consumerIdx] = new Consumer(keywordCountJpaRepository, queue);
            consumers[consumerIdx].start();
        }
        return queues[consumerIdx];
    }

    private void insertQueue(int consumerIdx, String keyword) {
        try {
            queues[consumerIdx].put(keyword);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getConsumerIdx(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        int num = bytes.length == 0 ? 0 : bytes[0];
        num = num < 0 ? num * -1 : num;
        return num % queues.length;
    }
}
