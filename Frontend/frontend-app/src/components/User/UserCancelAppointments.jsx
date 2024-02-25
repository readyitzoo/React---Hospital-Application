import React, { useState, useEffect } from 'react';
import '../../css/ReceptionistListaDoctori.css';
import api from '../../api/api';

function UserCancelAppointments() {
    const [appointments, setAppointments] = useState([]);
    const [patientsDetails, setPatientsDetails] = useState({});
    const [medicalCenters, setMedicalCenters] = useState({});
    const [doctorsDetails, setDoctorsDetails] = useState({});
    const [investigationsDetails, setInvestigationsDetails] = useState({});
    const [update, setUpdate] = useState(0);
    
    //UNUSED
    const [doctorAppointments, setDoctorAppointments] = useState([]);
    const [newAppointmentDateTime, setNewAppointmentDateTime] = useState('');

    useEffect(() => {
        fetchMedicalCenters();
        fetchAppointments();
        
    }, []);

    const fetchMedicalCenters = async () => {

        console.log('1. api.fetchMedicalCenters()');
        const data = await api.fetchMedicalCenters();
        console.log('1. resolved', {data});


        const centers = data.reduce((acc, center) => {
                acc[center.medicalCenterId] = center.name;
                return acc;
            }, {});

        setMedicalCenters(centers);

    };

    const fetchAppointments = async () => {

        console.log('2. api.appointmentReadForPacient()');
        const data = await api.appointmentReadForPacient();
        console.log('2. api.appointmentReadForPacient() resolved', {data});

        data.forEach(appointment => {
                    fetchPatientDetails(appointment.patientId);
                    fetchDoctorDetails(appointment.doctorId);
                    fetchInvestigations(appointment.doctorId);
                    // fetchDoctorAppointments(appointment.doctorId);
                });
        setAppointments(data);
    };

    //UNUSED
    const handleCreateAppointment = async (appointmentId) => {

        console.log('3. api.appointmentCreateByReceptionist(newAppointmentDateTime, appointmentId)');
        const data = await api.appointmentCreateByReceptionist(newAppointmentDateTime, appointmentId);
        console.log('3. resolved', {data});

        console.log('Appointment created:', data);

    };

    const fetchInvestigations = async (doctorId) => {

            console.log('4. api.investigationsReadAllbyDoctor(doctorId)');
            const data = await api.investigationsReadAllbyDoctor(doctorId);
            console.log('4. resolved', {data});

            const newInvestigations = data.reduce((acc, investigation) => {
                        acc[investigation.investigationId] = investigation.investigationName;
                        return acc;
                    }, {});
                    setInvestigationsDetails(prev => ({
                        ...prev,
                        ...newInvestigations
                    }));

    };
    
    
    const fetchDoctorDetails = async (doctorId) => {

        console.log('5. api.fetchDoctorDataById(doctorId)');
        const data = await api.fetchDoctorDataById(doctorId);
        console.log('5. resolved', {data});
        
        setDoctorsDetails(prev => ({
            ...prev,
            [doctorId]: data.doctorInfo
        }));

    };

    const fetchPatientDetails = async (patientId) => {

        console.log('6. api.fetchPatientDataById(patientId)')
        const data = await api.fetchPatientDataById(patientId);
        console.log('6. resolved', {data});

        setPatientsDetails(prevDetails => ({
        ...prevDetails,
        [patientId]: data.patientInfo
        }));
    };


    const handleCancelAppointment = async (appointmentId) => {

        try{
        console.log('7. api.appointmentDeleteById(appointmentId)');
        await api.appointmentDeleteById(appointmentId);
        console.log('7. resolved');
        }
        catch(error)
        {
            alert('Programare anulata cu succes!');
            setUpdate(prev => !prev);
        }

    };

    return (
        <div className="appointments_recep">
            <h1>Programări</h1>
            {appointments.length > 0 ? (
                <ul>
                    {appointments.map(appointment => (
                        <li key={appointment.appointmentId}>
                            <div>Spital: {medicalCenters[appointment.medicalCenterId]}</div>
                            <div>Doctor: {doctorsDetails[appointment.doctorId] ? `${doctorsDetails[appointment.doctorId].lastName} ${doctorsDetails[appointment.doctorId].firstName}` : 'Se încarcă...'}</div>
                            <div>Investigație: {investigationsDetails[appointment.investigationId] || 'Se încarcă...'}</div>
                            <div>Pacient: {patientsDetails[appointment.patientId] ? `${patientsDetails[appointment.patientId].lastName} ${patientsDetails[appointment.patientId].firstName}` : 'Se încarcă...'}</div>
                            <button onClick={() => handleCancelAppointment(appointment.appointmentId)}>Anulează Programare</button>
                        </li>
                    ))}

                </ul>
            ) : (
                <p>Nu există programări în așteptare.</p>
            )}

                
        </div>
    );
}

export default UserCancelAppointments;
