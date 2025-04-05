import React, { useState } from 'react';
import axios from 'axios';
import '../../../styles/admin/adminLoginPage/adminLogin.css';

const AdminLogin = ({ onLoginSuccess }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        console.log("🔐 Attempting login...");
        console.log("📤 Request payload:", { username, password });

        try {
            const response = await axios.post('http://localhost:8080/api/admin/login', {
                username,
                password
            });

            console.log("✅ Server response:", response.data);

            if (response.data.success) {
                console.log("🎉 Login successful, adminId:", response.data.adminId);
                localStorage.setItem('adminId', response.data.adminId);
                onLoginSuccess(); // Callback to switch view
            } else {
                console.warn("⚠️ Login failed with message:", response.data.message);
                setErrorMsg(response.data.message);
            }
        } catch (error) {
            console.error("❌ Axios error during login:", error);
            setErrorMsg('Login failed. Please try again.');
        }
    };

    return (
        <div className="admin-login-container">
            <form className="admin-login-form" onSubmit={handleSubmit}>
                <h2>Admin Login</h2>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Login</button>
                {errorMsg && <p className="error">{errorMsg}</p>}
            </form>
        </div>
    );
};

export default AdminLogin;