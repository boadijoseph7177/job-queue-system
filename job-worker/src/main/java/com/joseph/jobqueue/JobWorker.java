package com.joseph.jobqueue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

public class JobWorker {

    private static final String BROKER_URL = "http://localhost:8080/jobs";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            // 1. GET all jobs from broker
            HttpRequest getJobs = HttpRequest.newBuilder()
                    .uri(URI.create(BROKER_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(getJobs, HttpResponse.BodyHandlers.ofString());
            List<Job> jobs = mapper.readValue(response.body(), new TypeReference<List<Job>>() {
            });

            // 2. Filter QUEUED jobs and pick the one with highest priority
            Optional<Job> jobOpt = jobs.stream()
                    .filter(j -> "QUEUED".equals(j.getStatus().toString()))
                    .max(Comparator.comparingInt(Job::getPriority));

            if (jobOpt.isPresent()) {
                Job job = jobOpt.get();
                String jobId = job.getId();

                // 3. Mark as RUNNING
                updateJobStatus(jobId, "RUNNING");

                // 4. Simulate execution
                System.out.println("Processing job: " + jobId);
                Thread.sleep(2000);

                // 5. Mark as COMPLETED
                updateJobStatus(jobId, "COMPLETED");
                System.out.println("Job completed: " + jobId);
            } else {
                System.out.println("No queued jobs available.");
            }

            // 6. Wait before next loop
            Thread.sleep(3000);
        }
    }

    public static void updateJobStatus(String jobId, String newStatus) throws Exception {
        String url = BROKER_URL + "/" + jobId + "/status";
        String requestBody = "{\"status\":\"" + newStatus + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
