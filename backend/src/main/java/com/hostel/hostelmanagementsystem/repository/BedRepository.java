package com.hostel.hostelmanagementsystem.repository;

import com.hostel.hostelmanagementsystem.model.Bed;
import com.hostel.hostelmanagementsystem.model.Room;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BedRepository extends JpaRepository<Bed, String> {

    /**
     * Finds a bed by its unique ID.
     * <p>
     * This method overrides the default `findById` provided by JpaRepository
     * and is annotated with @NonNull to emphasize that the input and output
     * must not be null. It returns an Optional that either contains the bed
     * if found, or is empty if not found.
     *
     * @param bedId the unique ID of the bed to look for (must not be null)
     * @return an Optional containing the Bed if found, or empty otherwise
     */
    @Override
    @NonNull
    Optional<Bed> findById(@NonNull String bedId);

    /**
     * Retrieves a list of beds associated with a specific Room entity.
     * <p>
     * Purpose:
     * This method allows querying all beds that are linked to a particular Room object.
     * It is useful when needing to fetch all beds within a specific room instance.
     * <p>
     * Flow:
     * - Accepts a Room object as a parameter.
     * - Queries the database for all Bed entities associated with the provided Room.
     * - Returns a list of beds related to that Room.
     *
     * @param room the Room entity to find beds for
     * @return a list of Bed entities assigned to the specified Room
     */
    List<Bed> findByRoom(Room room);

    /**
     * Retrieves a list of beds associated with a specific room ID.
     * <p>
     * Purpose:
     * This method allows querying all beds that belong to a room identified by its unique room ID.
     * It is useful for retrieving bed information when only the room ID string is available.
     * <p>
     * Flow:
     * - Accepts a room ID as a parameter.
     * - Performs a query to retrieve all beds that have the specified room ID.
     * - Returns a list of beds linked to the given room ID.
     *
     * @param roomId the unique identifier of the room
     * @return a list of Bed entities belonging to the room with the given ID
     */
    List<Bed> findByRoom_RoomId(@NonNull String roomId);

    @Override
    @NonNull
    List<Bed> findAll();

    /**
     * Counts the number of beds associated with a specific room based on the room's unique ID.
     * <p>
     * Purpose:
     * This method allows the system to determine how many beds are currently assigned to a given room,
     * based on the provided room ID. It is useful for checking room capacity and ensuring that a room
     * does not exceed its maximum capacity.
     * <p>
     * Flow:
     * - The room ID is passed as a parameter.
     * - The method queries the database to count the number of beds associated with the given room ID.
     * - The count is returned as a `long` value, indicating the number of beds in that room.
     *
     * @param roomId the unique ID of the room whose bed count is to be retrieved
     * @return the number of beds in the room as a `long`
     */
    long countByRoom_RoomId(String roomId);

    /**
     * Saves a new or updated Bed entity to the database.
     * <p>
     * Purpose:
     * This method is used to persist a `Bed` entity to the database. It is used for both creating new
     * beds and updating existing ones. If the bed already exists in the database (based on its ID), it
     * will update the existing entry; otherwise, it will insert a new one.
     * <p>
     * Flow:
     * - The `Bed` object is passed as a parameter, which includes information such as the bed ID, number,
     *   and the associated room.
     * - The method delegates the operation to the `bedRepository.save()` function, which performs the
     *   database operation.
     * - The bed entity is returned after being saved or updated in the database.
     *
     * @param bed the `Bed` object to be saved or updated
     * @return the saved or updated `Bed` entity
     */
    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    Bed save(@NonNull Bed bed);

    /**
     * Deletes a bed from the database using its unique bed ID.
     * <p>
     * Purpose:
     * This method is intended for removing a specific bed record from the system
     * by identifying it with its unique bed ID. It is commonly used when a bed
     * is no longer available or needs to be removed for administrative purposes.
     * <p>
     * Flow:
     * - Accepts the bed ID as a parameter.
     * - Performs a delete operation on the bed associated with the provided ID.
     * - If no bed is found with the given ID, no changes are made to the database.
     *
     * @param bedId the unique identifier of the bed to delete
     */
   void deleteByBedId(String bedId);


    }
