import react from 'react';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom'

export default function ReceptionistGuard({Element})
{
    const navigate = useNavigate();

    useEffect(() => {
        const role = sessionStorage.getItem('role');
        if(role !== '"Receptionist"' && role !== '"Admin"')
            navigate('/');
        }
    ,[])

    return(
        <Element />
    );
}