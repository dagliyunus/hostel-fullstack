import React from "react";

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
