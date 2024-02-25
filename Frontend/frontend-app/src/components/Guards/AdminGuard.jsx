import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom'

export default function AdminGuard({Element})
{
    const navigate = useNavigate();

    useEffect(() => {
        const role = sessionStorage.getItem('role');
        if(role !== '"Admin"')
            navigate('/AdminPage');
        }
    ,[])

    return(
        <Element />
    );
}