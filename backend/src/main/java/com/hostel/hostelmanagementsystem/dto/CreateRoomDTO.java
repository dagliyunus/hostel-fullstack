package com.hostel.hostelmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new room.
 * <p>
 * This class is used to encapsulate the data required to create a new room in the system,
 * including its identifier, number, capacity, floor, and the number of beds.
 */
@Getter
@Setter
public class CreateRoomDTO {
    private String roomId;
    private String roomNumber;
    private Integer capacity;
    private Integer floor;
    private int bedCount;

    public CreateRoomDTO(String roomId, String roomNumber, Integer capacity, Integer floor, int bedCount) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.floor = floor;
        this.bedCount = bedCount;
    }
}
