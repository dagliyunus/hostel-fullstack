// src/services/contactMessageService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/contact';

const contactMessageService = {
    sendMessage: async (formData) => {
        return await axios.post(`${BASE_URL}/send-email`, formData);
    },

    getUnreadMessages: async () => {
        return await axios.get(`${BASE_URL}/unread`);
    },

    getAllMessages: async () => {
        return await axios.get(`${BASE_URL}/all`);
    },

    markAsRead: async (messageId) => {
        return await axios.put(`${BASE_URL}/mark-as-read/${messageId}`);
    }
};

export default contactMessageService;