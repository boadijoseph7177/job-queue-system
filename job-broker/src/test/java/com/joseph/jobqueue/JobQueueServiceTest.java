package com.joseph.jobqueue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JobQueueServiceTest {

    @Test
    public void testSubmitAndTakeJob() throws InterruptedException {
        JobQueueService queueService = new JobQueueService();

        Job job = Job.builder()
                .id("job-001")
                .type("sendEmail")
                .payload("email data")
                .priority(5)
                .status(JobStatus.QUEUED)
                .createdAt(java.time.Instant.now())
                .build();

        queueService.submitJob(job);

        Job takenJob = queueService.takeJob();

        assertEquals(job.getId(), takenJob.getId());
        assertEquals(job.getPriority(), takenJob.getPriority());
    }
}
