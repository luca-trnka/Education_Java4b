import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';

const UserProfile = () => {
    const [user, setUser] = useState({});
    const [roles, setRoles] = useState([]);
    const [selectedRole, setSelectedRole] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const {userId} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                const response = await axios.get(`http://localhost:8080/api/users/${userId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setUser(response.data);
                setRoles(['NEW_USER', 'CUSTOMER', 'SUPPLIER', 'WORKER', 'ADMIN']);
                setSelectedRole(response.data.role);
            } catch (error) {
                console.error('Fetch user error:', error);
            }
        };

        fetchUser();
    }, [userId]);

    const handleSaveChanges = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                console.error('Token is not available');
                return;
            }
            const response = await axios.put(`http://localhost:8080/api/users/${userId}`, {
                id: user.id,
                email: user.email,
                name: user.name,
                password: user.password,
                role: selectedRole
            }, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setUser(response.data);
            setIsEditing(false);
        } catch (error) {
            console.error('Update user error:', error);
        }
    };

    const handleGoBack = () => {
        navigate('/dashboard');
    };

    return (
        <div>
            <h2>User Profile</h2>
            <p>Email: {isEditing ? <input type="email" value={user.email} onChange={(e) => setUser({
                ...user,
                email: e.target.value
            })}/> : user.email}</p>
            <p>Name: {isEditing ? <input type="text" value={user.name}
                                         onChange={(e) => setUser({...user, name: e.target.value})}/> : user.name}</p>
            <p>Role:&nbsp;
                {isEditing ? (
                    <select value={selectedRole} onChange={(e) => setSelectedRole(e.target.value)}>
                        {roles.map(role => (
                            <option key={role} value={role}>{role}</option>
                        ))}
                    </select>
                ) : (
                    <span>{selectedRole}</span>
                )}
            </p>
            <button onClick={isEditing ? handleSaveChanges : () => setIsEditing(true)}>
                {isEditing ? 'Save' : 'Edit'}
            </button>
            <p>
                <button onClick={handleGoBack}>Go Back</button>
            </p>
        </div>
    );
}

export default UserProfile;