import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [offers, setOffers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsersAndOffers = async () => {
            try {
                const token = localStorage.getItem('token');
                console.log('Token:', token);
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                const usersResponse = await axios.get('http://localhost:8080/api/users/all', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setUsers(usersResponse.data);

                const offersResponse = await axios.get('http://localhost:8080/api/offers/all', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setOffers(offersResponse.data);
            } catch (error) {
                console.error('Fetch users and offers error:', error);
            }
        };

        fetchUsersAndOffers();
    }, []);

    const handleEditUser = (userId) => {
        navigate(`/user-profile/${userId}`);
    };

    const handleDeleteUser = async (userId) => {
        if (window.confirm('Are you sure you want to delete this user?')) {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                await axios.delete(`http://localhost:8080/api/users/${userId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setUsers(users.filter(user => user.id !== userId));
            } catch (error) {
                console.error('Delete user error:', error);
            }
        }
    };

    const handleEditOffer = (offerId) => {
        navigate(`/offer-profile/${offerId}`);
    };

    const handleDeleteOffer = async (offerId) => {
        if (window.confirm('Are you sure you want to delete this offer?')) {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                await axios.delete(`http://localhost:8080/api/offers/${offerId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setOffers(offers.filter(offer => offer.id !== offerId));
            } catch (error) {
                console.error('Delete offer error:', error);
            }
        }
    };

    return (
        <div>
            <h1>Admin Dashboard</h1>
            <h2>All users:</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.role}</td>
                        <td>
                            <button onClick={() => handleEditUser(user.id)}>Edit</button>
                            <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2>All offers:</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Customer ID</th>
                    <th>Supplier ID</th>
                    <th>Worker ID</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {offers.map(offer => (
                    <tr key={offer.id}>
                        <td>{offer.id}</td>
                        <td>{offer.title}</td>
                        <td>{offer.status}</td>
                        <td>{offer.customer ? offer.customer.id : 'N/A'}</td>
                        <td>{offer.supplier ? offer.supplier.id : 'N/A'}</td>
                        <td>{offer.workers ? offer.workers.map(worker => worker.id).join(', ') : 'N/A'}</td>
                        <td>
                            <button onClick={() => handleEditOffer(offer.id)}>Edit</button>
                            <button onClick={() => handleDeleteOffer(offer.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
}

export default AdminDashboard;