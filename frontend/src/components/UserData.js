// src/components/UserData.js

import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import Axios if you prefer to use it

const API_URL = 'http://localhost:8080/api'; // Replace with your backend API URL

const UserData = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Function to fetch data from backend
        const fetchUserData = async () => {
            try {
                const response = await axios.get(`${API_URL}/users`);
                setUsers(response.data); // Assuming the response is an array of users
                setLoading(false); // Set loading to false after data is fetched
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false); // Set loading to false on error as well
            }
        };

        fetchUserData(); // Call the fetch function when the component mounts

        // Cleanup function (optional)
        return () => {
            // Clean up any resources or subscriptions if needed
        };
    }, []); // Empty dependency array ensures useEffect runs once

    return (
        <div>
            <h2>User Data</h2>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <ul>
                    {users.map(user => (
                        <li key={user.id}>{user.name}</li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default UserData;
