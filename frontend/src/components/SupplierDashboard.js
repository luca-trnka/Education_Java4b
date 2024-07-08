import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SupplierDashboard = () => {
    const [workers, setWorkers] = useState([]);
    const [offers, setOffers] = useState([]);
    const [userSort, setUserSort] = useState('id');
    const [offerSort, setOfferSort] = useState('id');
    const navigate = useNavigate();
    const supplierId = localStorage.getItem('supplierId');

    useEffect(() => {
        const fetchWorkersAndOffers = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                const workersResponse = await axios.get('http://localhost:8080/api/users/workers', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const sortedWorkers = workersResponse.data.sort((a, b) => a.id - b.id);
                setWorkers(sortedWorkers);

                const offersResponse = await axios.get(`http://localhost:8080/api/offers/suppliers-offers`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const sortedOffers = offersResponse.data.sort((a, b) => a.id - b.id);
                setOffers(sortedOffers);

            } catch (error) {
                console.error('Fetch workers and offers error:', error);
            }
        };

        fetchWorkersAndOffers();
    }, [supplierId]);

    const handleUserSortChange = (selectedOption) => {
        const currentWorkers = [...workers];
        const sortedWorkers = currentWorkers.sort((a, b) => {
            switch (selectedOption) {
                case 'id':
                    return a.id - b.id;
                case 'name':
                    return a.name.localeCompare(b.name);
                default:
                    return 0;
            }
        });

        setWorkers(sortedWorkers);
    };

    const handleOfferSortChange = (selectedOption) => {
        const currentOffers = [...offers];
        const sortedOffers = currentOffers.sort((a, b) => {
            switch (selectedOption) {
                case 'id':
                    return a.id - b.id;
                case 'title':
                    return a.title.localeCompare(b.title);
                case 'customer':
                    return a.customer.id - b.customer.id;
                default:
                    return a.id - b.id;
            }
        });

        setOffers(sortedOffers);
    };
    const handleAddWorker = () => {
        navigate('/user-profile/new'); // Adjust the route as necessary
    };

    const handleAddOffer = () => {
        navigate('/offer-profile/new'); // Adjust the route as necessary, pre-fill supplier ID if possible
    };

    const handleEditOffer = (offerId) => {
        navigate(`/offer-profile/${offerId}`);
    };

    const handleDeleteOffer = async (offerId) => {
        if (window.confirm('Are you sure you want to delete this offer?')) {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                await axios.delete(`http://localhost:8080/api/offers/${offerId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setOffers(offers.filter(offer => offer.id !== offerId));
                alert('Offer deleted successfully');
            } catch (error) {
                console.error('Delete offer error:', error);
                alert('Error deleting offer');
            }
        }
    };

    const handleEditUser = (userId) => {
        navigate(`/user-profile/${userId}`);
    };

    const handleDeleteUser = async (userId) => {
        if (window.confirm('Are you sure you want to delete this user?')) {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                await axios.delete(`http://localhost:8080/api/users/${userId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const updatedUsers = workers.filter(user => user.id !== userId);
                setWorkers(updatedUsers);
                alert('User deleted successfully');
            } catch (error) {
                console.error('Error deleting user:', error);
                alert('Error deleting user');
            }
        }
    };
    const handleLogoutClick = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    return (
        <div>
            <h1>Supplier Dashboard</h1>
            <h2>All Workers:</h2>
            <label>
                Sort by:
                <select value={userSort} onChange={e => {
                    setUserSort(e.target.value);
                    handleUserSortChange(e.target.value);
                }}>
                    <option value="id">ID</option>
                    <option value="name">Name</option>
                </select>
            </label>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {workers.map(worker => (
                    <tr key={worker.id}>
                        <td>{worker.id}</td>
                        <td>{worker.name}</td>
                        <td>{worker.email}</td>
                        <td>
                            <button onClick={() => handleEditUser(worker.id)}>Edit</button>
                            <button onClick={() => handleDeleteUser(worker.id)}>Delete</button>                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <button onClick={handleAddWorker}>Add New</button>

            <h2>My Offers:</h2>
            <label>
                Sort by:
                <select value={offerSort} onChange={e => {
                    setOfferSort(e.target.value);
                    handleOfferSortChange(e.target.value);
                }}>
                    <option value="id">ID</option>
                    <option value="title">Title</option>
                    <option value="customer">Customer</option>
                </select>
            </label>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Customer ID</th>
                    <th>Workers ID</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {offers.map(offer => (
                    <tr key={offer.id}>
                        <td>{offer.id}</td>
                        <td>{offer.title}</td>
                        <td>{offer.status}</td>
                        <td>{offer.customer ? offer.customer.id : 'N/A'}</td>
                        <td>{offer.workers ? offer.workers.map(worker => worker.id).join(', ') : 'N/A'}</td>
                        <td>
                            <button onClick={() => handleEditOffer(offer.id)}>Edit</button>
                            <button onClick={() => handleDeleteOffer(offer.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
                <button onClick={handleAddOffer}>Add New</button>
            </table>

            <button onClick={handleLogoutClick}>Log out</button>
        </div>
    );
};

export default SupplierDashboard;