import React, { useState, useEffect } from 'react';
import Navbar from './Navbar'; // Import the Navbar component
import '../../css/ReceptionistProfile.css';
import profileIcon from '../../image/user.png';
import contactIcon from '../../image/contact.png';
import educationIcon from '../../image/education.png'; 
import skillsIcon from '../../image/skills.png';
import updateData from '../../image/update.png';
import logoImage from '../../image/hospital.png';
import homePage from '../../image/home.png';
import soonGif from '../../image/soon.gif'; 
import { Link } from 'react-router-dom';
import api from '../../api/api';



const HospitalAdminProfile = () => {
    const [activeSection, setActiveSection] = useState('profile');

    const [hadmin, setHospitalAdmin] = useState(null);

    useEffect(() => {
        const fetchHospitalAdminData = async () => {
            try {

                const data = await api.fetchHospitalAdminData();

                console.log('api.fetchHospitalAdminData');
                console.log(data);
                console.log('resolved')

                const hadmin = data['Hospital Admin'];

                setHospitalAdmin(data.HospitalAdmin); 
                setUserData({
                    ...userData,
                    email: hadmin.email,
                    cnp: hadmin.cnp,
                    password: hadmin.password,
                    firstName: hadmin.firstName,
                    lastName: hadmin.lastName,
                    phoneNumber: hadmin.phoneNumber,
                    hospitalName: hadmin.hospitalName
                    
                });
                console.log(data);

            } catch (error) {
                console.error('Error fetching data:', error);
            }
            
        };
    
        fetchHospitalAdminData();
    }, []);

    const [userData, setUserData] = useState({
        email: 'a',
        cnp: 'a',
        password: '',
        firstName: '',
        lastName: '',
        phoneNumber: '',
        hospitalName: ''   
    });

    const handleUpdate = async () => {
        const updatedData = {
          'Hospital Admin': {
            email: userData.email,
            cnp: userData.cnp,
            password: userData.password,
            firstName: userData.firstName,
            lastName: userData.lastName,
            phoneNumber: userData.phoneNumber, // No need to parse as integer
            hospitalName: userData.hospitalName,
          },
        };
      
        console.log("Data sent:", updatedData);
      
        try {
          const response = await api.updateHospitalAdminData(updatedData);
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
      
          const data = await response.json();
          console.log('Update successful:', data);
        } catch (error) {
          console.error('Error updating data:', error);
          error.response && console.log('Response data:', error.response.data);
        }
      };
      
    return (
        <>
        <Navbar activeItem={'HospitalAdminProfile'} setActiveItem={() => {}} />
        <div className="container">
             <div id="logo">
                <img src={logoImage} alt="Logo" className="image logo" />
                <div className="CTA">
                <Link to="/Navbar">
                    <img src={homePage} alt="Ceva Util" className="cta-image" />
                    <h2 className="home">Home</h2>
                </Link>
                </div>
            </div>

            <div className="leftbox">
                <nav>
                    
                    <a onClick={() => setActiveSection('personalInfo')} className={activeSection === 'personalInfo' ? 'active' : ''}>
                        <img src={profileIcon} className="image" alt="Personal Info" />
                    </a>
            
                    <a onClick={() => setActiveSection('contact')} className={activeSection === 'contact' ? 'active' : ''}>
                        <img src={contactIcon} className="image" alt="Contact" />
                    </a>
    
                    <a onClick={() => setActiveSection('education')} className={activeSection === 'education' ? 'active' : ''}>
                        <img src={educationIcon} className="image" alt="Education" />
                    </a>
                    
                    <a onClick={() => setActiveSection('skills')} className={activeSection === 'skills' ? 'active' : ''}>
                        <img src={skillsIcon} className="image" alt="Skills" />
                    </a>
                     
                    <a onClick={() => setActiveSection('update')} className={activeSection === 'update' ? 'active' : ''}>
                        <img src={updateData} className="image" alt="Update" />
                    </a>
                </nav>
            </div>
            <div className="rightbox">
            <div className={`personalInfo ${activeSection !== 'personalInfo' ? 'noshow' : ''}`}>
            {hadmin && (
                <div>
                <h1>Informații Personale</h1>
                <h2>Nume:</h2>
                <p>{`${hadmin['Hospital Admin'].firstName} ${hadmin['Hospital Admin'].lastName}`}</p>
                <h2>Email:</h2>
                <p>{hadmin['Hospital Admin'].email}</p>
                <h2>CNP:</h2>
                <p>{hadmin['Hospital Admin'].cnp}</p>
                </div>
            )}
            </div>

            <div className={`contact ${activeSection !== 'contact' ? 'noshow' : ''}`}>
            {hadmin && (
                <div>
                <h1>Detalii de Contact și Locație</h1>
                <h2>Telefon:</h2>
                <p>{hadmin['Hospital Admin'].phoneNumber}</p>
                <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                </div>
            )}
            </div>

            <div className={`education ${activeSection !== 'education' ? 'noshow' : ''}`}>
            {hadmin && (
                <div>
                <h1>Experiență și Educație</h1>
                <h2>Spital:</h2>
                <p>{hadmin['Hospital Admin'].hospitalName}</p>
                <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                </div>
            )}
            </div>

            <div className={`skills ${activeSection !== 'skills' ? 'noshow' : ''}`}>
            <h1>Competențe și Limbi</h1>
            <img src={soonGif} className="imageGIF" alt="Soon GIF" />
            </div>
                <div className={`update ${activeSection !== 'update' ? 'noshow' : ''}`}>
                    <h1>Actualizează Informațiile</h1>
                    <form>
                         
                        <div className="form__group field">
                            <input type="email" className="form__field" placeholder="Email" name="email" id="email" required value={userData.email} onChange={e => setUserData({ ...userData, email: e.target.value })} />
                            <label htmlFor="email" className="form__label">Email</label>
                        </div>
                        <div className="form__group field">
                            <input type="text" className="form__field" placeholder="FirstName" name="firstName" id="firstName" required value={userData.firstName} onChange={e => setUserData({ ...userData, firstName: e.target.value })} />
                            <label htmlFor="firstName" className="form__label">First Name</label>
                        </div>
                        <div className="form__group field">
                            <input type="text" className="form__field" placeholder="LastName" name="lastName" id="lastName" required value={userData.lastName} onChange={e => setUserData({ ...userData, lastName: e.target.value })} />
                            <label htmlFor="lastName" className="form__label">Last Name</label>
                        </div>
                        <div className="form__group field">
                            <input type="password" className="form__field" placeholder="Password" name="password" id="password" required value={null} onChange={e => setUserData({ ...userData, password: e.target.value })} />
                            <label htmlFor="password" className="form__label">Parola</label>
                        </div>
                        {}
                        <button type="button" onClick={handleUpdate} className="btn">Actualizează</button>
                    </form>
                </div> 
                

                {}

            </div>
        </div>
    </>
        
    );
};

export default HospitalAdminProfile;
