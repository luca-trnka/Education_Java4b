import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserProfile = ({ userId }) => {
    const [user, setUser] = useState({});
    const [roles, setRoles] = useState([]);
    const [selectedRole, setSelectedRole] = useState('');

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/users/${userId}`);
                setUser(response.data);
                // Assuming roles are fetched from backend or predefined
                setRoles(['Supplier', 'Customer', 'Worker', 'Admin']);
                setSelectedRole(response.data.role); // Assuming role is retrieved from backend
            } catch (error) {
                console.error('Fetch user error:', error);
            }
        };

        fetchUser();
    }, [userId]);

    const handleRoleChange = async () => {
        try {
            await axios.put(`http://localhost:8080/api/users/${userId}`, { ...user, role: selectedRole });
            // Handle successful role update
        } catch (error) {
            console.error('Update role error:', error);
            // Handle update error
        }
    };

    return (
        <div>
            <h2>User Profile</h2>
            <p>Email: {user.email}</p>
            <p>Name: {user.name}</p>
            <p>Role:
                <select value={selectedRole} onChange={(e) => setSelectedRole(e.target.value)}>
                    {roles.map(role => (
                        <option key={role} value={role}>{role}</option>
                    ))}
                </select>
                <button onClick={handleRoleChange}>Update Role</button>
            </p>
        </div>
    );
}

export default UserProfile;
