import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom'

export default function DoctorGuard({Element})
{
    const navigate = useNavigate();

    useEffect(() => {
        const role = sessionStorage.getItem('role');
        if(role !== '"Doctor"')
            navigate('/DoctorNavbar');
        }
    ,[])

    return(
        <Element />
    );
}