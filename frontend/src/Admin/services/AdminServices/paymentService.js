import axios from "axios";

const BASE_URL = "http://localhost:8080/api/admin/dashboard/managePayment";

export const getAllPaymentsSorted = () => {
    return axios.get(`${BASE_URL}/allSorted`);
};

