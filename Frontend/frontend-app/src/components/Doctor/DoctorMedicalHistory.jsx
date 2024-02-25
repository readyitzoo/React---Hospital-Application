import React, { useState, useEffect } from 'react';
import api from '../../api/api';

function DoctorMedicalHistory() {
    const [appointments, setAppointments] = useState([]);
    const [patients, setPatients] = useState({});
    const [medicalHistories, setMedicalHistories] = useState({});
    const [updateData, setUpdateData] = useState({});
    const [update, setUpdate] = useState(0);
    const user = JSON.parse(sessionStorage.getItem('user'));
    const doctorId = user.user_id;;

    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = async () => {

        //CHECKED
        console.log('1. api.appointmentReadAllForCurrentDoctor();')
        const data = await api.appointmentReadAllForCurrentDoctor();
        console.log('1. resolved', {data});

        setAppointments(data);
            data.forEach(appointment => {
                fetchPatient(appointment.patientId);
                fetchMedicalHistory(appointment.patientId);
            });

    };

    const fetchPatient = async (patientId) => {

        //CHECKED
        console.log('2. api.fetchPatientDataById(patientId);')
        const data = await api.fetchPatientDataById(patientId);
        console.log('2. resolved', {data});

        setPatients(prevPatients => ({
            ...prevPatients,
            [patientId]: data.patientInfo
        }));
    };

    
const fetchMedicalHistory = async (patientId) => {
    try {
        
        //CHECKED
        console.log('3. api.fetchMedicalHistoryById(patientId);');
        const data = await api.fetchMedicalHistoryById(patientId);
        console.log('3. resolved', {data});

        setMedicalHistories(prevHistories => ({
            ...prevHistories,
            [patientId]: data
        }));
        data.forEach(history => {
            setUpdateData(prevUpdateData => ({
                ...prevUpdateData,
                [history.medicalHistoryId]: {
                    patientId: patientId,
                    doctorId: doctorId,
                    diagnostic: history.diagnostic,
                    treatment: history.treatment,
                    pdfDocument: history.pdfDocument
                }
            }));
        });
    } catch (error) {
        console.error(`Error fetching medical history for patient ID ${patientId}:`, error);
    }
};

    const handleUpdateChange = (medicalHistoryId, field, value) => {
        setUpdateData({
            ...updateData,
            [medicalHistoryId]: {
                ...updateData[medicalHistoryId],
                [field]: value
            }
        });
    };

    const handleUpdateMedicalHistory = async (medicalHistoryId) => {
        const updateInfo = updateData[medicalHistoryId];
        if (!updateInfo) {
            console.error('No update information found for medical history ID:', medicalHistoryId);
            return;
        }
    
        try {
            
            //CHECKED
            try{
            console.log("4. api.medicalHistoryUpdate(medicalHistoryId, updateInfo);")
            const data = await api.medicalHistoryUpdate(medicalHistoryId, updateInfo);
            console.log("4. resolved", {data});

            console.log('Medical history updated:', data);
            }
            catch(error)
            {
                alert('Istoricul medical a fost modificat cu succes!');
                setUpdate(prev => !prev);
            }

            fetchMedicalHistory(updateInfo.patientId); 
        } catch (error) {
            console.error("Error updating medical history:", error);
        }
    };

    const updateUpdateData = (key, value) => {
        setUpdateData(prevData => ({
            ...prevData,
            [key]: value
        }));
    };

    const handleCreateMedicalHistory = async (appointment) => {
        const newHistoryData = {
            patientId: appointment.patientId,
            doctorId: appointment.doctorId,
            diagnostic: updateData[`diagnostic_new_${appointment.appointmentId}`],
            treatment: updateData[`treatment_new_${appointment.appointmentId}`],
            pdfDocument: updateData[`pdfDocument_new_${appointment.appointmentId}`]
        };
        
        try {
            console.log({newHistoryData});

            //DOENS'T WORK
            console.log('5. api.medicalHistoryCreate(newHistoryData);')
            const data = await api.medicalHistoryCreate(newHistoryData);
            console.log('5. resolved', {data});

            console.log('New medical history created:', data);

            fetchMedicalHistory(appointment.patientId); 

        } catch (error) {
            console.error("Error creating new medical history:", error);
        }
    };

    return (
        <div>
            <h1>Programări Doctor</h1>
            {appointments.map(appointment => (
                <div key={appointment.appointmentId}>
                    <p>Data Programării: {appointment.appointmentDateTime}</p>
                    {patients[appointment.patientId] && (
                        <div>
                            <p>Pacient: {patients[appointment.patientId].lastName} {patients[appointment.patientId].firstName}</p>
                            {medicalHistories[appointment.patientId] && medicalHistories[appointment.patientId].map(history => (
                                <div key={history.medicalHistoryId}>
                                    <p>Diagnostic: {history.diagnostic}</p>
                                    <p>Tratament: {history.treatment}</p>
                                    <input
                                        type="text"
                                        value={updateData[history.medicalHistoryId]?.diagnostic || ''}
                                        onChange={(e) => handleUpdateChange(history.medicalHistoryId, 'diagnostic', e.target.value)}
                                    />
                                    <input
                                        type="text"
                                        value={updateData[history.medicalHistoryId]?.treatment || ''}
                                        onChange={(e) => handleUpdateChange(history.medicalHistoryId, 'treatment', e.target.value)}
                                    />
                                    <input
                                        type="text"
                                        value={updateData[history.medicalHistoryId]?.pdfDocument || ''}
                                        onChange={(e) => handleUpdateChange(history.medicalHistoryId, 'pdfDocument', e.target.value)}
                                    />
                                    <button onClick={() => handleUpdateMedicalHistory(history.medicalHistoryId)}>Modifica</button>
                                </div>
                            ))}
                            
                        </div>
                        
                    )}
                    <div>
                        <input 
                            type="text" 
                            placeholder="Diagnostic"
                            onChange={(e) => updateUpdateData(`diagnostic_new_${appointment.appointmentId}`, e.target.value)}
                        />
                        <input 
                            type="text" 
                            placeholder="Tratament"
                            onChange={(e) => updateUpdateData(`treatment_new_${appointment.appointmentId}`, e.target.value)}
                        />
                        <input 
                            type="text" 
                            placeholder="PDF Document"
                            onChange={(e) => updateUpdateData(`pdfDocument_new_${appointment.appointmentId}`, e.target.value)}
                        />
                        <button onClick={() => handleCreateMedicalHistory(appointment)}>Creează</button>
                    </div>
                </div>
                
            ))}
        </div>
    );
}

export default DoctorMedicalHistory;
