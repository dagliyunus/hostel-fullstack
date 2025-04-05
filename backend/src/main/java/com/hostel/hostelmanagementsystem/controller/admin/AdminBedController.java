package com.hostel.hostelmanagementsystem.controller.admin;

import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.service.admin.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/manageBed")
public class AdminBedController {

    private final BedService bedService;

    /**
     * Constructs a new AdminBedController with the specified BedService.
     * <p>
     * Purpose:
     * This constructor is used to inject the `BedService` dependency into the `AdminBedController`.
     * It ensures that the controller has access to the service layer, which contains the business logic
     * for managing beds in the hostel system, such as adding, updating, and fetching bed details.
     * <p>
     * Flow:
     * - The `BedService` bean is injected automatically by Spring during the application startup.
     * - The injected `BedService` instance is assigned to the `bedService` field of the controller.
     * - The controller uses this service to handle requests related to beds, such as creating new beds,
     *   retrieving bed details, and managing bed associations with rooms.
     *
     * @param bedService the service used for bed-related operations, such as creating, updating, and fetching beds
     */
    @Autowired
    public AdminBedController(BedService bedService) {
        this.bedService = bedService;
    }


    /**
     * Handles GET requests to fetch a Bed by its ID.
     * <p>
     * Request Format(Postman):
     * /api/beds/byId?bed_id=BED_ID
     * <p>
     * Purpose:
     * This method allows the admin to retrieve details of a specific bed
     * using its unique identifier (bed_id) passed as a query parameter.
     * <p>
     * Flow:
     * - The bedId is received from the request query parameter.
     * - The controller delegates the lookup to the BedService.
     * - BedService internally uses BedRepository to fetch data from the database.
     * - If the Bed is found, a 200 OK response is returned with the Bed data.
     * - If not found, a 404 Not Found response is returned with an error message.
     */
    @GetMapping("/byId")
    public ResponseEntity<?> getBedById(@RequestParam("bed_id") String bedId) {
        Optional<Bed> bed = bedService.findBedById(bedId);
        if (bed.isPresent()) {
            return ResponseEntity.ok(bed.get());
        }else {
            return ResponseEntity
                    .status(404)
                    .body("Bed not found");
        }
    }

    /**
     * Handles GET requests to retrieve all beds in the system.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/manageBed/getAllBeds
     * <p>
     * Purpose:
     * This method enables the admin to view a list of all beds, useful for
     * administrative oversight and inventory checks.
     * <p>
     * Flow:
     * - The controller calls BedService to fetch all beds from the database.
     * - If the list is not empty, it returns a 200 OK response with the bed data.
     * - If no beds are found, it returns a 404 Not Found with an error message.
     *
     * @return ResponseEntity containing a list of Bed objects or an error message.
     */
    @GetMapping("/getAllBeds")
    public ResponseEntity<?> getAllBeds() {
        List<Bed> beds = bedService.getAllBeds();
        if( !beds.isEmpty()) {
            return ResponseEntity.ok(beds);
        }else{
            return ResponseEntity
                    .status(404)
                    .body("Beds not found");
        }
    }

    /**
     * Handles GET requests to fetch all beds assigned to a specific room by its ID.
     * <p>
     * Request Format:
     * GET /api/admin/dashboard/manageBed/getBedsByRoomId?room_id=ROOM_ID
     * <p>
     * Purpose:
     * This method allows the admin to retrieve all beds that belong to a specific room,
     * enabling room-wise bed tracking.
     * <p>
     * Flow:
     * - The room ID is provided as a request parameter.
     * - BedService is called to retrieve all beds associated with the given room ID.
     * - Returns a 200 OK with the list of beds, or a 404 Not Found if no beds are present.
     *
     * @param roomId the ID of the room whose beds should be fetched
     * @return ResponseEntity containing list of Bed objects or an error message
     */
    @GetMapping("getBedsByRoomId")
    public ResponseEntity<?> getBedsByRoomId(@RequestParam("room_id") String roomId) {
        try {
            List<Bed> beds = bedService.getBedsByRoomId(roomId);
            if (beds.isEmpty()) {
                return ResponseEntity.status(404).body("No beds found for the given room ID.");
            }
            return ResponseEntity.ok(beds);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching beds for the room.");
        }
    }

    /**
     * Handles POST requests to add a new Bed to a specified room.
     * <p>
     * Request Format(Postman):
     * POST /api/admin/dashboard/manageBed/addBed?room_id=ROOM_ID
     * <p>
     * Purpose:
     * This method allows the admin to add a new bed to a room using the room's unique identifier (room_id)
     * and the bed's details (bedId and bedNumber) passed in the request body.
     * <p>
     * Flow:
     * - The roomId is received from the request query parameter.
     * - The new bed details are received from the request body.
     * - The controller delegates the bed creation to the BedService.
     * - BedService checks if the room exists and associates the new bed with the room.
     * - If the bed is successfully created, a 200 OK response is returned with the new Bed data.
     * - If the room is not found, or if the bed creation fails, a 404 Not Found response is returned
     *   with an error message.
     * <p>
     * Response Codes:
     * - 200 OK: Successfully created the bed and returned the bed object.
     * - 404 Not Found: Room with the given ID not found, or bed creation failed.
     * Example Error Response:
     * 404 Not Found
     * "Room with ID R3 not found, or bed could not be created."
     */
    @PostMapping("/addBed")
    public ResponseEntity<?> addBed(@RequestParam("room_id") String roomId, @RequestBody Bed newBed) {
        try {
            Bed savedBed = bedService.createBedToRoom(roomId, newBed);
            return ResponseEntity.ok(savedBed);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body("Room with ID " + roomId + " not found, or bed could not be created.");
        }
    }

    /**
     * Handles DELETE requests to remove a specific bed by its ID.
     * <p>
     * Request Format:
     * DELETE /api/admin/dashboard/manageBed/deleteBed?bed_id=BED_ID
     * <p>
     * Purpose:
     * This method allows the admin to delete a bed by providing its unique identifier.
     * Useful when a bed is no longer available or needs to be replaced.
     * <p>
     * Flow:
     * - The bed ID is extracted from the request.
     * - BedService checks for the existence of the bed.
     * - If found, the bed is deleted and a success message is returned.
     * - If not found, returns a 404 Not Found with an error message.
     *
     * @param bedId the ID of the bed to be deleted
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/deleteBed")
    public ResponseEntity<?> deleteBed(@RequestParam("bed_id") String bedId) {
        Optional<Bed> bed = bedService.findBedById(bedId);
        if (bed.isPresent()) {
            bedService.deleteBed(bedId);
            return ResponseEntity.ok("Bed deleted successfully.");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("Bed not found");
        }
    }
}
