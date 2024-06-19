import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Homepage from './components/Homepage';
import Login from './components/Login';
import Register from './components/Register';
import AdminDashboard from './components/AdminDashboard';
import CustomerDashboard from './components/CustomerDashboard';
import SupplierDashboard from './components/SupplierDashboard';
import WorkerDashboard from './components/WorkerDashboard';
import NewUserDashboard from './components/NewUserDashboard';
import UserProfile from './components/UserProfile';
import { jwtDecode } from 'jwt-decode';
import { useHistory } from 'react-router-dom';

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [role, setRole] = useState('');

    const handleSuccessfulLogin = (token) => {
        localStorage.setItem('token', token);
        setIsLoggedIn(true);

        const decodedToken = jwtDecode(token);
        setRole(decodedToken.role);
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        setRole('');
    };

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            const decodedToken = jwtDecode(token);
            setRole(decodedToken.role);
            setIsLoggedIn(true);
        }
    }, []);

    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Homepage />} />
                    <Route path="/login" element={<Login onLogin={handleSuccessfulLogin} />} />
                    <Route path="/register" element={<Register />} />
                    {isLoggedIn && role === 'ADMIN' && <Route path="/dashboard" element={<AdminDashboard />} />}
                    {isLoggedIn && role === 'CUSTOMER' && <Route path="/dashboard" element={<CustomerDashboard />} />}
                    {isLoggedIn && role === 'SUPPLIER' && <Route path="/dashboard" element={<SupplierDashboard />} />}
                    {isLoggedIn && role === 'WORKER' && <Route path="/dashboard" element={<WorkerDashboard />} />}
                    {isLoggedIn && role === 'NEW_USER' && <Route path="/dashboard" element={<NewUserDashboard />} />}
                    <Route path="/profile/:userId" element={<UserProfile />} />
                    {/* Add routes for Customer, Supplier, Worker dashboards */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;