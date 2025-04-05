import React from "react";

/**
 * PaymentTable component renders a table displaying payment records.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Array<Object>} props.payments - Array of payment objects to display.
 * @param {string} props.payments[].paymentId - Unique identifier for the payment.
 * @param {string} props.payments[].bookingId - Associated booking ID related to the payment.
 * @param {string} props.payments[].paymentType - Type of payment (e.g., credit card, cash).
 * @param {number} props.payments[].amount - The total amount of the payment in Euros.
 * @param {string} props.payments[].paymentDate - The date and time when the payment was made.
 * @param {function} props.timeAgo - Function that formats the payment date into a "time ago" string.
 * @returns {JSX.Element} A JSX element that renders a table of payment information.
 */
const PaymentTable = ({ payments, timeAgo }) => {
    return (
        <div className="payment-table-wrapper">
            <table className="payment-table">
                <thead>
                <tr>
                    <th>Payment ID</th>
                    <th>Booking ID</th>
                    <th>Type</th>
                    <th>Amount (€)</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {payments.map((payment) => (
                    <tr key={payment.paymentId}>
                        <td>{payment.paymentId}</td>
                        <td>{payment.bookingId}</td>
                        <td>{payment.paymentType}</td>
                        <td>{payment.amount}</td>
                        <td>{timeAgo(payment.paymentDate)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default PaymentTable;
