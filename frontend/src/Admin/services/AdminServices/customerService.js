import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/admin/dashboard/manageCustomer';

/**
 * Fetches all customer records from the backend.
 *
 * @function getAllCustomers
 * @async
 * @returns {Promise<AxiosResponse>} A promise resolving to an array of all customers.
 */
export const getAllCustomers = () => {
    return axios.get(`${BASE_URL}/findAll`);
};

/**
 * Updates an existing customer record in the backend.
 *
 * @function updateCustomer
 * @async
 * @param {Object} customerData - The updated customer data to be sent to the backend.
 * @param {string} customerData.customerId - Unique identifier of the customer.
 * @param {string} customerData.firstName - First name of the customer.
 * @param {string} customerData.lastName - Last name of the customer.
 * @param {string} customerData.email - Email address of the customer.
 * @param {string} customerData.phone - Phone number of the customer.
 * @param {string} customerData.dateOfBirth - Date of birth in ISO format.
 * @param {string|number} customerData.roomId - ID of the room assigned.
 * @param {string|number} customerData.bedId - ID of the bed assigned.
 * @returns {Promise<Object>} A promise resolving to the updated customer data from the backend.
 * @throws Will throw an error if the update fails.
 */
export const updateCustomer = async (customerData) => {
    try {
        const response = await axios.put(`${BASE_URL}/updateCustomer`, customerData);
        console.log('Customer updated successfully:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error updating customer:', error.response?.data || error.message);
        throw error;
    }
};