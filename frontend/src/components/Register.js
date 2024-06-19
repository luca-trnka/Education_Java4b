import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const Registration = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

    const handleRegistration = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/register', {email, password, name});
            if (response && response.data) {
                setMessage("Registration was successful, now wait for the admin confirmation.");
                navigate('/'); // Redirect to home page
            }
        } catch (error) {
            console.error('Registration error:', error);
            if (error.response) {
                setMessage(`Registration was not successful: ${error.response.data.message}`);
            }
        }
    };

    return (
        <form onSubmit={handleRegistration}>
            <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}
                   required/>
            <input type="text" placeholder="Name Lastname" value={name} onChange={(e) => setName(e.target.value)}
                   required/>
            <button type="submit">Register</button>
            {message && <p>{message}</p>}
        </form>
    );
}

export default Registration;