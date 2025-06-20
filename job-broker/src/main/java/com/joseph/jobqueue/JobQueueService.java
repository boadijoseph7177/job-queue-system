package com.joseph.jobqueue;

import com.joseph.jobqueue.Job;
import java.util.concurrent.PriorityBlockingQueue;

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