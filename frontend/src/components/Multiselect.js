import React, { useEffect, useState } from 'react';
import axios from 'axios';

function MultiSelect({ entityType, offer, setOffer, selectedId, setSelectedId, isEditing }) {
    const [entities, setEntities] = useState([]);
    const token = localStorage.getItem('token');

    const fetchEntities = async () => {
        let url = 'http://localhost:8080/api/users/';
        switch (entityType) {
            case 'workers':
                url += 'workers';
                break;
            case 'customers':
                url += 'customers';
                break;
            case 'suppliers':
                url += 'suppliers';
                break;
            default:
                console.error('Unknown entity type');
                return;
        }

        try {
            const response = await axios.get(url, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error(`Error fetching ${entityType}:`, error);
            return [];
        }
    };

    useEffect(() => {
        fetchEntities().then(setEntities);
    }, [entityType]);

    const handleChange = (e) => {
        if (entityType === 'customers' || entityType === 'suppliers') {
            setSelectedId(e.target.value);
        } else if (entityType === 'workers') {
            const selectedOptions = Array.from(e.target.selectedOptions, option => Number(option.value));
            setOffer({...offer, workerIds: selectedOptions});
        } else {
            setSelectedId(e.target.value);
        }
    };

    return (
        isEditing ? (
            <select multiple={entityType === 'workers'} value={entityType === 'workers' ? offer.workerIds : selectedId}
                    onChange={handleChange}>
                {entities.map(entity =>
                    <option key={entity.id} value={entity.id}>{entity.name}</option>
                )}
            </select>
        ) : (
            entityType === 'workers' ? offer.workerIds.join(', ') : selectedId
        )
    );
}

export default MultiSelect;