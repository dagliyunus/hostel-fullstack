package com.hostel.hostelmanagementsystem.service.admin;

import com.hostel.hostelmanagementsystem.dto.BedDTO;
import com.hostel.hostelmanagementsystem.dto.CreateRoomDTO;
import com.hostel.hostelmanagementsystem.dto.RoomWithBedsDTO;
import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Room;
import com.hostel.hostelmanagementsystem.repository.BedRepository;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, BedRepository bedRepository) {
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    /**
     * Retrieves a Room entity from the database by its unique roomId.
     * This method is typically used when full Room entity details are required.
     *
     * @param roomId the unique identifier of the room
     * @return an Optional containing the Room if found, otherwise empty
     */
    public Optional<Room> getRoomById(String roomId){
        return roomRepository.findByRoomId((roomId));
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    /**
     * Retrieves a Room along with all its associated beds and maps the data to RoomWithBedsDTO.
     * This method is used in the admin dashboard to provide detailed room and bed structure.
     * <p>
     * It first queries the Room entity by roomId, and then uses stream().map() to transform each
     * Bed entity into a BedDTO, returning a fully populated RoomWithBedsDTO.
     *
     * @param roomId the unique identifier of the room
     * @return an Optional containing RoomWithBedsDTO if room exists, otherwise empty
     */
    public Optional<RoomWithBedsDTO> getRoomWithBeds(String roomId) {
        Optional<Room> roomOpt = roomRepository.findByRoomId(roomId);

        return roomOpt.map(room -> {
            List<BedDTO> bedDTOs = room.getBeds().stream()
                    .map(bed -> new BedDTO(bed.getBedNumber()))
                    .collect(Collectors.toList());

            return new RoomWithBedsDTO(
                    room.getRoomId(),
                    room.getRoomNumber(),
                    room.getCapacity(),
                    room.getFloor(),
                    bedDTOs
            );
        });
    }

    /**
     * Generates a new unique room ID based on existing room entries in the database.
     * <p>
     * Purpose:
     * This method ensures that each room ID is unique and sequentially increasing.
     * Room IDs follow the pattern "R{number}", where the number is the next available integer
     * after the current highest room ID in the system.
     * <p>
     * Flow:
     * - Retrieves all existing rooms from the database.
     * - Filters and parses the numeric portion of each room ID.
     * - Identifies the maximum numeric value among existing IDs.
     * - Returns a new room ID by incrementing the maximum value.
     *
     * @return a new unique room ID in the format "R{next_number}"
     */
    private String generateNextRoomId() {
        List<Room> allRooms = roomRepository.findAll();

        int max = allRooms.stream()
                .map(Room::getRoomId)
                .filter(id -> id.startsWith("R"))
                .map(id -> id.substring(1))
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return "R" + (max + 1);
    }

    /**
     * Creates a new Room entity along with a specified number of Bed entities and persists them to the database.
     * <p>
     * This method performs the following operations:
     * <ul>
     *   <li>Counts existing rooms in the database to generate a new unique room ID in the format "R{count+1}".</li>
     *   <li>Counts existing beds in the database to ensure each new bed has a globally unique bed ID and bed number.</li>
     *   <li>Creates a {@link Room} entity using the data provided in the {@link CreateRoomDTO}, including:
     *       <ul>
     *           <li>room number</li>
     *           <li>capacity</li>
     *           <li>floor</li>
     *       </ul>
     *   </li>
     *   <li>Generates a list of {@link Bed} entities with:
     *       <ul>
     *           <li>Unique bed ID in the format "B{globalBedCount+index}"</li>
     *           <li>Unique bed number in the format "BN{globalBedCount+index}"</li>
     *           <li>Each bed is associated with the room via {@code bed.setRoom(room)}</li>
     *       </ul>
     *   </li>
     *   <li>Attaches the list of beds to the room with {@code room.setBeds(beds)}</li>
     *   <li>Saves the room (and cascaded bed entities) to the database using {@code roomRepository.save(room)}</li>
     * </ul>
     *
     * The entire operation is wrapped in a {@code @Transactional} context to ensure atomicity — either all entities
     * are saved successfully, or none at all in case of failure.
     *
     * @param createRoomDTO the DTO containing input data for the new room and number of beds to create
     * @return the saved {@link Room} entity including its associated beds
     */
    @Transactional
    public Room createRoomWithBeds(CreateRoomDTO createRoomDTO) {
        long roomCount = roomRepository.count();
        long bedCount = bedRepository.count();

        String roomId = "R" + (roomCount + 1);

        Room room = new Room();
        room.setRoomId(roomId);
        room.setRoomNumber(createRoomDTO.getRoomNumber());
        room.setCapacity(createRoomDTO.getCapacity());
        room.setFloor(createRoomDTO.getFloor());

        List<Bed> beds = new ArrayList<>();
        for (int i = 1; i <= createRoomDTO.getBedCount(); i++) {
            Bed bed = new Bed();
            bed.setBedId("B" + (bedCount + i));
            bed.setBedNumber("BN" + (bedCount + i));
            bed.setRoom(room);
            beds.add(bed);
        }

        room.setBeds(beds);
        room.setRoomId(generateNextRoomId());
        return roomRepository.save(room);
    }

    /**
     * Deletes a room from the database by its unique roomId.
     * <p>
     * This method is wrapped in a transactional context to ensure data consistency and
     * to satisfy JPA's requirement that any data-modifying operation must be executed
     * within a transaction. The repository method will safely remove the room entity
     * if it exists.
     *
     * @param roomId the unique identifier of the room to be deleted
     */
    @Transactional
    public void deleteRoom(String roomId) {
        roomRepository.deleteByRoomId(roomId);
    }

    /**
     * Updates the attributes of an existing room based on the provided data.
     *
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Retrieves the existing room from the database using the given {@code roomId}.</li>
     *   <li>If no room is found, it returns {@code null}.</li>
     *   <li>If a room is found, it checks whether the new room number provided in {@code updatedRoom}
     *       is already used by another room. This ensures the {@code room_number} remains unique.</li>
     *   <li>If another room already has the same {@code room_number}, and it's not the same room being updated,
     *       the method throws an {@link IllegalArgumentException} indicating a conflict.</li>
     *   <li>If the check passes, it updates only the modifiable fields:
     *       <ul>
     *         <li>{@code roomNumber}</li>
     *         <li>{@code floor}</li>
     *         <li>{@code capacity}</li>
     *       </ul>
     *   </li>
     *   <li>Finally, the updated entity is persisted using {@code roomRepository.save()}.</li>
     * </ol>
     *
     * <p>
     * This method is wrapped with {@link jakarta.transaction.Transactional} to ensure atomicity and consistency.
     *
     * @param roomId the ID of the room to update
     * @param updatedRoom a Room object containing the updated values
     * @return the updated Room entity if the room exists, otherwise {@code null}
     */
    @Transactional
    public Room updateRoom(String roomId, Room updatedRoom) {
        Optional<Room> existingRoomOpt = roomRepository.findByRoomId(roomId);

        if (existingRoomOpt.isPresent()) {
            Room existingRoom = existingRoomOpt.get();

            Optional<Room> roomWithSameNumber = roomRepository.findByRoomNumber(updatedRoom.getRoomNumber());

            if (roomWithSameNumber.isPresent() &&
                !roomWithSameNumber.get().getRoomId().equals(roomId)) {
                throw new IllegalArgumentException("Room number is already in use by another room.");
            }

            //  Updating only allowed fields
            existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
            existingRoom.setFloor(updatedRoom.getFloor());
            existingRoom.setCapacity(updatedRoom.getCapacity());

            return roomRepository.save(existingRoom);
        } else {
            return null;
        }
    }
}
