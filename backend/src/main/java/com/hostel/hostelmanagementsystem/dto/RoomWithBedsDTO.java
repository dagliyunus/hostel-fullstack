package com.hostel.hostelmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a Room along with its associated Beds.
 *
 * This DTO is specifically used in admin-level views where detailed room data,
 * including its beds, is required—such as in the "Manage Room" tab of the Admin Dashboard.
 *
 * This class aggregates both room-level metadata and a list of simplified `BedDTO` objects.
 * It supports frontend display requirements like showing room info and associated bed numbers in popups.
 *
 * Lombok annotations (@Getter and @Setter) automatically generate getters and setters for all fields.
 */
@Getter
@Setter
public class RoomWithBedsDTO {


    private String roomId;
    private String roomNumber;
    private int capacity;
    private int floor;
    private List<BedDTO> beds;

    public RoomWithBedsDTO(String roomId, String roomNumber, int capacity, int floor, List<BedDTO> beds) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.floor = floor;
        this.beds = beds;
    }
}
