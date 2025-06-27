package com.joseph.jobqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobQueueService jobQueueService;

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
        System.out.println("Job submitted: " + job);
        return job;
    }

    @GetMapping
    public Collection<Job> getAllJobs() {
        return jobQueueService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(@PathVariable String id) {
        Job job = jobQueueService.getJob(id);
        if (job == null) {
            return ResponseEntity.status(404).body("Job with ID " + id + " not found.");
        }
        return ResponseEntity.ok(job);
    }
}