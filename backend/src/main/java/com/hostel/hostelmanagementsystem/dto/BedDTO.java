package com.hostel.hostelmanagementsystem.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a simplified view of a Bed.
 * <p>
 * This class is used to expose bed data (only the bed number) to the client,
 * especially in contexts such as the RoomWithBedsDTO where a list of associated
 * beds must be displayed without exposing internal database IDs or relationships.
 * <p>
 * The DTO helps reduce over-fetching and simplifies the API response structure.
 * <p>
 * Lombok's @Getter and @Setter annotations auto-generate standard getters and setters.
 */

@Getter
@Setter
public class BedDTO {

    private String bedNumber;

    public BedDTO(String bedNumber) {
        this.bedNumber = bedNumber;
    }
}
