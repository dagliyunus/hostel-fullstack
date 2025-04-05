This document outlines the key annotations used within the repository layer of the HostelManagementSystem project. These annotations are primarily related to Spring Data JPA and Lombok integration. Their purpose is to support repository definitions, query customization, and type-safety, while reducing boilerplate.

⸻

🧩 Spring Data JPA & Repository Annotations

@Repository

Indicates that the interface or class is a repository component in the Spring context. This enables exception translation and dependency injection.

Example:

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> { ... }


⸻

@Override

Specifies that a method is overriding a method from a superclass or interface. Commonly used in custom repository methods that extend standard JPA interfaces.

Example:

@Override
public Optional<Customer> findById(@NonNull String customerId) { ... }


⸻

@NonNull

Ensures that the parameter or return value is not null. This is used to enforce null-safety, especially in method parameters or return types, and it enhances compile-time checks.

Example:

@Override
@NonNull
List<Booking> findAll();


⸻

@Query

Allows defining custom SQL or JPQL queries directly on repository methods. This is useful when the method name conventions are insufficient or for performance-optimized queries.

Example:

@Query("SELECT b FROM Booking b JOIN FETCH b.room r WHERE b.bookingId = :bookingId")
Optional<Booking> findBookingDetailsById(@Param("bookingId") String bookingId);

@SuppressWarnings("unchecked")

Suppresses compiler warnings related to unchecked operations, especially when using raw types or generic casting. Use this only when absolutely necessary and when the operation is type-safe.

Example:

@Override
@SuppressWarnings("unchecked")
Customer save(Customer customer);