import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * CustomerTable Component
 *
 * Displays a dynamic HTML table of all hostel customers provided via props.
 * Each customer is rendered as a row containing key attributes like ID, name,
 * email, phone number, room and bed numbers. A set of action buttons is available per customer.
 *
 * @component
 * @param {Object[]} customers - Array of customer DTOs to be displayed.
 * @param {function} onEdit - Callback fired when the "Edit" button is clicked for a customer.
 */
const CustomerTable = ({ customers, onEdit }) => {
    return (
            <div className="payment-table-wrapper">
                <table className="payment-table">
                <thead>
                <tr>
                    <th>Customer ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Room</th>
                    <th>Bed</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {customers.map((customer) => (
                    <tr key={customer.customerId}>
                        <td>{customer.customerId}</td>
                        <td>{customer.firstName}</td>
                        <td>{customer.lastName}</td>
                        <td>{customer.email}</td>
                        <td>{customer.phone}</td>
                        <td>{customer.roomNumber}</td>
                        <td>{customer.bedNumber}</td>
                        <td>
                            <button onClick={() => onEdit(customer.customerId)}>Edit</button>
                           </td>
                    </tr>
                ))}
                </tbody>
            </table>
            </div>
    );
};

export default CustomerTable;