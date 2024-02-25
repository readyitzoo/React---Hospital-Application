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

//unused
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

const ReceptionistProfile = () => {
    const [activeSection, setActiveSection] = useState('profile');
    const navigate = useNavigate();

    //unused
    const goToHome = () => {
        navigate('/ReceptionistHome'); 
      };


    const [receptionist, setReceptionist] = useState(null);

    useEffect(() => {
        const fetchReceptionistData = async () => {
            try {

                const data = await api.fetchReceptionistData();
                
                console.log('api.fetchReceptionistData');
                console.log(data);
                console.log('resolved')

                setReceptionist(data.receptionist); 
                setUserData({
                    ...userData,
                    email: data.receptionist.email,
                    cnp: data.receptionist.cnp,
                    password: data.receptionist.password,
                    firstName: data.receptionist.firstName,
                    lastName: data.receptionist.lastName,
                    phoneNumber: data.receptionist.phoneNumber,
                    hospitalName: data.receptionist.hospitalName
                });
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
    
        fetchReceptionistData();
    }, []);

    //unused
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
            phoneNumber: parseInt(userData.phoneNumber, 10),
            hospitalName: userData.hospitalName
        };

        console.log("Datele trimise:", updatedData);

        //UNUSED
        // downloadJSON(updatedData, "updatedData.json");   
        try {
            console.log('api.updateReceptionistData')
            api.updateReceptionistData(updatedData);
            console.log('resolved')
            
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
                <Link to="/ReceptionistNavbar">
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
                    {/* <h1>Informații Personale</h1>
                    <h2>Nume:</h2>
                    <p>{userData.fullName}</p>
                    <h2>Data Nașterii:</h2>
                    <p>{userData.birthday}</p>
                    <h2>Gen:</h2>
                    <p>{userData.gender}</p>
                    <h2>Email:</h2>
                    <p>{userData.email}</p> */}
                    {receptionist && (
                        <div>
                            <h1>Informații Personale</h1>
                            <h2>Nume:</h2>
                            <p>{`${receptionist.firstName} ${receptionist.lastName}`}</p>
                            <h2>Email:</h2>
                            <p>{receptionist.email}</p>
                            <h2>CNP:</h2>
                            <p>{receptionist.cnp}</p>
                        </div>
                    )}
                </div>
               
                <div className={`contact ${activeSection !== 'contact' ? 'noshow' : ''}`}>
                    {receptionist && (
                        <div>
                            <h1>Detalii de Contact și Locație</h1>
                            <h2>Telefon:</h2>
                            <p>{receptionist.phoneNumber}</p>
                            <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                        </div>
                    )}
                    {/* <h1>Detalii de Contact și Locație</h1>
                    <h2>Telefon:</h2>
                    <p>{userData.phone}</p>
                    <h2>Adresă:</h2>
                    <p>{userData.address}</p> */}
                </div>
                {}
                <div className={`education ${activeSection !== 'education' ? 'noshow' : ''}`}>
                    {receptionist && (
                        <div>
                            <h1>Experiență și Educație</h1>
                            <h2>Spital:</h2>
                            <p>{receptionist.hospitalName}</p>
                            <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                        </div>
                    )}
                    {/* <h1>Experiență și Educație</h1>
                    <h2>Experiență:</h2>
                    <p>{userData.experience}</p>
                    <h2>Educație:</h2>
                    <p>{userData.education}</p> */}
                </div>
                {}
                <div className={`skills ${activeSection !== 'skills' ? 'noshow' : ''}`}>
                    <h1>Competențe și Limbi</h1>
                    <img src={soonGif} className="imageGIF" alt="Soon GIF" />
                </div>
                {/* <div className={`skills ${activeSection !== 'skills' ? 'noshow' : ''}`}>
                    
                    <h1>Competențe și Limbi</h1>
                    <h2>Limbi:</h2>
                    <p>{userData.languages}</p>
                    <h2>Competențe: </h2>
                    <p>{userData.skills}</p>
                </div> */}

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

export default ReceptionistProfile;
