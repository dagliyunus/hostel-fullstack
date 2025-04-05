package com.hostel.hostelmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {

    /**
     * Unique identifier for the room.
     * Mapped to the 'room_id' column in the database.
     * Acts as the primary key for the Room entity.
     */
    @Id
    @Column(name = "room_id", nullable = false, length = 50)
    private String roomId;

    /**
     * Human-readable identifier for the room (e.g., "101", "A2").
     * Mapped to the 'room_number' column.
     * Must be unique and not null.
     */
    @Column(name = "room_number",nullable = false, unique = true, length = 10)
    private String roomNumber;

    /**
     * Indicates the floor number the room is located on.
     * Mapped to the 'floor' column.
     */
    @Column(name = "floor")
    private Integer floor;

    /**
     * The maximum number of people allowed in the room.
     * Mapped to the 'capacity' column.
     * Cannot be null.
     */
    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    /**
     * One-to-many relationship with the Bed entity.
     * Represents the list of beds associated with this room.
     * Mapped by the 'room' field in the Bed entity.
     * Cascade type ALL ensures bed updates propagate with room updates.
     * Fetch type EAGER loads all beds with the room.
     * @JsonManagedReference used to manage bi-directional JSON serialization.
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Bed> beds;

}
