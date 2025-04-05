package com.hostel.hostelmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class Customer {

    /**
     * This tells JPA this is the primary key field
     * Unique identifier for the customer. Marked as the primary key and mapped to 'customer_id' column in the database.
     */
    @Id
    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;


    /**
     * Customer's first name.
     * Cannot be null and must not exceed 100 characters in length.
     */
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    /**
     * Customer's last name.
     * Cannot be null and must not exceed 100 characters in length.
     */
    @Column(name = "last_name",nullable = false, length = 100)
    private String lastName;

    /**
     * Customer's date of birth.
     * This field is required and cannot be null.
     */
    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Customer's email. Must be unique and not null. Max length is 100 characters.
     * Must be unique and not null — enforced here and in DB
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Customer's phone number. Optional field with a max length of 20 characters.
     * Optional contact number — max 20 characters
     */
    @Column(name = "phone",nullable = true, length = 20)
    private String phone;

    /**
     * Relationship mapping: Many customers are assigned to one room.
     * This field maps to the 'room_id' foreign key in the database.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    /**
     * Transient field for holding the room number.
     * <p>
     * This field is not persisted to the database. It is used to hold the room number
     * for display or transfer purposes in DTOs or views where the full Room entity is not required.
     */
    @Transient
    private String roomNumber;

    /**
     * Relationship mapping: Many customers are assigned to one bed.
     * This field maps to the 'bed_id' foreign key in the database.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bed_id", nullable = false)
    private Bed bed;

    /**
     * Timestamp indicating when the customer registered.
     * Automatically set to the current time when the object is created.
     * Default registration time. Matches SQL: DEFAULT CURRENT_TIMESTAMP
     * Spring Boot will auto-insert `now()` if not overridden
     */
    @Column(name = "registered_at",columnDefinition = "TIMESTAMP")
    private LocalDateTime registeredAt = LocalDateTime.now();

}
