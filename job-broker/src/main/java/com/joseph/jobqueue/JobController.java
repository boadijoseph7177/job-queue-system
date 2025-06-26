package com.joseph.jobqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobQueueService jobQueueService;

    // In-memory status score, will be replaced by a database in production
    private Map<String, Job> jobstore = new ConcurrentHashMap<>();

    @PostMapping
    public Job submitJob(@RequestBody Job jobRequest) {
        String id = UUID.randomUUID().toString();
        Job job = Job.builder()
                .id(id)
                .type(jobRequest.getType())
                .payload(jobRequest.getPayload())
                .status(JobStatus.QUEUED)
                .priority(jobRequest.getPriority())
                .createdAt(Instant.now())
                .build();

        jobQueueService.submitJob(job);
        jobstore.put(id, job);
        System.out.println("Job submitted: " + job);
        return job;
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable String id) {

        return jobstore.get(id);
    }

}