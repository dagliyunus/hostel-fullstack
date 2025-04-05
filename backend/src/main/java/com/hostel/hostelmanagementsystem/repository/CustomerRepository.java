package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Customer;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    /**
     * Retrieves a customer by their unique customer ID.
     * <p>
     * Purpose:
     * This method is used to look up a specific customer using their customer ID,
     * which is the primary key in the database.
     * It is commonly used in scenarios such as displaying customer details,
     * validating existence, or performing updates/deletes.
     * <p>
     * Flow:
     * - Accepts a non-null customerId parameter.
     * - Queries the database for a customer with that ID.
     * - Returns an Optional containing the Customer if found; otherwise, returns an empty Optional.
     *
     * @param customerId the unique identifier of the customer to retrieve
     * @return an Optional containing the Customer if found, otherwise an empty Optional
     */
    @Override
    @NonNull
    Optional<Customer> findById(@NonNull String customerId);

    /**
     * Retrieves a customer by their email address.
     * email is a unique identifier in the database (UNIQUE NOT NULL), so this query returns at most one result.
     * Using "Optional" helps safely handle the case where no customer is found,
     * which avoids "null pointer exceptions".
     *
     * @param email the email address to search for
     * @return an Optional containing the Customer if found, or empty if not
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Retrieves all customers from the database.
     * Used for admin dashboard to display complete customer list.
     * JPQL works with entity class names and fields, not table/column names.
     * So in a Spring Data JPA @Query, SELECT * FROM Customer will may cause an error.
     * So, it is better to use JPQL
     * @return List of all Customer entities
     */
    @Override
    @NonNull
    @Query("SELECT c FROM Customer c")
    List<Customer> findAll();

    /**
     * Saves a customer entity to the database.
     * <p>
     * Purpose:
     * This method is used to persist a new customer or update an existing one in the database.
     * It overrides the default JpaRepository save method and returns the saved Customer entity.
     * <p>
     * Flow:
     * - Accepts a Customer object to be saved.
     * - Persists the entity into the database.
     * - Returns the persisted Customer object.
     *
     * @param customer the Customer entity to save
     * @return the saved Customer entity
     */
    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    Customer save(Customer customer);

    /**
     * Checks whether a customer exists that is associated with the specified bed.
     * <p>
     * Purpose:
     * This method is used to determine if any customer in the system is linked to a given bed.
     * It is useful for validating bed assignments or enforcing bed uniqueness constraints.
     * <p>
     * Flow:
     * - Accepts a Bed entity.
     * - Queries the database to check if any customer is associated with that bed.
     * - Returns true if such a customer exists; otherwise, false.
     *
     * @param bed the Bed entity to check for existing associations
     * @return true if a customer exists for the given bed; false otherwise
     */
    boolean existsByBed(Bed bed);
}
