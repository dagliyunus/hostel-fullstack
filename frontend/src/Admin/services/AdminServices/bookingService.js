import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/admin/dashboard/manageBooking';

// 🔄 Get all bookings
export const getAllBookings = async () => {
    return await axios.get(`${BASE_URL}/getAllBookings`);
};

// ➕ Create a booking (admin use)
export const createBooking = async (bookingData) => {
    console.log("📦 Sending bookingData:", bookingData);
    return await axios.post(`${BASE_URL}/createBooking`, bookingData);
};

// ✏️ Update a booking (if update is implemented later)
export const updateBooking = async (bookingData) => {
    return await axios.put(`${BASE_URL}/updateBooking`, bookingData);
};

// 🗑️ Delete booking (optional)
export const deleteBooking = async (bookingId) => {
    return await axios.delete(`${BASE_URL}/deleteBooking/${bookingId}`);
};
