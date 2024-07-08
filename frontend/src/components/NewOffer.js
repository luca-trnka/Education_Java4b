import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const NewOffer = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('');
    const [customer, setCustomer] = useState('');
    const [supplier, setSupplier] = useState('');
    const [workers, setWorkers] = useState([]);
    const navigate = useNavigate();

    const offerStatuses = [
        "NEW",
        "ACCEPTED",
        "REJECTED",
        "IN_PROGRESS",
        "READY_TO_BE_SHOWN",
        "CUSTOMER_APPROVAL",
        "CUSTOMER_DISAPPROVAL",
        "FIXING_BUGS",
        "INVOICED",
        "COMPLETED"
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token is not available');
            return;
        }
        try {
            const offerData = {
                title: title,
                status: status,
                description: description,
                supplierId: supplier,
                customerId: customer,
                workerIds: workers.map(worker => parseInt(worker, 10))
            };
            const response = await axios.post('http://localhost:8080/api/offers/', offerData, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.status === 201) {
                alert('Offer created successfully');
                navigate('/dashboard');
            } else {
                alert('Failed to create offer');
            }
        } catch (error) {
            console.error('Error creating offer:', error);
            alert('Error creating offer');
        }
    };

    const handleGoBack = () => {
        navigate('/dashboard');
    };

    return (
        <div>
            <h1>Create New Offer</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Title:</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}/>
                </div>
                <div>
                    <label>Description:</label>
                    <input type="text" value={description} onChange={(e) => setDescription(e.target.value)}/>
                </div>
                <div>
                    <label>Status:</label>
                    <select value={status} onChange={(e) => setStatus(e.target.value)}>
                        {offerStatuses.map((status) => (
                            <option key={status} value={status}>{status}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label>Customer:</label>
                    <input type="text" value={customer} onChange={(e) => setCustomer(e.target.value)}/>
                </div>
                <div>
                    <label>Supplier:</label>
                    <input type="text" value={supplier} onChange={(e) => setSupplier(e.target.value)}/>
                </div>
                <div>
                    <label>Workers (comma-separated IDs):</label>
                    <input type="text" value={workers.join(',')}
                           onChange={(e) => setWorkers(e.target.value.split(',').map(id => id.trim()))}/>
                </div>
                <button type="submit">Create Offer</button>
            </form>
            <button onClick={handleGoBack}>Go Back</button>
        </div>
    );
}

export default NewOffer;