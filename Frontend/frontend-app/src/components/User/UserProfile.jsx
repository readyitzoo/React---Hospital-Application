import React, { useState, useEffect } from 'react';
import '../../css/ReceptionistProfile.css';
import profileIcon from '../../image/user.png';
import contactIcon from '../../image/contact.png';
import educationIcon from '../../image/education.png'; 
import skillsIcon from '../../image/skills.png';
import updateData from '../../image/update.png';
import logoImage from '../../image/hospital.png';
import homePage from '../../image/home.png';
import soonGif from '../../image/soon.gif'; 
import { useNavigate  } from "react-router-dom";
import { Link } from 'react-router-dom';
import api from '../../api/api';


const downloadJSON = (data, filename) => {
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
};

const UserProfile = () => {
    const [activeSection, setActiveSection] = useState('profile');
    const navigate = useNavigate();
    const [medicalHistory, setMedicalHistory] = useState([]);


    const goToHome = () => {
        navigate('/UserHome'); 
    };

    const fetchMedicalHistory = async () => {
        try {

            console.log('api.medicalHistoryReadAllByPatient');
            const data = await api.medicalHistoryReadAllByPatient();
            console.log('resolved');

            setMedicalHistory(data);

        } catch (error) {
            console.error('Error fetching medical history:', error);
        }
    };
    
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await api.fetchPatientData();
                setUser(data.patientInfo); 
                setUserData({
                    email: data.patientInfo.email,
                    cnp: data.patientInfo.cnp,
                    password: data.patientInfo.password,
                    firstName: data.patientInfo.firstName,
                    lastName: data.patientInfo.lastName,
                    phoneNumber: data.patientInfo.phoneNumber
                    
                });
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchUserData();
        fetchMedicalHistory();
    }, []);

    //UNUSED
    const handleNavClick = (section) => {
        setActiveSection(section);
    };

    const [userData, setUserData] = useState({
        email: '',
        cnp: '',
        password: '',
        firstName: '',
        lastName: '',
        phoneNumber: '',
        hospitalName: ''   
    });

    const handleUpdate = async () => {

        const updatedData = {
            email: userData.email,
            cnp: userData.cnp,
            password: userData.password,
            firstName: userData.firstName,
            lastName: userData.lastName,
            phoneNumber: parseInt(userData.phoneNumber, 10)
        };
        
        try {

            //TO DO: solve the catch error problem
            console.log('api.updatePatientData(updatedData)');
            await api.updatePatientData(updatedData);
            console.log('resolved');
            
        } catch (error) {
            console.error('Error updating data:', error);
            error.response && console.log('Response data:', error.response.data);
        }
    };


    return (
        <div className="container">
             <div id="logo">
                <img src={logoImage} alt="Logo" className="image logo" />
                <div className="CTA">
                <Link to="/UserNavbar">
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
                    {user && (
                        <div>
                            <h1>Informații Personale</h1>
                            <h2 className = "pad">Nume:</h2>
                            <p>{`${user.firstName} ${user.lastName}`}</p>
                            <h2 className = "pad">Email:</h2>
                            <p>{user.email}</p>
                            <h2 className = "pad">CNP:</h2>
                            <p>{user.cnp}</p>
                        </div>
                    )}
                </div>
               
                <div className={`contact ${activeSection !== 'contact' ? 'noshow' : ''}`}>
                    {user && (
                        <div>
                            <h1>Detalii de Contact și Locație</h1>
                            <h2 className = "pad">Telefon:</h2>
                            <p>{user.phoneNumber}</p>
                            <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                        </div>
                    )}
                </div>
                {}
                <div className={`education ${activeSection !== 'education' ? 'noshow' : ''}`}>
                    {user && (
                        <div>
                            <h1>Experiență și Educație</h1>
                            <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                        </div>
                    )}
                </div>
                {}
                <div className={`skills ${activeSection !== 'skills' ? 'noshow' : ''}`}>
                    <h1>Istoric Medical</h1>
                    {medicalHistory.length > 0 ? (
                        <ul>
                            {medicalHistory.map(item => (
                                <li key={item.medicalHistoryId}>
                                    <h2 className = "pad">Diagnostic:</h2>
                                    <p>{item.diagnostic}</p>
                                    <h2 className = "pad">Tratament:</h2>
                                    <p>{item.treatment}</p>
                                    {/*TO DO: afiseaza frumos continutul fisierului*/ }
                                    {item.fileName && <a  className = "pad" href={item.pdfDocument} target="_blank" rel="noopener noreferrer">{item.fileName}</a>}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Nu există istoric medical înregistrat.</p>
                    )}
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
    );
};

export default UserProfile;
