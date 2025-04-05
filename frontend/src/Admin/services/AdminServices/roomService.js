// roomService.js
// This module handles all HTTP requests related to Room data for the admin dashboard.
// It uses Axios to perform async calls to the backend API for room operations like
// fetching all rooms, fetching a single room with beds, and creating a room with beds.

import axios from 'axios';

// Base URL for all admin dashboard room-related API endpoints
const API_BASE_URL = 'http://localhost:8080/api/admin/dashboard';

/**
 * Fetches all rooms from the backend.
 * This is used to display the full list of rooms in the "Manage Rooms" table.
 *
 * @returns {Promise<AxiosResponse>} A promise that resolves with the list of rooms.
 */
export const getAllRooms = () => {
    return axios.get(`${API_BASE_URL}/manageRoom/getAllRooms`);
};

/**
 * Fetches a specific room by ID along with all its associated beds.
 * This is used when the admin clicks "View" to see room details in a popup.
 *
 * @param {string} roomId - The unique room ID (e.g., "R1")
 * @returns {Promise<AxiosResponse>} A promise with detailed room and bed data.
 */
export const getRoomWithBeds = (roomId) => {
    return axios.get(`${API_BASE_URL}/manageRoom/getRoomWithBeds`, {
        params: { room_id: roomId } // query param sent to the backend
    });
};

/**
 * Creates a new room with a given number of beds.
 * This function is used when the admin submits the "Create Room" form.
 *
 * @param {Object} roomData - Contains roomNumber, floor, capacity, and bedCount
 * @returns {Promise<AxiosResponse>} A promise that resolves with the created room
 */
export const createRoomWithBeds = (roomData) => {
    return axios.post(`${API_BASE_URL}/manageRoom/createRoomWithBeds`, roomData);
};

/**
 * Deletes a room from the backend using its roomId.
 * Makes a DELETE request with room_id as a request parameter.
 *
 * @param {string} roomId - The unique ID of the room to delete (e.g., "R1")
 * @returns {Promise} Axios response promise
 */
export const deleteRoom = (roomId) => {
    return axios.delete(`${API_BASE_URL}/manageRoom/deleteRoom`, {
        params: { room_id: roomId }
    });
};

/**
 * Updates an existing room's details using a PUT request.
 * Sends updated room data to the backend, and expects room_id as a query parameter.
 *
 * @param {string} roomId - The unique ID of the room to update
 * @param {Object} roomData - The updated room data (roomNumber, floor, capacity, etc.)
 * @returns {Promise<AxiosResponse>} A promise with the updated room
 */
export const updateRoom = (roomId, roomData) => {
    return axios.put(`${API_BASE_URL}/manageRoom/updateRoom`, roomData, {
        params: { room_id: roomId }  // This sends room_id as a query parameter in the URL
    });
};

/**
 * Fetches all beds for a specific room based on room ID.
 * This is used to display all beds associated with a particular room.
 *
 * @param {string} roomId - The unique room ID (e.g., "R1")
 * @returns {Promise<AxiosResponse>} A promise that resolves with the list of beds for the given room
 */
export const getBedsByRoomId = (roomId) => {
    return axios.get(`${API_BASE_URL}/manageBed/getBedsByRoomId`, {
        params: { room_id: roomId } // Room ID is passed as a query parameter
    });
};

/**
 * Adds a bed to a specific room.
 * This function is used when the admin submits the "Add Bed" form.
 *
 * @param {string} roomId - The unique ID of the room to which the bed is added
 * @param {Object} bedData - Contains bed details
 * @returns {Promise<AxiosResponse>} A promise that resolves with the updated room data
 */
export const addBedToRoom = (roomId, bedData) => {
    return axios.post(`${API_BASE_URL}/manageBed/addBed`, bedData, {
        params: { room_id: roomId } // Add room_id as a query parameter
    });
};

