# 🧵 Java Job Queue System

A multithreaded, priority-based job queue system built with Java. Designed to simulate a production-ready backend task processing engine with extensibility for distributed processing, database persistence, and horizontal scaling.

This project implements a thread-safe job queue where producers can submit jobs with varying priorities and consumers (workers) process these jobs concurrently. Inspired by real-world queueing systems like Celery, Sidekiq, and AWS SQS.

It uses Java’s `PriorityBlockingQueue`, `ExecutorService`, and `ConcurrentHashMap` to provide safe concurrent access, background processing, and job status tracking. An optional REST API is exposed via Spring Boot to allow external clients to submit jobs and check their statuses.

## Features

- ✅ Submit jobs with customizable priority levels.
- ✅ FIFO ordering within the same priority level.
- ✅ Thread-safe job queue using `PriorityBlockingQueue`.
- ✅ Multiple worker threads using `ExecutorService`.
- ✅ Job lifecycle states: `PENDING`, `RUNNING`, `COMPLETED`, `FAILED`.
- ✅ RESTful API (Spring Boot) for submitting and tracking jobs.
- ✅ In-memory storage with `ConcurrentHashMap` to monitor job status.
- ✅ Clean shutdown with graceful worker termination.

## Planned Optimizations

- 🧠 **Persistent Storage**: Replace in-memory store with PostgreSQL, MongoDB, or Redis for durability.
- 🔁 **Retry Mechanism**: Automatic retries for failed jobs with exponential backoff.
- 📦 **Job Result Tracking**: Store and return job outputs, results, or failure reasons.
- 📈 **Dashboard Metrics**: Visualize queue depth, processing rate, error rates, and worker load.
- 🧩 **Pluggable Job Handlers**: Add support for dynamic job types using a registry or strategy pattern.
- 🔐 **API Authentication**: Secure endpoints using Basic Auth, OAuth2, or JWT.
- ⚙️ **Auto-Scaling Workers**: Dynamically scale worker threads based on queue length.
- 🔄 **Queue Persistence**: Store queued jobs across app restarts using file-based or DB-backed queue.
- 🔌 **Distributed Processing**: Add Kafka or RabbitMQ integration for distributed job consumption.

## Tech Stack

- Java 17+
- Spring Boot (REST API)
- Java Concurrency Utilities (`PriorityBlockingQueue`, `ExecutorService`)
- Lombok (code brevity)
- Maven or Gradle (build tools)
- JUnit + Mockito (testing)
- PostgreSQL / Redis (future storage layer)

## How It Works

1. A job is submitted via the REST API or directly to the service.
2. The job is added to a priority queue based on its priority value.
3. A pool of worker threads continuously pulls from the queue and executes jobs.
4. Job status is updated in a concurrent map for tracking.
5. Completed or failed jobs are stored for future inspection.

## 📁 Folder Structure

```
java-job-queue/
├── broker/                     # Spring Boot REST API (Job Broker)
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── joseph/
│   │                   └── jobqueue/
│   │                       ├── controller/     # REST Controllers
│   │                       ├── model/          # Job model classes
│   │                       ├── service/        # Job queue logic
│   │                       └── BrokerApplication.java
│   └── pom.xml
│
├── worker/                     # Worker that processes jobs
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── joseph/
│   │                   └── jobqueue/
│   │                       └── JobWorker.java  # Main worker class
│   └── pom.xml
│
├── .gitignore
├── README.md
└── pom.xml                     # Parent Maven project file
```


## Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle

# 🛠️ Java Job Queue System

## 🚀 Clone and Build

```bash
git clone https://github.com/your-username/java-job-queue.git
cd java-job-queue
./mvnw clean install
```

> Requires JDK 17+. Maven wrapper (`./mvnw`) is included.

## ▶️ Run the Broker API

```bash
cd broker
./mvnw spring-boot:run
```

Starts the REST API server at `http://localhost:8080`.

## 🧑‍🔧 Run the Worker

In a **new terminal**:

```bash
cd worker
./mvnw compile exec:java -Dexec.mainClass="com.joseph.jobqueue.JobWorker"
```

This worker polls the broker for jobs every few seconds and processes them.

## 📬 Example Usage

Enqueue a job:

```bash
curl -X POST http://localhost:8080/jobs
```

Sample console output:

```
👷 Worker started... polling for jobs...
⚙️  Processing job: 72bfbfca-...
✅ Completed job: 72bfbfca-...
```

List all jobs:

```bash
curl http://localhost:8080/jobs
```

## 📡 API Endpoints (Broker)

| Method | Endpoint        | Description               |
|--------|------------------|---------------------------|
| POST   | `/jobs`          | Create and enqueue a job  |
| GET    | `/jobs`          | Get all jobs              |
| GET    | `/jobs/{id}`     | Get a specific job by ID  |
| PUT    | `/jobs/{id}`     | *(Planned)* Update status |

---

## 🧩 Planned Features and Optimizations

- ✅ Basic in-memory job queue (PriorityBlockingQueue + Map)
- 🔄 Support for concurrent workers (multi-threaded or distributed)
- 💾 Persistence with PostgreSQL, MongoDB, or Redis
- 🔁 Retry logic for failed jobs
- 🧠 Job prioritization with dynamic weight scoring
- 📊 Monitoring dashboard (React + Spring Boot actuator)
- 🔐 Secure APIs with JWT auth
- 🐳 Docker + Docker Compose setup
- ☁️ Deploy to cloud platforms (Render, Fly.io, GCP)

---

## 👨‍💻 Author

**Joseph Boadi**  
[GitHub](https://github.com/boadijoseph7177) · [LinkedIn](https://www.linkedin.com/in/josephboadi7)

---

## 📄 License

MIT License

