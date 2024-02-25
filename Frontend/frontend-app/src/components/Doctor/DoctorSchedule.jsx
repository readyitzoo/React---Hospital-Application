import React, { useState, useEffect } from 'react';
import api from '../../api/api';

function DoctorAvailability() {
    const [availability, setAvailability] = useState([]);
    const [updatedTimes, setUpdatedTimes] = useState({});
    const [update, setUpdate] = useState(0);

    useEffect(() => {
        fetchAvailability();
    }, []);

    //CHECKED

    const fetchAvailability = async () => {
        try {

            //CHECKED
            console.log('1. api.availabilityForCurrentDoctor();')
            const data = await api.availabilityForCurrentDoctor();
            console.log('1. resolved', {data});

            setAvailability(data);

            const initialTimes = data.reduce((acc, slot) => {
                acc[slot.availabilityId] = { timeStart: slot.timeStart, timeEnd: slot.timeEnd };
                return acc;
            }, {});
            setUpdatedTimes(initialTimes);
        } catch (error) {
            console.error("Error fetching availability:", error);
        }
    };

    const handleTimeChange = (availabilityId, field, value) => {
        setUpdatedTimes({
            ...updatedTimes,
            [availabilityId]: {
                ...updatedTimes[availabilityId],
                [field]: value
            }
        });
    };

    const handleUpdate = async (availabilityId, day) => {
        const { timeStart, timeEnd } = updatedTimes[availabilityId];

        const user = JSON.parse(sessionStorage.getItem('user'));
        const doctorId = user.user_id;

        const updatedAvailability = {
            doctorId,
            day: day,
            timeStart: timeStart,
            timeEnd: timeEnd
        };

        try {
            
            //CHECKED
            console.log('2. api.availabilitySendForApproval([updatedAvailability]);');
            const data = await api.availabilitySendForApproval([updatedAvailability]);
            console.log('2. resolved', {data});

            console.log('Update sent for approval:', data);
            fetchAvailability();
        } catch (error) {
            console.error("Error sending update for approval:", error);
            alert('Cererea pentru program a fost plasata cu succes!');
            setUpdate(prev => !prev);
        }
    };

    return (
        <div>
            <h1>Disponibilitate Doctor</h1>
            <ul>
                {availability.map((slot) => (
                    <li key={slot.availabilityId}>
                        <div>Zi: {slot.day}</div>
                        <input
                            type="text"
                            value={updatedTimes[slot.availabilityId]?.timeStart || ''}
                            onChange={(e) => handleTimeChange(slot.availabilityId, 'timeStart', e.target.value)}
                            placeholder="Noua oră de început"
                        />
                        <input
                            type="text"
                            value={updatedTimes[slot.availabilityId]?.timeEnd || ''}
                            onChange={(e) => handleTimeChange(slot.availabilityId, 'timeEnd', e.target.value)}
                            placeholder="Noua oră de sfârșit"
                        />
                        <button onClick={() => handleUpdate(slot.availabilityId, slot.day)}>Actualizează</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default DoctorAvailability;
