This document provides a structured overview of the annotations commonly used in the service layer of the project. These annotations help with service registration, dependency injection, and task scheduling. The following annotations are essential to the architecture and functionality of the service layer.

⸻

🧩 Spring Framework Annotations

@Service

Marks a class as a service component in the Spring context. Classes annotated with @Service are automatically detected during component scanning and registered as Spring beans. This is appropriate for business logic and service layer implementations.

Example:

@Service
public class BookingService {
// business logic
}


⸻

@Autowired

Used for automatic dependency injection. It tells Spring to resolve and inject the annotated dependency at runtime. It can be applied to constructors, fields, or setter methods.

Example:

@Autowired
private BookingRepository bookingRepository;

@Scheduled

Enables method-level scheduling. Methods annotated with @Scheduled are executed at fixed intervals or cron expressions. This is typically used for background jobs, automated notifications, or caching tasks.

Example:

@Scheduled(fixedRate = 10000) // runs every 10 seconds
public void pollLatestBooking() {
// polling logic
}