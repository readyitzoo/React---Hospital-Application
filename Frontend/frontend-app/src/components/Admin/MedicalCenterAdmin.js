import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdmin.css'; // Folosim același fișier CSS pentru stiluri

const MedicalCenterAdmin = () => {

  return (
    <div className="container">
      <div className="leftbox"></div>
      <div className="rightbox">
      <h1>Gestionează Admin Spitale</h1>
      
      <Link to="/medical-center-admin/add">
        <button className='admin-button '>Adaugă Centru Medical Nou</button>
      </Link>

      <Link to="/medical-center-admin/view">
        <button className='admin-button '>Vizualizare Centre medicale</button>
      </Link>

      <Link  to="/admin"> 
        <button className='admin-button '>Înapoi la Pagina de Admin</button>
      </Link>


    </div>
    </div>
  );
    
};

export default MedicalCenterAdmin;
