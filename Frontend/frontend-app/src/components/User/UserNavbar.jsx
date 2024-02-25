import React, { useState, useEffect, useRef } from 'react';
import '../../css/Navbar.css';
import { useNavigate, useLocation } from 'react-router-dom';
import UserAppointments from './UserAppointments';
import UserCancelAppointments from './UserCancelAppointments';
import UserDoctorDetails from './UserDoctorDetails';
import DoctorsListComponent from '../Doctor/DoctorListComponent';
import api from '../../api/api';

const Navbar = () => {
    const [activeItem, setActiveItem] = useState('');
    const navbarRef = useRef(null);
    const horiSelectorRef = useRef(null);
    const navigate = useNavigate();
    const location = useLocation();


    useEffect(() => {
        if (activeItem && navbarRef.current) {
            const activeElement = navbarRef.current.querySelector('.nav-item.active');
            if (activeElement) {
                updateHoriSelector(navbarRef.current.querySelector('.nav-item.active'));
            }
        }
    }, [activeItem]); 
    

    useEffect(() => {
        if (navbarRef.current && navbarRef.current.querySelector('.nav-item.active')) {
            const activeElement = navbarRef.current.querySelector('.nav-item.active');
            updateHoriSelector(activeElement);
        }

        const handleResize = () => {
            console.log('Window resized');
            if (navbarRef.current && navbarRef.current.querySelector('.nav-item.active')) {
                updateHoriSelector(navbarRef.current.querySelector('.nav-item.active'));
            }
        };

        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    const getActiveItem = () => {
        switch (location.pathname) {
            case '/Navbar': 
                return 'DoctorsList';
            case '/ReceptionistHome':
                return 'addressBook';

            default:
                return '';
        }
    };

    const updateHoriSelector = (item) => {
        console.log('updateHoriSelector called with item:', item);
        if (navbarRef.current && horiSelectorRef.current && item) {
            const rect = item.getBoundingClientRect();
            const navbarRect = navbarRef.current.getBoundingClientRect();
            horiSelectorRef.current.style.top = `${rect.top - navbarRect.top}px`;
            horiSelectorRef.current.style.left = `${rect.left - navbarRect.left}px`;
            horiSelectorRef.current.style.width = `${rect.width}px`;
            horiSelectorRef.current.style.height = `${rect.height}px`;
        }
    };


    
    const handleItemClick = (itemName) => {
        setActiveItem(itemName);
        switch (itemName) {

            case 'Profile':
                navigate('/UserProfile');
                break;
            case 'Logout':
                api.logout();
                break;
            default:
                break;
        }
    };
    


    return (

        <>
            <nav className={'${styles.navbar} navbar-expand-custom navbar-mainbg'} ref={navbarRef}>
            
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav ml-auto">
                        <div className="hori-selector" ref={horiSelectorRef}><div className="left"></div><div className="right"></div></div>
                        <li className={`nav-item ${activeItem === 'UserAppointments' ? 'active' : ''}`} onClick={() => handleItemClick('UserAppointments')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-address-book"></i>Programeaza-te</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'UserCancelAppointments' ? 'active' : ''}`} onClick={() => handleItemClick('UserCancelAppointments')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-clone"></i>Programarile Mele</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'UserDoctorDetails' ? 'active' : ''}`} onClick={() => handleItemClick('UserDoctorDetails')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-calendar-alt"></i>Programarile Doctorilor</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'Profile' ? 'active' : ''}`} onClick={() => handleItemClick('Profile')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-copy"></i>Profile</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'Logout' ? 'active' : ''}`} onClick={() => handleItemClick('Logout')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-copy"></i>Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
            
            {activeItem === 'DoctorsList' && <DoctorsListComponent />}
            {activeItem === 'UserAppointments' && <UserAppointments />}
            {activeItem === 'UserCancelAppointments' && <UserCancelAppointments />}
            {activeItem === 'UserDoctorDetails' && <UserDoctorDetails />}

        </>
        );
};

export default Navbar;