import React, { useEffect, useState } from 'react';
import CustomerTable from '../../components/CustomerTable.jsx';
import { getAllCustomers,  updateCustomer } from '../../services/AdminServices/customerService.js';

/**
 * ManageCustomers component is the admin interface for viewing and editing customer details.
 *
 * @component
 * @returns {JSX.Element} A component that displays customer data and provides editing functionality via a popup.
 *
 * @description
 * Features:
 * - Fetches all customer records on initial render via `getAllCustomers()`.
 * - Displays a list of customers in a table with edit buttons.
 * - Allows inline editing of customer details through a popup modal.
 * - On form submission, sends updates to the backend via `updateCustomer()`.
 * - Automatically reloads the page after successful update to reflect changes.
 *
 * State Variables:
 * - `customers` (Array): All customers fetched from the backend.
 * - `loading` (boolean): Indicates whether customer data is being loaded.
 * - `showEditPopup` (boolean): Toggles the visibility of the edit popup.
 * - `editingCustomer` (Object|null): Stores the currently selected customer for editing.
 *
 * Editing Logic:
 * - If a customer is selected for editing, pre-fills their information in a popup.
 * - Fields include: first name, last name, email, phone, date of birth, room number.
 * - Bed and room data are mapped into nested objects before sending to backend.
 */
const ManageCustomers = () => {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showEditPopup, setShowEditPopup] = useState(false);
    const [editingCustomer, setEditingCustomer] = useState(null);

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const response = await getAllCustomers();
                console.log("Customer API response:", response.data);

                const customersArray = Array.isArray(response.data) ? response.data : [];
                setCustomers(customersArray);
            } catch (error) {
                console.error('Error fetching customers:', error);
                setCustomers([]); // Ensure state is always an array
            } finally {
                setLoading(false);
            }
        };

        fetchCustomers();
    }, []);

    const handleEdit = async (customerId) => {
        if (showEditPopup && editingCustomer) {
            const customerPayload = {
                ...editingCustomer,
                room: {roomId: editingCustomer.roomId},
                bed: {bedId: editingCustomer.bedId}
            };

            try {
                await updateCustomer(customerPayload);
                alert('Customer updated successfully.');
                setShowEditPopup(false);
                window.location.reload();
            } catch (error) {
                alert('Failed to update customer.');
                console.error(error);
            }
        } else {
            const customerToEdit = customers.find(c => c.customerId === customerId);
            if (customerToEdit) {
                setEditingCustomer({
                    ...customerToEdit,
                    roomNumber: customerToEdit.roomNumber || '',
                    bedNumber: customerToEdit.bedNumber || ''
                });
                setShowEditPopup(true);
            }
        }
    };

    return (
        <div className="payment-wrapper">
            <div className="payment-header">
                <h2>Manage Customers</h2>
            </div>

            {loading ? (
                <p>Loading customers...</p>
            ) : (
                    <CustomerTable
                        customers={customers}
                        onEdit={handleEdit}
                    />
            )}

            {/*  Edit Customer Popup */}
            {showEditPopup && editingCustomer && (
                <div className="popup">
                    <div className="popup-content">
                        <h3>Edit Customer</h3>
                        <input
                            placeholder="First Name"
                            value={editingCustomer.firstName}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, firstName: e.target.value })
                            }
                        />
                        <input
                            placeholder="Last Name"
                            value={editingCustomer.lastName}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, lastName: e.target.value })
                            }
                        />
                        <input
                            placeholder="Email"
                            value={editingCustomer.email}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, email: e.target.value })
                            }
                        />
                        <input
                            placeholder="Phone"
                            value={editingCustomer.phone}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, phone: e.target.value })
                            }
                        />
                        <input
                            placeholder="Date of Birth (YYYY-MM-DD)"
                            value={editingCustomer.dateOfBirth}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, dateOfBirth: e.target.value })
                            }
                        />
                        <input
                            placeholder="Room Number"
                            value={editingCustomer.roomNumber}
                            onChange={e =>
                                setEditingCustomer({ ...editingCustomer, roomNumber: e.target.value })
                            }
                        />
                        <p>
                            <strong>Bed:</strong> {editingCustomer.bedNumber || 'N/A'}
                        </p>
                        <button onClick={() => handleEdit(editingCustomer.customerId)}>Update</button>
                        <button onClick={() => setShowEditPopup(false)}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ManageCustomers;