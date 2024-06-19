import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/users/all');
                setUsers(response.data);
            } catch (error) {
                console.error('Fetch users error:', error);
            }
        };

        fetchUsers();
    }, []);

    const handleEditUser = (userId) => {
        // Navigate to user profile edit page or modal
    };

    const handleDeleteUser = async (userId) => {
        if (window.confirm('Are you sure you want to delete this user?')) {
            try {
                await axios.delete(`http://localhost:8080/api/users/${userId}`);
                setUsers(users.filter(user => user.id !== userId));
            } catch (error) {
                console.error('Delete user error:', error);
                // Handle delete error
            }
        }
    };

    return (
        <div>
            <h2>Admin Dashboard</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.email}</td>
                        <td>{user.name}</td>
                        <td>{user.role}</td>
                        <td>
                            <button onClick={() => handleEditUser(user.id)}>Edit</button>
                            <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminDashboard;
