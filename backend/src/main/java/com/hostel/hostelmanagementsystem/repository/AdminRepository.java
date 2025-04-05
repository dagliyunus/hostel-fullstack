package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    /**
     * Retrieves an Admin entity based on the provided username.
     *
     * <p>This method is used for authentication and user lookup purposes. It returns an Optional
     * that contains the Admin object if found, or is empty if no matching username exists.</p>
     *
     * @param username the unique username of the admin to be retrieved
     * @return an Optional containing the Admin if found, otherwise empty
     */
    Optional<Admin> findByUsername(String username);
}
