import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import Multiselect from './Multiselect';

const OfferProfile = () => {
    const [offer, setOffer] = useState({});
    const [statuses, setStatuses] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('');
    const [selectedSupplierId, setSelectedSupplierId] = useState('');
    const [selectedCustomerId, setSelectedCustomerId] = useState('');
    const [workerIds, setWorkerIds] = useState([]);
    const [isEditing, setIsEditing] = useState(false);
    const {offerId} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchOffer = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error('Token is not available');
                    return;
                }
                const response = await axios.get(`http://localhost:8080/api/offers/${offerId}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                const offerData = response.data;
                offerData.customerId = offerData.customer?.id;
                offerData.supplierId = offerData.supplier?.id;
                offerData.workerIds = offerData.workers?.map(worker => worker.id);
                setOffer(offerData);
                setSelectedCustomerId(offerData.customerId);
                setSelectedSupplierId(offerData.supplierId);
                setStatuses(['NEW', 'ACCEPTED', 'REJECTED', 'IN_PROGRESS', 'READY_TO_BE_SHOWN', 'CUSTOMER_APPROVAL', 'CUSTOMER_DISAPPROVAL', 'FIXING_BUGS', 'INVOICED', 'PAID', 'COMPLETED']);
                setSelectedStatus(response.data.status);
            } catch (error) {
                console.error('Fetch offer error:', error);
            }
        };

        fetchOffer();
    }, [offerId]);

    const handleSaveChanges = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                console.error('Token is not available');
                return;
            }
            const offerDTO = {
                id: offer.id,
                title: offer.title,
                description: offer.description,
                status: selectedStatus,
                supplierId: selectedSupplierId,
                customerId: selectedCustomerId,
                workerIds: offer.workerIds
            };
            console.log(offerDTO);
            const response = await axios.put(`http://localhost:8080/api/offers/${offerId}`, offerDTO, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setOffer(response.data);
            setIsEditing(false);
        } catch (error) {
            console.error('Update offer error:', error);
        }
    };

    const handleGoBack = () => {
        navigate('/dashboard');
    };

    return (
        <div>
            <h2>Offer Profile</h2>
            <p>Title: {isEditing ? <input type="text" value={offer.title} onChange={(e) => setOffer({
                ...offer,
                title: e.target.value
            })}/> : offer.title}</p>
            <p>Description: {isEditing ? <textarea value={offer.description} onChange={(e) => setOffer({
                ...offer,
                description: e.target.value
            })}/> : offer.description}</p>
            <p>Status:&nbsp;
                {isEditing ? (
                    <select value={selectedStatus} onChange={(e) => setSelectedStatus(e.target.value)}>
                        {statuses.map(status => (
                            <option key={status} value={status}>{status}</option>
                        ))}
                    </select>
                ) : (
                    <span>{selectedStatus}</span>
                )}
            </p>
            <p>Customer ID:
                {isEditing ?
                    <Multiselect entityType="customers" selectedId={selectedCustomerId} setSelectedId={setSelectedCustomerId} isEditing={isEditing}/>
                    : selectedCustomerId}
            </p>
            <p>Supplier ID:
                {isEditing ?
                    <Multiselect entityType="suppliers" selectedId={selectedSupplierId} setSelectedId={setSelectedSupplierId} isEditing={isEditing}/>
                    : selectedSupplierId}
            </p>
            <p>Worker IDs:
                {isEditing ?
                    <Multiselect entityType="workers" offer={offer} setOffer={setOffer} isEditing={isEditing}/>
                    : offer.workerIds && offer.workerIds.join(', ')
                }
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

export default OfferProfile;