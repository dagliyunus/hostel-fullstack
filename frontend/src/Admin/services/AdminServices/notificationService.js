import axios from 'axios';

const API_URL = 'http://localhost:8080/api/admin/dashboard/manageNotifications';

/**
 * Fetches all notification records from the backend.
 *
 * @function getAllNotifications
 * @async
 * @returns {Promise<AxiosResponse>} A promise that resolves to a list of all notifications.
 */
export const getAllNotifications = () => axios.get(API_URL);

/**
 * Marks a specific notification as read by its ID.
 *
 * @function markAsRead
 * @async
 * @param {string|number} id - The ID of the notification to mark as read.
 * @returns {Promise<AxiosResponse>} A promise that resolves to the server's response after marking the notification as read.
 */
export const markAsRead = (id) => axios.patch(`${API_URL}/${id}/markAsRead`);

