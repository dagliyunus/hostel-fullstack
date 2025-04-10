import React, { useEffect, useState } from "react";
import PaymentTable from '../../components/PaymentTable.jsx';
import { getAllPaymentsSorted } from "../../services/AdminServices/paymentService";
import "../../../styles/admin/dashboard/payment.css";

/**
 * ManagePayment is a React component used in the admin dashboard to display all payment records.
 *
 * @component
 * @returns {JSX.Element} A rendered view that includes a payment history table or loading/error states.
 *
 * @description
 * Features:
 * - Fetches a sorted list of payments using the `getAllPaymentsSorted()` service on initial render.
 * - Displays the results inside a reusable `PaymentTable` component.
 * - Converts the timestamp of each payment into a relative "time ago" format using `timeAgo()` function.
 * - Handles loading and error states gracefully.
 *
 * State Variables:
 * - `payments` (Array): Stores the fetched payment records.
 * - `loading` (boolean): Indicates whether the data is currently being fetched.
 * - `error` (string|null): Stores any error message encountered during the fetch.
 *
 * Utility Functions:
 * - `timeAgo(timestamp: string): string`: Converts a given timestamp to a human-readable "x time ago" string.
 *
 * Dependencies:
 * - `getAllPaymentsSorted` from AdminServices/paymentService.
 * - `PaymentTable` component for rendering payment data.
 */

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