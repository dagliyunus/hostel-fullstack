import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/contact';

const contactMessageService = {
    /**
     * Sends a contact form message to the backend email endpoint.
     *
     * @async
     * @function sendMessage
     * @param {Object} formData - The data from the contact form (name, email, message, etc.).
     * @returns {Promise<AxiosResponse>} A promise resolving to the backend response after sending the message.
     */
    sendMessage: async (formData) => {
        return await axios.post(`${BASE_URL}/send-email`, formData);
    },

    /**
     * Retrieves all unread contact messages from the backend.
     *
     * @async
     * @function getUnreadMessages
     * @returns {Promise<AxiosResponse>} A promise resolving to the list of unread messages.
     */
    getUnreadMessages: async () => {
        return await axios.get(`${BASE_URL}/unread`);
    },

    /**
     * Retrieves all contact messages from the backend.
     *
     * @async
     * @function getAllMessages
     * @returns {Promise<AxiosResponse>} A promise resolving to the list of all messages.
     */
    getAllMessages: async () => {
        return await axios.get(`${BASE_URL}/all`);
    },

    /**
     * Marks a specific contact message as read.
     *
     * @async
     * @function markAsRead
     * @param {string|number} messageId - The ID of the message to be marked as read.
     * @returns {Promise<AxiosResponse>} A promise resolving to the update confirmation.
     */
    markAsRead: async (messageId) => {
        return await axios.put(`${BASE_URL}/mark-as-read/${messageId}`);
    }
};

export default contactMessageService;