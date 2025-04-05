package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.Room;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    /**
     * Retrieves a Room entity from the database using its unique roomId.
     * <p>
     * This method is used to fetch a specific Room based on its identifier,
     * typically from a controller or service layer where room-related details
     * (like beds, capacity, floor, etc.) are needed. It returns an Optional,
     * which allows handling the case where a Room with the given roomId may not exist.
     *
     * @param roomId the unique identifier of the Room to find
     * @return an Optional containing the Room if found, otherwise an empty Optional
     */
    Optional<Room> findByRoomId(String roomId);

    /**
     * Retrieves a Room entity based on its room number.
     * <p>
     * This method is useful when you need to locate a Room using the
     * human-readable room number (e.g., "101", "A203") instead of its
     * internal roomId. It is commonly used for display logic, search features,
     * or validations where room number is used as input.
     *
     * @param roomNumber the room number to search for
     * @return an Optional containing the Room if found, or an empty Optional if not
     */
    Optional<Room> findByRoomNumber(String roomNumber);

    @Override
    @NonNull
    List<Room> findAll();

    /**
     * Saves a new Room to the database.
     * If the Room has a null ID, a new record is inserted.
     * If the Room has an existing ID, it will be updated.
     *
     * @param room the Room entity to save
     * @return the persisted Room entity
     */
    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    Room save(Room room);

    /**
     * Deletes a room by its roomId.
     * Spring Data JPA will generate the query automatically.
     * Equivalent SQL: DELETE FROM room WHERE room_id = ?
     *
     * @param roomId the ID of the room to delete
     */
    void deleteByRoomId(String roomId);


}
