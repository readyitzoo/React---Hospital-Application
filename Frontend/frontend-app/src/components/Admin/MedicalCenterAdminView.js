import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdminView.css'; // Asumând că stilurile sunt adecvate pentru vizualizarea centrelor medicale

const MedicalCenterAdminView = () => {
  const [medicalCenters, setMedicalCenters] = useState([]);

  useEffect(() => {
    // Simularea datelor pentru centre medicale
    const simulatedData = [
      {
        id: 1,
        name: 'Centrul Medical A',
        address: 'Strada Libertății, nr. 10, Oraș A',
        phoneNumber: '0710123456'
      },
      {
        id: 2,
        name: 'Centrul Medical B',
        address: 'Bulevardul Unirii, nr. 22, Oraș B',
        phoneNumber: '0721123456'
      }
      // Puteți adăuga mai multe obiecte pentru a simula mai multe date
    ];

    setMedicalCenters(simulatedData);
  }, []);

  const deleteMedicalCenter = (centerId) => {
    // Logică simplificată pentru ștergere
    setMedicalCenters((prevCenters) =>
      prevCenters.filter((center) => center.id !== centerId)
    );
  };

  return (
    <div className="container_h_a_v">
      
      <h1>Gestionează Centre Medicale</h1>
      <Link to="/admin">
          <button className="admin-button ">Înapoi la Pagina de Admin</button>
      </Link>
      <Link to="/medical-center-admin/add">
        <button className="admin-button ">Adaugă Centru Medical Nou</button>
      </Link>

      <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nume Centru Medical</th>
          <th>Adresă</th>
          <th>Telefon</th>
          <th>Acțiuni</th>
        </tr>
      </thead>
      <tbody>
        {medicalCenters.map((center) => (
          <tr key={center.id}>
            <td>{center.id}</td>
            <td>{center.name}</td>
            <td>{center.address}</td>
            <td>{center.phoneNumber}</td>
            <td>
              <Link to={`/medical-center-admin/edit/${center.id}`}>
                <button className='btn' >Editare</button>
              </Link>
              <button className='btn' onClick={() => deleteMedicalCenter(center.id)}>Ștergere</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>

    </div>
  
  );
};

export default MedicalCenterAdminView;
