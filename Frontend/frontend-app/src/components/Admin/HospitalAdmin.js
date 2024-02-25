import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdmin.css';

const HospitalAdmin = () => {


return (
    <div className="container">
      <div className="leftbox"></div>
      <div className="rightbox">
      <h1>Gestionează Admin Spitale</h1>
      

      <Link to="/HospitalAdminAdd">
        <button className='admin-button '>Adaugă Admin Spital Nou</button>
      </Link>

      <Link to="/HospitalAdminView">
        <button className='admin-button '>Vizualizare Admini Spitale</button>
      </Link>

      <Link  to="/AdminPage"> 
        <button className='admin-button '>Înapoi la Pagina de Admin</button>
      </Link>

    </div>
    </div>
  );
};

export default HospitalAdmin;