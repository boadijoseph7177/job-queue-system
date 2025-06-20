package com.joseph.jobqueue;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder

public class Job implements Comparable<Job> {
    private String id;
    private String type;
    private String payload;
    private JobStatus status;
    private int priority;
    private Instant createdAt;

    @Override
    public int compareTo(Job other) {
        // Compare by priority first, then by creation time
        int priorityComparison = Integer.compare(other.priority, this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return this.createdAt.compareTo(other.createdAt);
    }
}