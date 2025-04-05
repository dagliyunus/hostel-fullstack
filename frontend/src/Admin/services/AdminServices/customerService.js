import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/admin/dashboard/manageCustomer';

export const getAllCustomers = () => {
    return axios.get(`${BASE_URL}/findAll`);
};



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