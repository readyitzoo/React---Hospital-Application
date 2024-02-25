import React, { useState, useEffect } from 'react';
import api from '../../api/api'

//CHECKED

function DoctorAppointments() {
    const [appointments, setAppointments] = useState([]);
    const [patients, setPatients] = useState({});

    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = async () => {

        //CHECKED
        console.log('1. await api.appointmentReadAllForCurrentDoctor();');
        const data = await api.appointmentReadAllForCurrentDoctor();
        console.log('1. resolved', {data});

        setAppointments(data);
            data.forEach(appointment => {
                fetchPatientDetails(appointment.patientId);
            });
    };

    const fetchPatientDetails = async (patientId) => {

        if (!patients[patientId]) {
            //CHECKED
            console.log('2. api.fetchPatientDataById(patientId);');
            const data = await api.fetchPatientDataById(patientId);
            console.log('2. resolved', {data});

            setPatients(prevPatients => ({
                ...prevPatients,
                [patientId]: data.patientInfo
            }));
        }
    };

    return (
        <div>
            <h1>Programări Doctor</h1>
            <ul>
                {appointments.map(appointment => (
                    <li key={appointment.appointmentId}>
                        <div>Data Programării: {appointment.appointmentDateTime}</div>
                        {patients[appointment.patientId] && (
                            <div>Pacient: {patients[appointment.patientId].lastName} {patients[appointment.patientId].firstName}</div>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default DoctorAppointments;
