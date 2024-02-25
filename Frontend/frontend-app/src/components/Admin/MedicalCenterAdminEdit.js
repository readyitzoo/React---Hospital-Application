import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../../css/HospitalAdminEdit.css'; // Folosim stilurile de la HospitalAdminEdit

const MedicalCenterAdminEdit = () => {
  const { id } = useParams();
  const [medicalCenterData, setMedicalCenterData] = useState({
    name: '',
    address: '',
  });

  useEffect(() => {
    // Aici puteți face o cerere la server pentru a obține datele centrului medical
    // Vom utiliza 'id' pentru a face cererea corectă
    // După primirea datelor de la server, actualizăm starea 'medicalCenterData' cu datele centrului medical
    // Pentru exemplu, vom folosi date statice
    setMedicalCenterData({
      name: 'Centrul Medical Exemplu',
      address: 'Strada Exemplu, nr. 10, Orașul Exemplu',
    });
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMedicalCenterData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aici puteți gestiona trimiterea datelor actualizate la server
    console.log(medicalCenterData);
    // Exemplu de redirecționare către pagina de administrare a centrelor medicale:
    window.location.href = '/medical-center-admin';
  };

  return (
    <div className="container_h_a_e">
      <h1>Editare Centru Medical</h1>
      <Link to="/medical-center-admin">
        <button className="btn btn-secondary">Înapoi </button>
      </Link>
      <form onSubmit={handleSubmit} className='registerForm'>
        <input 
          placeholder="Nume Centru Medical.." 
          name="name" 
          value={medicalCenterData.name} 
          onChange={handleChange}
        />
        <input 
          placeholder="Adresă Centru Medical.." 
          name="address" 
          value={medicalCenterData.address} 
          onChange={handleChange}
        />
        <button type="submit" className="submitButton">Salvează Modificările</button>
      </form>
    </div>
  );
};

export default MedicalCenterAdminEdit;
