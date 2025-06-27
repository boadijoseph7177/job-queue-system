package com.joseph.jobqueue;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class JobQueueService {
    private final PriorityBlockingQueue<Job> queue;
    private final Map<String, Job> jobstore = new ConcurrentHashMap<>();

    public JobQueueService() {
        this.queue = new PriorityBlockingQueue<>(
                10,
                (job1, job2) -> {
                    int cmp = Integer.compare(job2.getPriority(), job1.getPriority()); // higher priority first
                    if (cmp == 0) {
                        return job1.getCreatedAt().compareTo(job2.getCreatedAt()); // earlier createdAt first
                    }
                    return cmp;
                });
    }

    public void submitJob(Job job) {
        jobstore.put(job.getId(), job);
        System.out.println("Job submitted: " + job);
        queue.add(job);
    }

    public Job takeJob() throws InterruptedException {
        return queue.take();
    }

    public int getQueueSize() {
        return queue.size();
    }

    public Job getJob(String id) {
        return jobstore.get(id);
    }

    public Collection<Job> getAllJobs() {
        return jobstore.values();
    }
}