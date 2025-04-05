package com.hostel.hostelmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Bed")
public class Bed {

    /**
     * Primary key of the Bed entity.
     * Maps to the 'bed_id' column in the Bed table.
     * Uniquely identifies each bed in the system.
     */
    @Id
    @Column(name = "bed_id")
    private String bedId;

    /**
     * Unique identifier (number) for the bed within a room.
     * This value is required (non-null) and must be unique.
     * Maps to the 'bed_number' column in the Bed table.
     */
    @Column(name = "bed_number",unique = true, nullable = false, length = 10)
    private String bedNumber;


    /**
     * Defines a many-to-one relationship between Bed and Room entities.
     * Each Bed is associated with exactly one Room, indicated by the foreign key 'room_id'.
     * <p>
     * - @ManyToOne: Specifies the many-to-one relationship.
     * - fetch = FetchType.EAGER: Loads the Room entity eagerly whenever a Bed is fetched.
     * - optional = false: Enforces that every Bed must be assigned to a Room (non-nullable).
     * - @JoinColumn: Maps this association to the 'room_id' column in the Bed table.
     * - @JsonBackReference: Prevents infinite recursion during JSON serialization
     *   by marking this side of the relationship as the back reference.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private Room room;

}
