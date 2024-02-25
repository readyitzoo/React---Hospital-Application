import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../../css/HospitalAdminEdit.css'; // Utilizăm stilurile de la HospitalAdminAdd

const HospitalAdminEdit = () => {
  const { emailParam } = useParams(); // Folosim emailParam pentru a evita confuzia cu starea email
  const [adminData, setAdminData] = useState({
    email: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    hospitalName: '',
  });

  useEffect(() => {
    // Aici puteți face o cerere la server pentru a obține datele administratorului de spital
    // Vom utiliza 'emailParam' pentru a face cererea corectă
    // După primirea datelor de la server, actualizăm starea 'adminData' cu datele administratorului
    // Pentru exemplu, vom folosi date statice
    setAdminData({
      email: 'admin@example.com',
      firstName: 'John',
      lastName: 'Doe',
      phoneNumber: '1234567890',
      hospitalName: 'General Hospital',
    });
  }, [emailParam]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAdminData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aici puteți gestiona trimiterea datelor actualizate la server
    console.log(adminData);
  };

  return (
    <div className="container_h_a_e">
      <h1>Editare Admin Spital</h1>
      <Link to="/hospital-admin">
        <button className="btn btn-secondary">Înapoi</button>
      </Link>
      <form onSubmit={handleSubmit} className='registerForm'>
        <input 
          placeholder="Email.." 
          name="email" 
          value={adminData.email} 
          onChange={handleChange}
        />
        <input 
          placeholder="First Name" 
          name="firstName" 
          value={adminData.firstName} 
          onChange={handleChange}
        />
        <input 
          placeholder="Last Name" 
          name="lastName" 
          value={adminData.lastName} 
          onChange={handleChange}
        />
        <input 
          placeholder="Phone Number" 
          name="phoneNumber" 
          value={adminData.phoneNumber} 
          onChange={handleChange}
        />
        <input 
          placeholder="Hospital Name" 
          name="hospitalName" 
          value={adminData.hospitalName} 
          onChange={handleChange}
        />
        <button type="submit" className="submitButton">Salvează Modificările</button>
      </form>
    </div>
  );
};

export default HospitalAdminEdit;
