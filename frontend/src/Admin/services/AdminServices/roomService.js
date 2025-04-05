import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/admin/dashboard';

/**
 * Fetches all rooms from the backend.
 *
 * Used in the admin panel to display a complete list of rooms.
 * Sends a GET request to `/manageRoom/getAllRooms`.
 *
 * @function getAllRooms
 * @async
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to an array of room objects.
 */
export const getAllRooms = () => {
    return axios.get(`${API_BASE_URL}/manageRoom/getAllRooms`);
};

/**
 * Fetches a single room's details along with all its associated beds.
 *
 * Used in the "View Room" popup to show room-specific data and its beds.
 * Sends a GET request with `room_id` as a query parameter.
 *
 * @function getRoomWithBeds
 * @async
 * @param {string} roomId - The unique identifier of the room (e.g., "R101").
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to the room object and its beds.
 */
export const getRoomWithBeds = (roomId) => {
    return axios.get(`${API_BASE_URL}/manageRoom/getRoomWithBeds`, {
        params: { room_id: roomId } // query param sent to the backend
    });
};

/**
 * Creates a new room and its associated beds.
 *
 * Called when the admin submits the "Create Room" form.
 * Sends a POST request with roomNumber, floor, capacity, and bedCount in the body.
 *
 * @function createRoomWithBeds
 * @async
 * @param {Object} roomData - Object containing room creation details.
 * @param {string|number} roomData.roomNumber - The number assigned to the room.
 * @param {string|number} roomData.floor - The floor the room is located on.
 * @param {number} roomData.capacity - The maximum number of people the room can accommodate.
 * @param {number} roomData.bedCount - How many beds should be created within the room.
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to the created room.
 */
export const createRoomWithBeds = (roomData) => {
    return axios.post(`${API_BASE_URL}/manageRoom/createRoomWithBeds`, roomData);
};

/**
 * Deletes a room from the backend.
 *
 * Used when the admin clicks the delete button in the room table.
 * Sends a DELETE request with the `room_id` as a query parameter.
 *
 * @function deleteRoom
 * @async
 * @param {string} roomId - The ID of the room to delete (e.g., "R101").
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to a success message or deletion status.
 */
export const deleteRoom = (roomId) => {
    return axios.delete(`${API_BASE_URL}/manageRoom/deleteRoom`, {
        params: { room_id: roomId }
    });
};

/**
 * Updates the details of an existing room.
 *
 * Used when the admin edits room data from the popup modal.
 * Sends a PUT request with new data in the body and `room_id` in the query.
 *
 * @function updateRoom
 * @async
 * @param {string} roomId - The ID of the room to update.
 * @param {Object} roomData - Updated room properties.
 * @param {string|number} roomData.roomNumber - Updated room number.
 * @param {string|number} roomData.floor - Updated floor value.
 * @param {number} roomData.capacity - Updated capacity value.
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to the updated room.
 */
export const updateRoom = (roomId, roomData) => {
    return axios.put(`${API_BASE_URL}/manageRoom/updateRoom`, roomData, {
        params: { room_id: roomId }  // This sends room_id as a query parameter in the URL
    });
};

/**
 * Retrieves all beds assigned to a specific room.
 *
 * Used in the "Room Details" popup to display bed assignments.
 * Sends a GET request with `room_id` as a query parameter.
 *
 * @function getBedsByRoomId
 * @async
 * @param {string} roomId - ID of the room whose beds should be fetched.
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to a list of beds.
 */
export const getBedsByRoomId = (roomId) => {
    return axios.get(`${API_BASE_URL}/manageBed/getBedsByRoomId`, {
        params: { room_id: roomId } // Room ID is passed as a query parameter
    });
};

/**
 * Adds a new bed to a given room.
 *
 * Used in the "Add Bed" form inside the room details modal.
 * Sends a POST request with bed data and the room's ID.
 *
 * @function addBedToRoom
 * @async
 * @param {string} roomId - The ID of the room where the new bed should be added.
 * @param {Object} bedData - The bed details including bedId and bedNumber.
 * @param {string} bedData.bedId - Optional unique ID for the bed (if managed client-side).
 * @param {string|number} bedData.bedNumber - The number or label of the bed.
 * @returns {Promise<import('axios').AxiosResponse>} Promise resolving to the updated room including the new bed.
 */
export const addBedToRoom = (roomId, bedData) => {
    return axios.post(`${API_BASE_URL}/manageBed/addBed`, bedData, {
        params: { room_id: roomId } // Add room_id as a query parameter
    });
};
