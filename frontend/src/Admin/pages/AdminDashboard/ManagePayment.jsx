import React, { useEffect, useState } from "react";
import PaymentTable from '../../components/PaymentTable.jsx';
import { getAllPaymentsSorted } from "../../services/AdminServices/paymentService";
import "../../../styles/admin/dashboard/payment.css";

const ManagePayment = () => {
    const [payments, setPayments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPayments = async () => {
            try {
                const response = await getAllPaymentsSorted();
                setPayments(Array.isArray(response.data) ? response.data : []);
            } catch (err) {
                setError("Failed to load payment records.");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchPayments();
    }, []);

    const timeAgo = (timestamp) => {
        const diff = Date.now() - new Date(timestamp).getTime();
        const minutes = Math.floor(diff / (1000 * 60));
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);
        if (minutes < 1) return "Just now";
        if (minutes < 60) return `${minutes} minute(s) ago`;
        if (hours < 24) return `${hours} hour(s) ago`;
        return `${days} day(s) ago`;
    };

    return (
        <div className="payment-wrapper">
            <div className="payment-header">
                <h2>Payment History</h2>
            </div>
            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <p className="error">{error}</p>
            ) : (
                <PaymentTable payments={payments} timeAgo={timeAgo} />
            )}
        </div>
    );
};

export default ManagePayment;