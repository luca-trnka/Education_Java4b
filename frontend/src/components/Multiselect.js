import React, { useEffect, useState } from 'react';
import axios from 'axios';

function MultiSelect({ offer, setOffer, isEditing }) {
    const [workers, setWorkers] = useState([]);
    const token = localStorage.getItem('token');
    const fetchWorkers = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/users/workers', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
            return response.data;
        } catch (error) {
            console.error('Error fetching workers:', error);
            return [];
        }
    };

    useEffect(() => {
        fetchWorkers().then(setWorkers);
    }, []);

    return (
            isEditing ?
                <select multiple={true} value={offer.workerIds}
                        onChange={(e) => {
                            const selectedOptions = Array.from(e.target.selectedOptions, option => Number(option.value));
                            setOffer({...offer, workerIds: selectedOptions})
                        }}>
                    {workers.map(worker =>
                        <option key={worker.id} value={worker.id}>{worker.name}</option>
                    )}
                </select>
                : offer.workerIds.join(', ')

    );
}

export default MultiSelect;