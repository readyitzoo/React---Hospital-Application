import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdminView.css';
import api from '../../api/api';

const HospitalAdminView = () => {
  const [hospitals, setHospitals] = useState([]);
  const [hospitalAdmins, setHospitalAdmins] = useState([]);
  const [tableData, setTableData] = useState([]);

  useEffect(() => {

    const getTableData = async () =>{

      const data = await api.hospitalAdminReadAll();
      console.log({data});
      setHospitalAdmins(data);
      console.log({hospitalAdmins});

      let tableData = hospitalAdmins.map((hadmin)=>{
        
        const {"Hospital Admin" : hadminData} = hadmin; 

        return {
          email: hadminData.email,
          firstName: hadminData.firstName,
          lastName: hadminData.lastName,
          phoneNumber: hadminData.phoneNumber,
          hospitalName : hadminData.hospitalName
      }})
      
      setTableData(tableData);
      console.log('setTableData resolved', {tableData});
  
    }

    getTableData();
  }, []);

  const deleteHospital = (hospitalEmail) => {
    // Logică simplificată pentru ștergere
    setHospitals((prevHospitals) =>
      prevHospitals.filter((hospital) => hospital.email !== hospitalEmail)
    );
  };

  return (
    <div className="container_h_a_v">
      
        <h1>Gestionează Admin Spitale</h1>
        <Link to="/AdminPage">
          <button className="admin-button ">Înapoi la Pagina de Admin</button>
        </Link>

        <Link to="/HospitalAdminAdd">
          <button className="admin-button ">Adaugă Admin Spital Nou</button>
        </Link>

        <table>
        <thead>
          <tr>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Phone Number</th>
            <th>Hospital Name</th>
            <th>Acțiuni</th>
          </tr>
        </thead>
        <tbody>
          {tableData.map((hospital) => (
            <tr key={hospital.email}>
              <td>{hospital.email}</td>
              <td>{hospital.firstName}</td>
              <td>{hospital.lastName}</td>
              <td>{hospital.phoneNumber}</td>
              <td>{hospital.hospitalName}</td>
              <td>
                <Link to={`/HospitalAdminEdit/${hospital.email}`}>
                  <button className='btn' >Editare</button>
                </Link>
                <button className='btn' onClick={() => deleteHospital(hospital.email)}>Ștergere</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      </div>
    
  );
};

export default HospitalAdminView;
