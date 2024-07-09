import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const NewUser = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [role, setRole] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token is not available');
            navigate('/dashboard');
            return;
        }
        const userRole = "SUPPLIER"
        if (userRole === "SUPPLIER") {
            setRole('WORKER');
        }
    }, [navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token is not available');
            return;
        }
        const userData = {
            email,
            password,
            name,
            role
        };
        console.log("Submitting offer data:", userData);

        try {
            const response = await axios.post('http://localhost:8080/api/users/newuser', userData, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
            if (response.status === 201) {
                alert('User created successfully');
                navigate('/dashboard');
            } else {
                alert('Failed to create user');
            }
        } catch (error) {
            console.error('Error creating user:', error);
            alert('Error creating user');
        }
    };
    const handleGoBack = () => {
        navigate('/dashboard');
    };

    return (
        <div>
            <h1>Create New User</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                </div>
                <div>
                    <label>Password:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <div>
                    <label>Name:</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)}/>
                </div>
                    {role !== 'WORKER' && (
                        <div>
                    <label>Role:</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="NEW_USER">NEW_USER</option>
                        <option value="CUSTOMER">CUSTOMER</option>
                        <option value="SUPPLIER">SUPPLIER</option>
                        <option value="WORKER">WORKER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                    )}
                <button type="submit">Create User</button>
            </form>
            <button onClick={handleGoBack}>Go Back</button>
        </div>
    );
};

export default NewUser;