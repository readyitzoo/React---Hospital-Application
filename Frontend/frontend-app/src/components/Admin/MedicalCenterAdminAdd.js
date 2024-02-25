import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdminAdd.css'; // Presupunem că stilurile se aplică și în acest context

const MedicalCenterAdminAdd = () => {
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");

  const submitData = (event) => {
    event.preventDefault();
    // Logica pentru a gestiona trimiterea datelor centrului medical nou
    console.log(name, address);
  };

  return (
    <div className="container">
      <h1>Adaugă Centru Medical Nou</h1>
      <Link to="/medical-center-admin">
        <button className="btn btn-secondary">Înapoi </button>
      </Link>
      <form onSubmit={submitData} className='registerForm'>
        <input 
          placeholder="Nume Centru Medical.." 
          onChange={(event) => setName(event.target.value)}
          value={name}
        />
        <input 
          placeholder="Adresa Centru Medical.." 
          onChange={(event) => setAddress(event.target.value)}
          value={address}
        />
        <button type="submit" className="submitButton">Adaugă Centru Medical</button>
      </form>
    </div>
  );
};

export default MedicalCenterAdminAdd;
