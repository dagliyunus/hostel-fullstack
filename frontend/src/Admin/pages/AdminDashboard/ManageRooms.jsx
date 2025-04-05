import React, { useEffect, useState } from 'react';
import {addBedToRoom, deleteRoom, getAllRooms, getBedsByRoomId, updateRoom} from '../../services/AdminServices/roomService.js';
import RoomTable from '../../components/RoomTable.jsx';
import { getRoomWithBeds } from '../../services/AdminServices/roomService.js';
import { createRoomWithBeds } from '../../services/AdminServices/roomService.js';
import RoomPopup from '../../components/RoomPopup.jsx';

/**
 * ManageRooms is an administrative React component used to manage room and bed data.
 *
 * @component
 * @returns {JSX.Element} Renders the full interface for creating, updating, viewing, and deleting rooms and beds.
 *
 * @description
 * This component serves as the UI for administrators to handle room-related operations such as:
 * - Creating new rooms with a specified number of beds.
 * - Viewing room details along with associated beds in a modal.
 * - Editing room details including room number, floor, and capacity.
 * - Deleting rooms with confirmation.
 * - Dynamically adding beds to rooms through a form.
 *
 * State Variables:
 * - `rooms` (Array): List of room objects fetched from the backend.
 * - `beds` (Array): List of beds associated with a selected room.
 * - `loading` (boolean): Indicates loading state during data fetch.
 * - `selectedRoom` (Object|null): Currently selected room for popup display.
 * - `showPopup`, `showCreateForm`, `showUpdateForm`, `showAddBedForm` (boolean): Controls popup/form visibility.
 * - `newRoomData` (Object): Stores input values for new room creation.
 * - `updatedRoomData` (Object): Holds values for updating an existing room.
 * - `bedData` (Object): Used when adding a new bed to a room.
 *
 * Functions:
 * - `onView(roomId)`: Loads and displays room and bed details.
 * - `onAdd()`: Handles UI toggle and submission of new room data.
 * - `onDelete(roomId)`: Deletes a room after confirmation.
 * - `onEdit(roomId)`: Prepares and submits room update data.
 * - `handleAddBedForPopup(roomId)`: Manages form visibility and submission for bed creation.
 * - `handleDeleteBedForPopup(roomId)`: Placeholder for future bed deletion feature.
 *
 * Dependencies:
 * - RoomTable, RoomPopup components.
 * - roomService methods: getAllRooms, getRoomWithBeds, createRoomWithBeds, updateRoom, deleteRoom, addBedToRoom, getBedsByRoomId.
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
                    bedId: '',
                    bedNumber: ''
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