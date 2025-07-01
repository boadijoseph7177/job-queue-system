package com.joseph.jobqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateJobStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        Job job = jobQueueService.getJob(id);
        if (job == null) {
            return ResponseEntity.status(404).body("Job with ID " + id + " not found.");
        }
        String newStatus = body.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest().body("Missing status in request body.");
        }
        try {
            job.setStatus(JobStatus.valueOf(newStatus));
            // If your Job is immutable, update it in your service/store here
            return ResponseEntity.ok(job);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + newStatus);
        }
    }
}
