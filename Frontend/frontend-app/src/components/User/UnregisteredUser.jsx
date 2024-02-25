import React, { useState, useEffect, useRef } from 'react';
import '../../css/Navbar.css';
import { useNavigate, useLocation } from 'react-router-dom';
import UserAppointments from './UserAppointments';
import DoctorListComponent from '../Doctor/DoctorListComponent';
import MedicalCenterList from '../MedicalCenters/MedicalCentersList';


const UnregisteredUser = () => {
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
                navigate('/Login');
                break;
            case 'UserAppointments':
                navigate('/Login');
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
                        <li className={`nav-item ${activeItem === 'DoctorsList' ? 'active' : ''}`} onClick={() => handleItemClick('DoctorsList')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="fas fa-tachometer-alt"></i>Doctors</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'MedicalCenters' ? 'active' : ''}`} onClick={() => handleItemClick('MedicalCenters')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="fas fa-tachometer-alt"></i>Medical Centers</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'UserAppointments' ? 'active' : ''}`} onClick={() => handleItemClick('UserAppointments')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-address-book"></i>Appointments</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'Profile' ? 'active' : ''}`} onClick={() => handleItemClick('Profile')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-copy"></i>Profile</a>
                        </li>
                    </ul>
                </div>
            </nav>
            
            {activeItem === 'DoctorsList' && <DoctorListComponent />}
            {activeItem === 'UserAppointments' && <UserAppointments />}
            {activeItem === 'MedicalCenters' && <MedicalCenterList />}


        </>
        );
};

export default UnregisteredUser;