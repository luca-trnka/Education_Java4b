import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [offers, setOffers] = useState([]);
    const [userSort, setUserSort] = useState('id');
    const [offerSort, setOfferSort] = useState('id');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsersAndOffers = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                const usersResponse = await axios.get('http://localhost:8080/api/users/all', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const sortedUsers = usersResponse.data.sort((a, b) => a.id - b.id);
                setUsers(sortedUsers);

                const offersResponse = await axios.get('http://localhost:8080/api/offers/all', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                const sortedOffers = offersResponse.data.sort((a, b) => a.id - b.id);
                setOffers(sortedOffers);

            } catch (error) {
                console.error('Fetch users and offers error:', error);
            }
        };

        fetchUsersAndOffers();
    }, []);

    const handleUserSortChange = (selectedOption) => {
        const currentUsers = [...users];
        const sortedUsers = currentUsers.sort((a, b) => {
            switch (selectedOption) {
                case 'id':
                    return a.id - b.id;
                case 'name':
                    return a.name.localeCompare(b.name);
                case 'role':
                    return a.role.localeCompare(b.role);
                default:
                    return 0;
            }
        });

        setUsers(sortedUsers);
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
                const updatedUsers = users.filter(user => user.id !== userId);
                setUsers(updatedUsers);
                alert('User deleted successfully');
            } catch (error) {
                console.error('Error deleting user:', error);
                alert('Error deleting user');
            }
        }
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
            } catch (error) {
                console.error('Delete offer error:', error);
            }
        }
    };

    const handleLogoutClick = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    return (
        <div>
            <h1>Admin Dashboard</h1>
            <h2>All users:</h2>
            <label>
                Sort by:
                <select value={userSort} onChange={e => {
                    setUserSort(e.target.value);
                    handleUserSortChange(e.target.value);
                }}>
                    <option value="id">ID</option>
                    <option value="name">Name</option>
                    <option value="role">Role</option>
                </select>
            </label>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.role}</td>
                        <td>
                            <button onClick={() => handleEditUser(user.id)}>Edit</button>
                            <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
                <button onClick={() => navigate('/user-profile/new')}>Add New</button>
            </table>

            <h2>All offers:</h2>
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
                    <th>Supplier ID</th>
                    <th>Worker ID</th>
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
                        <td>{offer.supplier ? offer.supplier.id : 'N/A'}</td>
                        <td>{offer.workers ? offer.workers.map(worker => worker.id).join(', ') : 'N/A'}</td>
                        <td>
                            <button onClick={() => handleEditOffer(offer.id)}>Edit</button>
                            <button onClick={() => handleDeleteOffer(offer.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
                <button onClick={() => navigate('/offer-profile/new')}>Add New</button>
            </table>

            <button onClick={handleLogoutClick}>Log out</button>
        </div>
    );
}

export default AdminDashboard;