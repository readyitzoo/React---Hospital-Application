import React, { useState, useEffect } from 'react';
import '../../css/ReceptionistListaDoctori.css';
import api from '../../api/api';

function ReceptionistAppointments() {
    const [appointments, setAppointments] = useState([]);
    const [patientsDetails, setPatientsDetails] = useState({});
    const [medicalCenters, setMedicalCenters] = useState({});
    const [doctorsDetails, setDoctorsDetails] = useState({});
    const [investigationsDetails, setInvestigationsDetails] = useState({});
    const [doctorAppointments, setDoctorAppointments] = useState([]);
    const [newAppointmentDateTime, setNewAppointmentDateTime] = useState('');

    useEffect(() => {
        fetchMedicalCenters();
        fetchAppointments();
        // fetchDoctorAppointments(appointment.doctorId);
    }, []);

    const fetchMedicalCenters = async () => {

        console.log(`1. api.fetchMedicalCenters()`)
        const data = await api.fetchMedicalCenters();
        console.log(`1. resolved`, {data})

        const centers = data.reduce((acc, center) => {
            acc[center.medicalCenterId] = center.name;
            return acc;
        }, {});

        setMedicalCenters(centers);

    };

    const fetchAppointments = async () => {

        console.log('2. api.appointmentReadAllForReceptionist()')
        const data = await api.appointmentReadAllForReceptionist();
        console.log('2. resolved', {data});

        setAppointments(data);

        data.forEach(appointment => {
            fetchPatientDetails(appointment.patientId);
            fetchDoctorDetails(appointment.doctorId);
            fetchInvestigations(appointment.doctorId);
            fetchDoctorAppointments(appointment.doctorId);
        });

    };
    

    // CREATE NEW APPOINTMENT
    const handleCreateAppointment = async (appointmentId) => {
        try {
            console.log({newAppointmentDateTime})
            console.log('3. api.appointmentCreateByReceptionist(newAppointmentDateTime, appointmentId);')
            const data = await api.appointmentCreateByReceptionist(newAppointmentDateTime, appointmentId);
            console.log('3. resolved', {data});

            console.log('Appointment created:', data);
            
        } catch (error) {
            console.error('Error creating appointment:', error);
        }
    };
    


    const fetchDoctorAppointments = async (doctorId) => {

        console.log('4. api.appointmentReadAllForDoctor(doctorId);')
        const data = await api.appointmentReadAllForDoctor(doctorId);
        console.log('4. resolved', {data});

        if (Array.isArray(data)) {
            setDoctorAppointments(data);
        } 
        else {
            console.error('Data received is not an array:', data);
            setDoctorAppointments([]); 
        }
    };
    
    


    const fetchInvestigations = async (doctorId) => {

        console.log('5. api.investigationsReadAllbyDoctor(doctorId);')
        const data = await api.investigationsReadAllbyDoctor(doctorId);
        console.log('5. resolved', {data});

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
        if (!doctorsDetails[doctorId]) {
            
            console.log('6. api.fetchDoctorDataById(doctorId);')
            const data = await api.fetchDoctorDataById(doctorId);
            console.log('6. resolved', {data});

            setDoctorsDetails(prev => ({
                        ...prev,
                        [doctorId]: data.doctorInfo
                    }));
        }
    };

    const fetchPatientDetails = async (patientId) => {
        
        console.log('7. api.fetchPatientDataById(patientId);')
        const data = await api.fetchPatientDataById(patientId);
        console.log('7. resolved', {data});

        setPatientsDetails(prevDetails => ({
                    ...prevDetails,
                    [patientId]: data.patientInfo
                }));
    };

    return (
        <div className="appointments_recep">
            <h1>Programări</h1>
            {appointments.length > 0 ? (
                <ul>
                    {appointments.map(appointment => (
                        <li key={appointment.waitingAppointmentId}>
                            <div>Spital: {medicalCenters[appointment.medicalCenterId]}</div>
                            <div>Doctor: {doctorsDetails[appointment.doctorId] ? `${doctorsDetails[appointment.doctorId].firstName} ${doctorsDetails[appointment.doctorId].lastName}` : 'Se încarcă...'}</div>
                            <div>Investigation: {investigationsDetails[appointment.investigationId] || 'Se încarcă...'}</div>
                            <div>Pacient: {patientsDetails[appointment.patientId] ? `${patientsDetails[appointment.patientId].lastName} ${patientsDetails[appointment.patientId].firstName}` : 'Se încarcă...'}</div>
                            <div className="doctor-appointments">
                                <h1>Programările Doctorului</h1>
                                {doctorAppointments.map(appointment => (
                                    <div key={appointment.appointmentId}>
                                        Data și ora programării: {appointment.appointmentDateTime}
                                    </div>
                                ))}
                            </div>
                            <div className="appointment-input">
                                <input 
                                    type="datetime-local" 
                                    value={newAppointmentDateTime}
                                    onChange={e => setNewAppointmentDateTime(e.target.value)}
                                />
                                <button 
                                    onClick={() => handleCreateAppointment(appointment.waitingAppointmentId)}
                                >
                                    Stabilește Programare
                                </button>
                            </div>
                        </li>
                        
                    ))}
                </ul>
            ) : (
                <p>Nu există programări în așteptare.</p>
            )}
                
        </div>
    );
}

export default ReceptionistAppointments;
