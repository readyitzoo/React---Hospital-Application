
import {useEffect, useState} from 'react';
import '../../css/DoctorListComponent.css';
import api from '../../api/api';


export default function MedicalCenterList()
{
    const [doctors, setDoctors] = useState([]);

    useEffect(() => {
        
        const getDoctors = async () => {
            console.log('api.fetchMedicalCenters')
            const data = await api.fetchMedicalCenters();
            console.log('resolved', {data});

            setDoctors(data);
        }
        getDoctors();
    }, []);

    return (
        <div className='DoctorListComponentWrapper'>
            <ul>
                {doctors.map((doctor) => {
                    
                    return (
                        <div className='DoctorCardWrapper'>
                            <div><b>{doctor.name}</b></div>
                            <div><b>Locatie:</b> {doctor.location}</div>
                        </div>
                    )
                })}
            </ul>
        </div>
    );

}