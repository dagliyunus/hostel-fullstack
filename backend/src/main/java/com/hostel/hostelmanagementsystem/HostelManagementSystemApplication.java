package com.hostel.hostelmanagementsystem;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the Hostel Management System Spring Boot application.
 *
 * <p>This class is responsible for bootstrapping the application using
 * {@link SpringApplication}. It also enables task scheduling with
 * {@link EnableScheduling} and defines a post-construction method for
 * generating a hashed password.
 */
@SpringBootApplication(scanBasePackages = "com.hostel.hostelmanagementsystem")
@EnableScheduling
public class HostelManagementSystemApplication {

    /**
     * Generates a bcrypt hash of the default admin password upon application startup.
     *
     * <p>This method is executed after dependency injection is complete using the
     * {@link PostConstruct} annotation. It demonstrates how to generate a secure
     * hashed password using the BCrypt algorithm and logs the hash to the console.
     * <strong>Note:</strong> This is intended for development/debugging purposes only.
     */
    @PostConstruct
    public void generateAdminHash() {
        String plainPassword = "admin123";
        String hashed = BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
        System.out.println("Generated bcrypt hash: " + hashed);
    }

    public static void main(String[] args) {
        SpringApplication.run(HostelManagementSystemApplication.class, args);
    }

}
