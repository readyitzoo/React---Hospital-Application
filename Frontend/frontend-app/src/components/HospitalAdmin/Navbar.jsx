import React, { useEffect, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import DoctorListComponent from '../Doctor/DoctorListComponent';
import HospitalAdminApprovals from './HospitalAdminApprovals';
import HospitalAdminRegisterReceptionist from './HospitalAdminRegisterReceptionist';
import '../../css/Navbar.css';

const Navbar = () => {
    const navbarRef = useRef(null);
    const horiSelectorRef = useRef(null);
    const navigate = useNavigate();
    const location = useLocation();

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
            case '/HospitalAdminHome':
                return 'addressBook';
            default:
                return '';
        }
    };

    const activeItem = getActiveItem();

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
        switch (itemName) {
            case 'Profile':
                navigate('/HospitalAdminProfile');
                break;
            case 'DoctorsList':
                navigate('/Navbar');
                break;
            case 'HospitalAdminApprovals':
                navigate('/HospitalAdminApprovals');
                break;
            case 'Register Staff':
                navigate('/HospitalAdminRegisterReceptionist');
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
                            <a className="nav-link" href="javascript:void(0);"><i className="fas fa-tachometer-alt"></i>DoctorsList</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'HospitalAdminApprovals' ? 'active' : ''}`} onClick={() => handleItemClick('HospitalAdminApprovals')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-address-book"></i>Approvals</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'Register Staff' ? 'active' : ''}`} onClick={() => handleItemClick('Register Staff')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-copy"></i>Register Staff</a>
                        </li>
                        <li className={`nav-item ${activeItem === 'Profile' ? 'active' : ''}`} onClick={() => handleItemClick('Profile')}>
                            <a className="nav-link" href="javascript:void(0);"><i className="far fa-copy"></i>Profile</a>
                        </li>
                    </ul>
                </div>
            </nav>
            
            {activeItem === 'DoctorsList' && <DoctorListComponent />}
            {activeItem === 'HospitalAdminApprovals' && <HospitalAdminApprovals />}
            {activeItem === 'Register Staff' && <HospitalAdminRegisterReceptionist />}
        </>
    );
};

export default Navbar;
