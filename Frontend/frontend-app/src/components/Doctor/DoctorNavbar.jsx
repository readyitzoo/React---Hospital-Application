import React, { useState, useEffect, useRef } from 'react';
import '../../css/Navbar.css';
import { useNavigate, useLocation } from 'react-router-dom';
import DoctorSchedule from './DoctorSchedule';
import DoctorMedicalHistory from './DoctorMedicalHistory';
import UserDoctorDetails from '../User/UserDoctorDetails';
import DoctorAppointments from './DoctorAppointments';

//CHECKED

const DoctorNavbar = () => {
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
                navigate('/DoctorProfile');
                break;
            case 'Logout':
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
                        <li className={`nav-item ${activeItem === 'DoctorAppointments' ? 'active' : ''}`} onClick={() => handleItemClick('DoctorAppointments')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-address-book"></i>Programarile Mele</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'DoctorSchedule' ? 'active' : ''}`} onClick={() => handleItemClick('DoctorSchedule')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-clone"></i>Programul Meu</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'DoctorMedicalHistory' ? 'active' : ''}`} onClick={() => handleItemClick('DoctorMedicalHistory')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-calendar-alt"></i>Istoricul Pacientilor</a>
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
            
            {activeItem === 'DoctorAppointments' && <DoctorAppointments />}
            {activeItem === 'DoctorSchedule' && <DoctorSchedule />}
            {activeItem === 'DoctorMedicalHistory' && <DoctorMedicalHistory />}
            {activeItem === 'UserDoctorDetails' && <UserDoctorDetails />}

        </>
        );
};

export default DoctorNavbar;