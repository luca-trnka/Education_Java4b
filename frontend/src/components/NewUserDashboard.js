import React from 'react';
import { useNavigate } from 'react-router-dom';

const NewUserDashboard = ({ onLogout }) => {
    const navigate = useNavigate();

    const handleLogoutClick = () => {
        onLogout();
        navigate('/');
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <h1>Waiting for confirmation from Admin.</h1>
            <button onClick={handleLogoutClick}>Log out</button>
        </div>
    );
}

export default NewUserDashboard;