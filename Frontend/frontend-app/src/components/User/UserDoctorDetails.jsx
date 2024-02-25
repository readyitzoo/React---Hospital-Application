import React, { useState, useEffect } from 'react';
import '../../css/ReceptionistListaDoctori.css';
import api from '../../api/api';

function UserDoctorDetails() {
    const [doctors, setDoctors] = useState([]);
    const [appointments, setAppointments] = useState({});

    useEffect(() => {
        fetchDoctors();
    }, []);

    const fetchDoctors = async () => {

        console.log('1. api.fetchAllDoctors()');
        const data = await api.fetchAllDoctors();
        console.log('1. api.fetchAllDoctors() resolved', {data});

        setDoctors(data);
            data.forEach(doctor => {
                fetchDoctorAppointments(doctor.userId);
            });
    };

    const fetchDoctorAppointments = async (doctorId) => {
        
        console.log('2. api.appointmentReadAllForDoctor(doctorId)');
        const data = await api.appointmentReadAllForDoctor(doctorId);
        console.log('2. api.appointmentReadAllForDoctor(doctorId) resolved', {data});

        setAppointments(prevAppointments => ({
            ...prevAppointments,
            [doctorId]: data
        }));
    };
    

    return (
        <div>
            <h1>Doctori și Programările lor</h1>
            <ul>
                {doctors.map(doctor => (
                    <li key={doctor.userId}>
                        <h2>Dr. {doctor.doctorInfo.lastName} {doctor.doctorInfo.firstName}</h2>
                        <ul>
                            {appointments[doctor.userId] && appointments[doctor.userId].map(appointment => (
                                <li key={appointment.appointmentId}>
                                    Programare: {appointment.appointmentDateTime}
                                </li>
                            ))}
                        </ul>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default UserDoctorDetails;
