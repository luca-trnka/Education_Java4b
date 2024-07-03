import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const NewOffer = () => {
    const [title, setTitle] = useState('');
    const [status, setStatus] = useState('');
    const [customer, setCustomer] = useState('');
    const [supplier, setSupplier] = useState('');
    const [workers, setWorkers] = useState([]);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token is not available');
            return;
        }
        try {
            await axios.post('http://localhost:8080/api/offers', {
                title,
                status,
                customer,
                supplier,
                workers
            }, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            navigate('/dashboard');
        } catch (error) {
            console.error('Error creating offer:', error);
        }
    };

    return (
        <div>
            <h1>Create New Offer</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Title:</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
                </div>
                <div>
                    <label>Status:</label>
                    <input type="text" value={status} onChange={(e) => setStatus(e.target.value)} />
                </div>
                <div>
                    <label>Customer:</label>
                    <input type="text" value={customer} onChange={(e) => setCustomer(e.target.value)} />
                </div>
                <div>
                    <label>Supplier:</label>
                    <input type="text" value={supplier} onChange={(e) => setSupplier(e.target.value)} />
                </div>
                <div>
                    <label>Workers (comma-separated IDs):</label>
                    <input type="text" value={workers.join(',')} onChange={(e) => setWorkers(e.target.value.split(',').map(id => id.trim()))} />
                </div>
                <button type="submit">Create Offer</button>
            </form>
        </div>
    );
}

export default NewOffer;