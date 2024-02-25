import React, { useState, useEffect } from 'react';
import '../../css/UserAppointment.css';
import api from '../../api/api';

const UserAppointments = () => {
    const [medicalCenters, setMedicalCenters] = useState([]);
    const [selectedHospital, setSelectedHospital] = useState('');
    const [doctors, setDoctors] = useState([]);
    const [investigations, setInvestigations] = useState([]);
    const [selectedMedicalCenterId, setSelectedMedicalCenterId] = useState(null);
    const [selectedDoctorId, setSelectedDoctorId] = useState(null);
    const [selectedInvestigationId, setSelectedInvestigationId] = useState(null);


    const fetchMedicalCenters = async () => {

        console.log('api.fetchMedicalCenters')
        const data = await api.fetchMedicalCenters();
        console.log('resolved');

        setMedicalCenters(data);
    };

    useEffect(() => {
        fetchMedicalCenters();
    }, []);

    const fetchDoctorsByHospital = async (hospitalName) => {

        console.log('api.fetchDoctorsByHospital')
        const data = await api.fetchDoctorsByHospital(hospitalName);
        console.log('resolved');

        setDoctors(data);
    };

    const fetchInvestigationsByDoctor = async (doctorId) => {

        console.log('api.investigationsReadAllbyDoctor')
        const data = await api.investigationsReadAllbyDoctor(doctorId);
        console.log('resolved');

        setInvestigations(data);
    };

    const handleHospitalSelect = (centerName, centerId) => {
        setSelectedHospital(centerName);
        fetchDoctorsByHospital(centerName);
        setSelectedMedicalCenterId(centerId);
    };
    

    const handleDoctorSelect = (doctorId) => {
        fetchInvestigationsByDoctor(doctorId);
        setSelectedDoctorId(doctorId);
    };

    const handleInvestigationSelect = (investigationId) => {
        setSelectedInvestigationId(investigationId);
    };
    

    const handleCreateAppointment = async () => {

        const user = JSON.parse(sessionStorage.getItem('user'));
        const id = user.user_id;

        const appointmentData = {
            medicalCenterId: selectedMedicalCenterId,
            patientId: id,
            doctorId: selectedDoctorId,
            investigationId: selectedInvestigationId
        };

        try {
            console.log('api.appointmentByPatient(appointmentData)');
            const data = await api.appointmentByPatient(appointmentData);
            console.log('resolved');

            console.log('Appointment created:', data);
        } catch (error) {

            alert('Programarea a fost plasata cu succes!');
            window.location.href = 'http://localhost:3000/UserNavbar';
        }
    };


    return (
        
        <div className="container_user">
        <div className="user_appointments">
            <h1>Spitale Disponibile</h1>
            <ul>
                {medicalCenters.map(center => (
                    <li className="horizontal-layout" key={center.medicalCenterId}>
                        <div className="squaredOne">
                            <input 
                                type="checkbox" 
                                id={`hospital_checkbox_${center.medicalCenterId}`} 
                                name="hospital_check" 
                                onChange={() => handleHospitalSelect(center.name, center.medicalCenterId)}
                            />
                            <label htmlFor={`hospital_checkbox_${center.medicalCenterId}`}></label>
                        </div>
                        {center.name} - {center.location}
                    </li>
                ))}
            </ul>

            {selectedHospital && (
                <>
                    <h1>Doctori la {selectedHospital}</h1>
                    <ul>
                        {doctors.map(doctor => (
                            <li key={doctor.userId}>
                                <div className="squaredOne">
                                    <input 
                                        type="checkbox" 
                                        id={`doctor_checkbox_${doctor.userId}`} 
                                        name="doctor_check" 
                                        onChange={() => handleDoctorSelect(doctor.userId)}
                                    />
                                    <label htmlFor={`doctor_checkbox_${doctor.userId}`}></label>
                                </div>
                                Dr. {doctor.doctorInfo.lastName} {doctor.doctorInfo.firstName}
                            </li>
                        ))}
                    </ul>
                    <h1>Investigații Disponibile</h1>
                </>
            )}

            
            <ul>
                {investigations.map(investigation => (
                    <li key={investigation.investigationId}>
                        <div className="squaredOne">
                        <input 
                            type="checkbox" 
                            id={`investigation_checkbox_${investigation.investigationId}`} 
                            name="investigation_check" 
                            onChange={() => handleInvestigationSelect(investigation.investigationId)}
                        />

                            <label htmlFor={`investigation_checkbox_${investigation.investigationId}`}></label>
                        </div>
                        {investigation.investigationName} - {investigation.price} RON
                    </li>
                ))}
                <button onClick={handleCreateAppointment}>Creează Programare</button>

            </ul>
        </div>
    </div>
    );
};

export default UserAppointments;
