import React, { useEffect, useState } from 'react';
import {addBedToRoom, deleteRoom, getAllRooms, getBedsByRoomId, updateRoom} from '../../services/AdminServices/roomService.js';
import RoomTable from '../../components/RoomTable.jsx';
import { getRoomWithBeds } from '../../services/AdminServices/roomService.js';
import { createRoomWithBeds } from '../../services/AdminServices/roomService.js';
import RoomPopup from '../../components/RoomPopup.jsx';

/**
 * React functional component for managing hostel rooms on the Admin Dashboard.
 *
 * This component:
 * - Fetches all room data from the backend when it mounts
 * - Displays the list of rooms via the <RoomTable /> component
 * - Shows a detailed popup view of a selected room, including its beds
 * - Allows admins to open a form to create new rooms (with bed count)
 *
 * State variables used:
 * - `rooms`: Holds the list of all rooms retrieved from the backend
 * - `loading`: Indicates whether data is still being fetched
 * - `selectedRoom`: Stores the room selected for viewing in the popup
 * - `showPopup`: Controls whether the RoomPopup component is shown
 * - `showCreateForm`: Toggles the display of the "Create Room" form
 * - `newRoomData`: Temporarily holds the input data for creating a new room
 */
const ManageRooms = () => {
    const [rooms, setRooms] = useState([]);
    const [beds, setBeds] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [showPopup, setShowPopup] = useState(false);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState(false);
    const [showAddBedForm, setShowAddBedForm] = useState(false);
        const [newRoomData, setNewRoomData] = useState({
        roomNumber: '',
        floor: '',
        capacity: '',
        bedCount: ''
    });
    const [updatedRoomData, setUpdatedRoomData] = useState({
        roomNumber: '',
        floor: '',
        capacity: ''
    });
    const [bedData, setBedData] = useState({
        bedId: '',
        bedNumber: '',
        roomId: '',
    });



   /**
    * Handles the action when the "View" button is clicked for a room.
    *
    * This function:
    * - Calls the backend endpoint to fetch room details along with its beds using `getRoomWithBeds`.
    * - If successful, sets the selected room data to state and triggers the RoomPopup to appear.
    * - If it fails, logs the error for debugging purposes.
    *
    * @param {string} roomId - The ID of the room to be viewed.
    */
   const onView = async (roomId) => {
        try {
            const response = await getRoomWithBeds(roomId);
            console.log("Room data fetched:", response.data);
            setSelectedRoom(response.data);
            setShowPopup(true);

        } catch (error) {
            console.error('Failed to fetch room details:', error);
        }
    };

   /**
    * Handles the "Add Room" functionality when the Add button is clicked.
    *
    * This function has two phases:
    * - First click: toggles the form display by setting `showCreateForm` to true.
    * - Second click: collects form input (`newRoomData`), sends it to the backend using `createRoomWithBeds`,
    *   and if successful, resets form state, refetches all rooms, and hides the form.
    * - Errors during submission are caught and logged, with a user alert for failure.
    */
    const onAdd = async () => {
        if (!showCreateForm) {
            setShowCreateForm(true); // First click: show the form
        } else {
            // Second click: try to submit
            try {
                await createRoomWithBeds(newRoomData);
                alert('Room created successfully!');
                setShowCreateForm(false);
                setNewRoomData({
                    roomNumber: '',
                    floor: '',
                    capacity: '',
                    bedCount: ''
                });
                const res = await getAllRooms();
                setRooms(res.data);
            } catch (error) {
                console.error('Error creating room:', error);
                alert('Failed to create room.');
            }
        }
    };

    /**
     * Deletes a room by its ID.
     * Prompts the admin for confirmation, sends a DELETE request via API,
     * and updates the local room list upon success.
     *
     * @param {string} roomId - The ID of the room to be deleted (e.g., "R1")
     */
    const onDelete = async (roomId) => {
        const confirmDelete = window.confirm(`Are you sure you want to delete Room ID: ${roomId}?`);
        if (!confirmDelete) return;

        try {
            await deleteRoom(roomId);
            alert(`Room ID ${roomId} has been successfully deleted.`);

            // Refresh the list after deletion
            const updatedList = await getAllRooms();
            setRooms(updatedList.data);
        } catch (error) {
            console.error('Error deleting room:', error);
            alert(`Failed to delete Room ID: ${roomId}. Please try again.`);
        }
    };

    /**
     * Handles the editing of a room's details in the admin dashboard.
     * This method is triggered when the admin clicks "Edit" on a room, and it toggles between showing the edit form and submitting the form data.
     *
     * Flow:
     * 1. If the edit form is not visible (`showUpdateForm` is false), the function first shows the form by setting `setShowUpdateForm(true)`.
     * 2. It finds the room to be edited by matching the `roomId` from the `rooms` list and sets the initial values in the `updatedRoomData` state.
     * 3. If the edit form is already visible, it proceeds to submit the form:
     *    - It constructs an updated room object using the `updatedRoomData` state.
     *    - Sends the updated room data to the backend via the `updateRoom` API call.
     *    - If the update is successful, it hides the form and resets the form data.
     *    - After the successful update, it fetches the latest room list by calling `getAllRooms` to refresh the list of rooms displayed in the UI.
     *
     * @param {string} roomId - The ID of the room to be updated.
     */
    const onEdit = async (roomId) => {
        if (!showUpdateForm) {
            setShowUpdateForm(true); // Show the form
            const roomToEdit = rooms.find(r => r.roomId === roomId);
            if (roomToEdit) {
                setUpdatedRoomData({
                    roomId: roomToEdit.roomId,
                    roomNumber: roomToEdit.roomNumber,
                    floor: roomToEdit.floor,
                    capacity: roomToEdit.capacity
                });
            }
        } else {
            // Submit the form
            try {
                const updatedRoom = {
                    roomId: updatedRoomData.roomId,
                    roomNumber: updatedRoomData.roomNumber,
                    floor: updatedRoomData.floor,
                    capacity: updatedRoomData.capacity
                };

                await updateRoom(updatedRoomData.roomId, updatedRoom);
                console.log("Room updated successfully!");
                alert('Room updated successfully!');
                setShowUpdateForm(false);
                setUpdatedRoomData({
                    roomId: '',
                    roomNumber: '',
                    floor: '',
                    capacity: ''
                });

                // Refresh room list
                const res = await getAllRooms();
                setRooms(res.data);
            } catch (error) {
                console.error('Error updating room:', error);
                alert('Failed to update room.');
            }
        }
    };


    const handleAddBedForPopup = async (roomId) => {
        if (!showAddBedForm) {
            setShowAddBedForm(true); // Show the form for adding a bed to the room

            // Find the room to add a bed in based on roomId
            const roomToAddBed = rooms.find(r => r.roomId === roomId);
            if (roomToAddBed) {
                // Set the room details into bedData state
                setBedData({
                    roomId: roomToAddBed.roomId,
                    bedId: '',           // Initialize bedId as empty
                    bedNumber: ''        // Initialize bedNumber as empty
                });
            }
        } else {
            // Proceed to submit the new bed data
            try {
                const newBed = {
                    bedId: bedData.bedId,
                    bedNumber: bedData.bedNumber,
                    roomId: bedData.roomId
                };

                // Call the addBedToRoom function (assuming you have already defined it to make the API request)
                await addBedToRoom(bedData.roomId, newBed);

                console.log("Bed added successfully!");
                alert('Bed added successfully!');

                // Reset the form and close it
                setShowAddBedForm(false);
                setBedData({
                    roomId: '',
                    bedId: '',
                    bedNumber: ''
                });

                // Fetch the updated list of beds for this room
                const bedResponse = await getBedsByRoomId(bedData.roomId);
                setBeds(bedResponse.data); // Update the beds state

                // Fetch the updated room data
                const res = await getRoomWithBeds(bedData.roomId);
                setSelectedRoom(res.data); // Update the selected room data with the new bed

            } catch (error) {
                console.error('Error adding bed:', error);
                alert('Failed to add bed.');
            }
        }
    };

    const handleDeleteBedForPopup = (roomId) => {
        alert(`Deleting bed in popup for Room ID: ${roomId}`);
    };

    /**
     * useEffect hook to load all room data when the component first mounts.
     *
     * This effect runs only once (due to empty dependency array []) and:
     * - Calls the backend API using getAllRooms() from the service layer
     * - On success, stores the list of rooms in the `rooms` state
     * - On failure, logs the error for debugging purposes
     * - Finally, regardless of success/failure, sets `loading` to false to stop the loading spinner
     */
    useEffect(() => {
        const fetchRooms = async () => {
            try {
                const response = await getAllRooms();
                setRooms(response.data);
            } catch (error) {
                console.error('Error fetching rooms:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchRooms();
    }, []);

    return (
        <div className="payment-wrapper">
            <div className="payment-header">
                <h2>Manage Rooms</h2>
                <button onClick={() => setShowCreateForm(true)}>Add Room</button>
            </div>

            {loading ? (
                <p>Loading rooms...</p>
            ) : (
                <>
                    {/* Responsive wrapper around the table */}
                        <RoomTable
                            rooms={rooms}
                            onView={onView}
                            onEdit={onEdit}
                            onDelete={onDelete}
                            onAdd={onAdd}
                        />


                    {showPopup && selectedRoom && (
                        <RoomPopup
                            room={selectedRoom}
                            beds={beds}
                            onClose={() => setShowPopup(false)}
                            onAddBedForPopup={handleAddBedForPopup}
                            onDeleteBedForPopup={handleDeleteBedForPopup}
                        />
                    )}

                    {showCreateForm && (
                        <div className="popup">
                            <div className="popup-content">
                                <h3>Create New Room</h3>
                                <input
                                    type="text"
                                    placeholder="Room Number"
                                    value={newRoomData.roomNumber}
                                    onChange={(e) => setNewRoomData({ ...newRoomData, roomNumber: e.target.value })}
                                />
                                <input
                                    type="number"
                                    placeholder="Floor"
                                    value={newRoomData.floor}
                                    onChange={(e) => setNewRoomData({ ...newRoomData, floor: e.target.value })}
                                />
                                <input
                                    type="number"
                                    placeholder="Capacity"
                                    value={newRoomData.capacity}
                                    onChange={(e) => setNewRoomData({ ...newRoomData, capacity: e.target.value })}
                                />
                                <input
                                    type="number"
                                    placeholder="Bed Count"
                                    value={newRoomData.bedCount}
                                    onChange={(e) => setNewRoomData({ ...newRoomData, bedCount: e.target.value })}
                                />
                                <button onClick={onAdd}>Submit</button>
                                <button onClick={() => setShowCreateForm(false)}>Cancel</button>
                            </div>
                        </div>
                    )}

                    {showUpdateForm && (
                        <div className="popup">
                            <div className="popup-content">
                                <h3>Update Room</h3>
                                <input
                                    type="text"
                                    placeholder="Room Number"
                                    value={updatedRoomData.roomNumber}
                                    onChange={(e) => setUpdatedRoomData({ ...updatedRoomData, roomNumber: e.target.value })}
                                />
                                <input
                                    type="number"
                                    placeholder="Floor"
                                    value={updatedRoomData.floor}
                                    onChange={(e) => setUpdatedRoomData({ ...updatedRoomData, floor: e.target.value })}
                                />
                                <input
                                    type="number"
                                    placeholder="Capacity"
                                    value={updatedRoomData.capacity}
                                    onChange={(e) => setUpdatedRoomData({ ...updatedRoomData, capacity: e.target.value })}
                                />
                                <button onClick={onEdit}>Submit</button>
                                <button onClick={() => setShowUpdateForm(false)}>Cancel</button>
                            </div>
                        </div>
                    )}

                    {showAddBedForm && (
                        <div className="popup">
                            <div className="popup-content">
                                <h3>Add Bed to Room</h3>
                                <input
                                    type="text"
                                    placeholder="Bed ID"
                                    value={bedData.bedId}
                                    onChange={(e) => setBedData({ ...bedData, bedId: e.target.value })}
                                />
                                <input
                                    type="text"
                                    placeholder="Bed Number"
                                    value={bedData.bedNumber}
                                    onChange={(e) => setBedData({ ...bedData, bedNumber: e.target.value })}
                                />
                                <input
                                    type="hidden"
                                    value={bedData.roomId}
                                    readOnly
                                />
                                <button onClick={() => handleAddBedForPopup(bedData.roomId)}>Submit</button>
                                <button onClick={() => setShowAddBedForm(false)}>Cancel</button>
                            </div>
                        </div>
                    )}
                </>
            )}
        </div>
    );
};

export default ManageRooms;