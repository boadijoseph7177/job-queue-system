package com.joseph.jobqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobBrokerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobBrokerApplication.class, args);
        System.out.println("Job Broker Application is running...");
    }
}