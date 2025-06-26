package com.joseph.jobqueue;

import org.springframework.stereotype.Service;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class JobQueueService {
    private final PriorityBlockingQueue<Job> queue;

    public JobQueueService() {
        this.queue = new PriorityBlockingQueue<>();
    }

    public void submitJob(Job job) {
        queue.add(job);
    }

    public Job takeJob() throws InterruptedException {
        return queue.take();
    }

    public int getQueueSize() {
        return queue.size();
    }
}