import react from 'react';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom'

export default function PatientGuard({Element})
{
    const navigate = useNavigate();

    useEffect(() => {
        const role = sessionStorage.getItem('role');
        if(role !== '"Patient"')
            navigate('/');
        }
    ,[])

    return(
        <Element />
    );
}