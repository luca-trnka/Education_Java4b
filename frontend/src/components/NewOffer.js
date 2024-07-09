import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Multiselect from './Multiselect';

const NewOffer = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('NEW'); // Default status
    const [selectedCustomerId, setSelectedCustomerId] = useState('');
    const [selectedSupplierId, setSelectedSupplierId] = useState('');
    const [workerIds, setWorkerIds] = useState([]);
    const navigate = useNavigate();

    const offerStatuses = [
        "NEW", "ACCEPTED", "REJECTED", "IN_PROGRESS", "READY_TO_BE_SHOWN",
        "CUSTOMER_APPROVAL", "CUSTOMER_DISAPPROVAL", "FIXING_BUGS", "INVOICED", "COMPLETED"
    ];

    useEffect(() => {
        const fetchOffer = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    navigate('/login');
                    return;
                }
            } catch (error) {
                console.error('Fetch offer error:', error);
            }
        };

        fetchOffer();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token is not available');
            return;
        }
        const offerData = {
            title,
            description,
            status,
            customerId: selectedCustomerId,
            supplierId: selectedSupplierId,
            workerIds
        };

        console.log("Submitting offer data:", offerData);

        try {
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
                    <textarea value={description} onChange={(e) => setDescription(e.target.value)}/>
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
                    <Multiselect
                        entityType="customers"
                        selectedId={selectedCustomerId}
                        setSelectedId={setSelectedCustomerId}
                        isEditing={true}
                    />
                </div>
                <div>
                    <label>Supplier:</label>
                    <Multiselect
                        entityType="suppliers"
                        selectedId={selectedSupplierId}
                        setSelectedId={setSelectedSupplierId}
                        isEditing={true}
                    />
                </div>
                <div>
                    <label>Workers:</label>
                    <Multiselect
                        entityType="workers"
                        offer={{workerIds: workerIds}}
                        setOffer={(newOffer) => setWorkerIds(newOffer.workerIds)}
                        isEditing={true}
                    />
                    </div>
                    <button type="submit">Create Offer</button>
            </form>
            <button onClick={handleGoBack}>Go Back</button>
        </div>

    );
};


export default NewOffer;