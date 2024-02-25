import React from 'react';
import { Link } from 'react-router-dom';
import '../../css/AdminPage.css';

const AdminPage = () => {
  return (
    <div className="container">
      
      <div className="leftbox"></div>
      <div className="rightbox">
        <h1>Pagina Admin</h1>
        <Link to="/HospitalAdmin">
          <button className="admin-button">  Gestionează Admin Spitale</button>
        </Link>
        
        <Link to="/MedicalCenterAdmin">
          <button className="admin-button">Gestionează Centre Medicale</button>
        </Link>
      </div>
    </div>
  );
};

export default AdminPage;
