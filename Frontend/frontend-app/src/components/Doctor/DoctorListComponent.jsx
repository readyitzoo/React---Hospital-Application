
import {useEffect, useState} from 'react';
import '../../css/DoctorListComponent.css';
import api from '../../api/api';


//TO DO: stilizare
export default function DoctorListComponent()
{
    const [doctors, setDoctors] = useState([]);

    useEffect(() => {
        
        const getDoctors = async () => {
            console.log('api.fetchAllDoctors')
            const data = await api.fetchAllDoctors();
            console.log('resolved', {data});

            setDoctors(data);
        }
        getDoctors();
    }, []);

    return (
        <div className='DoctorListComponentWrapper'>
            <ul>
                {doctors.map((doctor) => {
                    const doctorInfo = doctor.doctorInfo;
                    
                    return (
                        <div className='DoctorCardWrapper'>
                            <div><b>{doctorInfo.firstName} {doctorInfo.lastName}</b></div>
                            <div><b>Specializare:</b> {doctorInfo.specializationName}</div>
                            <div><b>Contact:</b> {doctorInfo.phoneNumber}</div>
                            <div><b>Spital:</b> {doctorInfo.hospitalName}</div>
                        </div>
                    )
                })}
            </ul>
        </div>
    );

}