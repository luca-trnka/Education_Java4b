import React from 'react';
import { Link } from 'react-router-dom';

const Homepage = () => {
    return (
        <div>
            <h2>Welcome to Offer Management</h2>
            <Link to="/login">
                <button>Login</button>
            </Link>
            <Link to="/register">
                <button>Register</button>
            </Link>
        </div>
    );
}

export default Homepage;
