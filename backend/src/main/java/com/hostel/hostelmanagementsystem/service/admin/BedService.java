package com.hostel.hostelmanagementsystem.service.admin;

import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Room;
import com.hostel.hostelmanagementsystem.repository.BedRepository;
import com.hostel.hostelmanagementsystem.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;


    /**
     * Constructs a new instance of BedService, initializing it with the specified repositories.
     * <p>
     * Purpose:
     * This constructor is used to inject the required dependencies for the BedService. It ensures that
     * both the `BedRepository` and `RoomRepository` are available for the service layer to perform operations
     * related to the `Bed` and `Room` entities.
     * <p>
     * Flow:
     * - The constructor accepts two parameters: `bedRepository` and `roomRepository`.
     * - `bedRepository` is used to perform database operations related to the `Bed` entity.
     * - `roomRepository` is used to perform database operations related to the `Room` entity.
     * - These repositories are injected by Spring's dependency injection mechanism, ensuring that
     *   the service layer has the necessary components to interact with the database.
     *
     * @param bedRepository the repository used to access bed data
     * @param roomRepository the repository used to access room data
     */
    @Autowired
    public BedService(BedRepository bedRepository, RoomRepository roomRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Retrieves a bed by its unique bed ID.
     * Returns an Optional that contains the Bed if found, or is empty if no match exists.
     * Delegates the call to the BedRepository's findById method.
     *
     * @param bedId the ID of the bed to retrieve
     * @return an Optional containing the Bed, or empty if not found
     */
    public Optional<Bed> findBedById(String bedId) {
        return bedRepository.findById(bedId);
    }

    /**
     * Retrieves all bed records from the database.
     * <p>
     * Purpose:
     * This method provides a list of all beds stored in the system. It is commonly used for
     * administrative views where all bed entries need to be displayed or processed.
     * <p>
     * Flow:
     * - Delegates the call to {@link BedRepository#findAll()} to fetch all bed records.
     * - Returns the complete list as provided by the repository.
     *
     * @return a list of all {@link Bed} entities stored in the database
     */
    public List<Bed> getAllBeds(){
        return bedRepository.findAll();
    }

    public List<Bed> getBedsByRoomId( String roomId) {
        return bedRepository.findByRoom_RoomId(roomId);
    }

    /**
     * Creates and saves a new Bed entity to a specified Room.
     * <p>
     * Purpose:
     * This method allows for the creation of a new bed in a specific room by checking the room's
     * current bed count and ensuring that it has available space for more beds. The bed details are
     * passed in the request, and the room is set for the bed before saving it to the database.
     * <p>
     * Flow:
     * - The roomId is used to find the room to which the bed is to be added.
     * - If the room is not found, an IllegalArgumentException is thrown with a message indicating
     *   the room was not found.
     * - The current bed count of the room is retrieved using `bedRepository.countByRoom_RoomId()`.
     * - If the room has reached its capacity (i.e., the number of beds is greater than or equal to the room's capacity),
     *   an IllegalArgumentException is thrown with a message indicating the room is at full capacity.
     * - If space is available, the new bed's room is set to the found room.
     * - The new bed is saved to the database using `bedRepository.save()`.
     * - The saved bed is returned to the caller.
     *
     * @param roomId the ID of the room to which the bed is being added
     * @param newBed the bed entity with details to be saved
     * @return the saved Bed entity with the assigned room
     * @throws IllegalArgumentException if the room is not found or if the room is already at full capacity
     */
    public Bed createBedToRoom(String roomId, Bed newBed) {
        // Finding the room by its ID
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room Not found with id: " + roomId));

        // here, checking if the room has space for additional beds based on its capacity
        long currentBedCount = bedRepository.countByRoom_RoomId(roomId);
        if (currentBedCount >= room.getCapacity()) {
            throw new IllegalArgumentException("Room is already at full capacity.");
        }

        // Setting the room for the new bed
        newBed.setRoom(room);

        // Save and return the new bed
        return bedRepository.save(newBed);
    }

    /**
     * Deletes a bed from the system using its unique bed ID.
     * <p>
     * Purpose:
     * This method is used by administrators to permanently remove a bed from the database
     * by specifying its unique identifier. It ensures that the deletion is wrapped in a transaction
     * to maintain data consistency.
     * <p>
     * Flow:
     * - Accepts a bed ID as a parameter.
     * - Calls the {@code deleteByBedId()} method from the {@link BedRepository} to perform the deletion.
     * - Immediately flushes the persistence context to the database to enforce execution.
     * <p>
     * Note:
     * The method is annotated with {@code @Transactional} to ensure that the delete and flush
     * operations are executed in a single transaction.
     *
     * @param bedId the unique identifier of the bed to be deleted
     */
    @Transactional
    public void deleteBed(String bedId) {
        bedRepository.deleteByBedId(bedId);
        bedRepository.flush();
    }
}
