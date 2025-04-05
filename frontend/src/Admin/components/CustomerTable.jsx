import React from 'react';
import '../../styles/admin/managementTables/style.css';

/**
 * CustomerTable component displays a table of customer information with an edit action.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Array<Object>} props.customers - Array of customer objects to display.
 * @param {string} props.customers[].customerId - Unique ID of the customer.
 * @param {string} props.customers[].firstName - First name of the customer.
 * @param {string} props.customers[].lastName - Last name of the customer.
 * @param {string} props.customers[].email - Email address of the customer.
 * @param {string} props.customers[].phone - Phone number of the customer.
 * @param {string|number} props.customers[].roomNumber - Room number assigned to the customer.
 * @param {string|number} props.customers[].bedNumber - Bed number assigned to the customer.
 * @param {function} props.onEdit - Callback function triggered when the "Edit" button is clicked.
 * @returns {JSX.Element} A JSX element displaying a formatted customer table.
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