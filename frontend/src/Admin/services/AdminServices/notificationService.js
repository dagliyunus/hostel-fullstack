// NotificationService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/admin/dashboard/manageNotifications';

export const getAllNotifications = () => axios.get(API_URL);

export const markAsRead = (id) => axios.patch(`${API_URL}/${id}/markAsRead`);

