package com.hostel.hostelmanagementsystem.controller.admin;

import com.hostel.hostelmanagementsystem.dto.CreateRoomDTO;
import com.hostel.hostelmanagementsystem.dto.RoomWithBedsDTO;
import com.hostel.hostelmanagementsystem.model.Room;
import com.hostel.hostelmanagementsystem.service.admin.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/dashboard/manageRoom")
public class AdminRoomController {

    private final RoomService roomService;

    @Autowired
    public AdminRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Retrieves a room by its unique room ID.
     * <p>
     * Purpose: This endpoint is used by the admin to fetch detailed information
     * about a specific room using its unique room ID as a query parameter.
     * <p>
     * Request Format(Postman):
     * GET /api/rooms/byId?room_id=ROOM_ID
     * <p>
     * Flow:
     * 1. Accepts the room_id parameter from the request.
     * 2. Passes it to the service layer via roomService.getRoomById()
     * 3. If the room exists, responds with HTTP 200 and the room data.
     * 4. If not found, responds with HTTP 404 and an error message.
     * <p>
     * Response:
     * - 200 OK: Room was found and returned successfully.
     * - 404 Not Found: No room exists with the provided ID.
     *
     * @param roomId the ID of the room to retrieve
     * @return ResponseEntity containing the room or an error message
     */
    @GetMapping("/byId")
    public ResponseEntity<?> getRoomById(@RequestParam("room_id") String roomId) {
        Optional<Room> room = roomService.getRoomById(roomId);
        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        }else {
            return ResponseEntity
                    .status(404)
                    .body("Room with id" + roomId + " not found.");
        }
    }

    /**
     * Retrieves all rooms stored in the system.
     * <p>
     * Purpose:
     * This endpoint is used to fetch the complete list of rooms, typically used for rendering the "Manage Room" section
     * of the admin dashboard where all registered rooms should be displayed in a table or list.
     * <p>
     * Flow:
     * 1. Calls the service layer method roomService.getAllRooms() to retrieve all Room entities.
     * 2. If the list is not empty, it returns HTTP 200 OK with the list of rooms.
     * 3. If the list is empty, returns HTTP 404 with a meaningful error message.
     * <p>
     * Request Format (Postman):
     * GET /api/rooms/getAllRooms
     * <p>
     * Response:
     * - 200 OK: List of Room entities.
     * - 404 Not Found: No room data available in the database.
     *
     * @return ResponseEntity containing list of Room objects or an error message
     */
    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * Retrieves detailed information of a room along with its associated beds.
     * <p>
     * Purpose:
     * This endpoint is used by the admin to fetch a room's data along with all beds assigned to that room.
     * It is typically used for rendering the "Manage Room" view in the admin dashboard with extended detail.
     * <p>
     * Request Format (Postman):
     * GET /api/rooms/getRoomWithBeds?room_id=ROOM_ID
     * <p>
     * Flow:
     * 1. Accepts a room ID via request parameter.
     * 2. Calls the service layer method roomService.getRoomWithBeds(roomId) to fetch room and beds data.
     * 3. The service returns an Optional<RoomWithBedsDTO> containing:
     *    - roomId, roomNumber, capacity, floor
     *    - List of BedDTOs with bedNumber(s)
     * 4. If room data is found, it is returned with HTTP 200 OK.
     * 5. If no room is found, a 404 NOT FOUND with an error message is returned.
     * <p>
     * Response:
     * - 200 OK: RoomWithBedsDTO containing room and its beds
     * - 404 Not Found: No room exists with the provided ID
     *
     * @param roomId the unique identifier of the room
     * @return ResponseEntity with RoomWithBedsDTO or an error message
     */
    @GetMapping("/getRoomWithBeds")
    public ResponseEntity<?> getRoomWithBeds(@RequestParam("room_id") String roomId) {
        Optional<RoomWithBedsDTO> roomWithBeds = roomService.getRoomWithBeds(roomId);
        if (roomWithBeds.isPresent()) {
            return ResponseEntity.ok(roomWithBeds.get());
        } else {
            return ResponseEntity.status(404).body("Room with id" + roomId + " not found.");
        }
    }

    /**
     * Creates a new room along with its associated beds.
     * <p>
     * Purpose:
     * This endpoint is used by the admin to create a room and assign beds to it in a single operation.
     * It is typically triggered from the "Create Room" feature on the admin dashboard.
     * <p>
     * Request Format (Postman):
     * POST /api/rooms/createRoomWithBeds
     * Body: JSON (CreateRoomDTO) including room details and a list of beds
     * <p>
     * Flow:
     * 1. Accepts a CreateRoomDTO via request body which contains room details and bed numbers.
     * 2. Calls the service layer method roomService.createRoomWithBeds(createRoomDTO).
     * 3. If the room is created successfully, returns the saved Room entity with HTTP 200 OK.
     * 4. If creation fails (e.g., data conflict, null result), returns HTTP 404 with error message.
     * <p>
     * Response:
     * - 200 OK: Room entity including the newly created room and beds
     * - 404 Not Found: Room could not be saved due to an error
     *
     * @param createRoomDTO the DTO containing room and beds creation data
     * @return ResponseEntity with Room or an error message
     */
    @PostMapping("/createRoomWithBeds")
    public ResponseEntity<?> createRoomWithBeds(@RequestBody CreateRoomDTO createRoomDTO) {
        Room savedRoom = roomService.createRoomWithBeds(createRoomDTO);
        if (savedRoom != null) {
            return ResponseEntity.ok(savedRoom);
        }else{
            return ResponseEntity
                    .status(404)
                    .body("Room can not saved");
        }
    }

    /**
     * Deletes a room based on its ID.
     * <p>
     * Purpose:
     * This endpoint allows the admin to delete a specific room. It is generally used
     * in the "Manage Room" or "Room List" section of the admin dashboard.
     * <p>
     * Request Format (Postman):
     * DELETE /api/rooms/deleteRoom?room_id=ROOM_ID
     * <p>
     * Flow:
     * 1. Accepts a room ID via request parameter.
     * 2. Calls the service layer method roomService.deleteRoom(roomId).
     * 3. If deletion is successful, returns HTTP 200 with confirmation message.
     * 4. If the room does not exist or deletion fails, returns HTTP 404 with error message.
     * <p>
     * Response:
     * - 200 OK: "Room deleted successfully."
     * - 404 Not Found: Room not found or deletion failed
     *
     * @param roomId the unique identifier of the room to be deleted
     * @return ResponseEntity with success or error message
     */
    @DeleteMapping("/deleteRoom")
    public ResponseEntity<?> deleteRoom(@RequestParam("room_id") String roomId) {
        try {
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok("Room deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Updates room information based on its ID.
     * <p>
     * Purpose:
     * This endpoint allows the admin to update the details of a room, such as room number,
     * floor, or capacity. Commonly used in the "Edit Room" view in the admin panel.
     * <p>
     * Request Format (Postman):
     * PUT /api/rooms/updateRoom?room_id=ROOM_ID
     * Body: JSON (Room) with updated fields
     * <p>
     * Flow:
     * 1. Accepts room ID via request parameter and updated Room data in request body.
     * 2. Calls the service method roomService.updateRoom(roomId, updatedRoom).
     * 3. If update is successful, returns updated Room with HTTP 200 OK.
     * 4. If room not found, returns HTTP 404 with error message.
     * <p>
     * Response:
     * - 200 OK: Updated Room entity
     * - 404 Not Found: No room exists with the provided ID
     *
     * @param roomId      the ID of the room to be updated
     * @param updatedRoom the new room data to be applied
     * @return ResponseEntity with updated Room or error message
     */
    @PutMapping("/updateRoom")
    public ResponseEntity<?> updateRoom(@RequestParam("room_id") String roomId,
                                        @RequestBody Room updatedRoom) {
        Room updated = roomService.updateRoom(roomId, updatedRoom);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity
                    .status(404)
                    .body("Room with ID " + roomId + " not found.");
        }
    }
}
