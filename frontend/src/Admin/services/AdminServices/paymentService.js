import axios from "axios";

const BASE_URL = "http://localhost:8080/api/admin/dashboard/managePayment";

/**
 * Fetches all payment records from the backend, sorted by most recent date.
 *
 * @function getAllPaymentsSorted
 * @async
 * @returns {Promise<AxiosResponse>} A promise that resolves to a list of sorted payments.
 *
 * @description
 * Sends a GET request to `${BASE_URL}/allSorted` to retrieve all payments in descending order
 * (most recent first). Used in the admin dashboard to populate payment history.
 */
export const getAllPaymentsSorted = () => {
    return axios.get(`${BASE_URL}/allSorted`);
};

