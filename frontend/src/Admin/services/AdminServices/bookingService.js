import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/admin/dashboard/manageBooking';

/**
 * Fetches all bookings from the backend.
 *
 * @async
 * @function getAllBookings
 * @returns {Promise<AxiosResponse>} A promise that resolves to the list of all bookings.
 */
export const getAllBookings = async () => {
    return await axios.get(`${BASE_URL}/getAllBookings`);
};

/**
 * Sends a new booking to the backend to be created.
 *
 * @async
 * @function createBooking
 * @param {Object} bookingData - The data of the booking to be created.
 * @param {string} bookingData.customerFirstName - First name of the customer.
 * @param {string} bookingData.customerLastName - Last name of the customer.
 * @param {string} bookingData.customerEmail - Email address of the customer.
 * @param {string} bookingData.customerPhone - Phone number of the customer.
 * @param {string} bookingData.customerDateOfBirth - Date of birth of the customer.
 * @param {string} bookingData.roomNumber - Assigned room number.
 * @param {string} bookingData.checkInDate - Check-in date.
 * @param {string} bookingData.checkOutDate - Check-out date.
 * @param {number|string} bookingData.totalPrice - Total price of the booking.
 * @returns {Promise<AxiosResponse>} A promise that resolves to the response after booking creation.
 */
export const createBooking = async (bookingData) => {
    console.log("📦 Sending bookingData:", bookingData);
    return await axios.post(`${BASE_URL}/createBooking`, bookingData);
};

/**
 * Updates an existing booking in the backend.
 *
 * @async
 * @function updateBooking
 * @param {Object} bookingData - The updated booking information.
 * @returns {Promise<AxiosResponse>} A promise that resolves to the response after updating the booking.
 */
export const updateBooking = async (bookingData) => {
    return await axios.put(`${BASE_URL}/updateBooking`, bookingData);
};

/**
 * Deletes a booking based on its ID.
 *
 * @async
 * @function deleteBooking
 * @param {string|number} bookingId - The ID of the booking to delete.
 * @returns {Promise<AxiosResponse>} A promise that resolves to the response after deletion.
 */
export const deleteBooking = async (bookingId) => {
    return await axios.delete(`${BASE_URL}/deleteBooking/${bookingId}`);
};
