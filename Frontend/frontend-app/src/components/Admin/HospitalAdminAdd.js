import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../css/HospitalAdminAdd.css'; 
import api from '../../api/api';



const HospitalAdminAdd = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [cnp, setCNP] = useState("");
  const [hospitalName, setHospitalName] = useState("");
  const [medicalCenters, setMedicalCenters] = useState([]);

//CHECKED

  useEffect( () => {
    getMedicalCenters();
  }, [])

  const submitData = async (event) => {
    event.preventDefault();
    
    const body = {
      email,
      password,
      firstName,
      lastName,
      phoneNumber,
      cnp,
      hospitalName
    }

    //CHECKED
    console.log('2. api.registerByAccountType(body, "HospitalAdmin");')
    const res = await api.registerByAccountType(body, "HospitalAdmin");
    console.log('2. resolved', {res});

  };

  const getMedicalCenters = async () => {

    //CHECKED
    console.log('1. api.fetchMedicalCenters();')
    const data = await api.fetchMedicalCenters();
    console.log('1. resolved', {data});

    setMedicalCenters(data);
  }

  const changeHospitalName = (event) => {
    setHospitalName(event.target.value);
  }  

  return (
    <div className="container">
      <h1>Adaugă Admin Spital Nou</h1>
      <Link to="/HospitalAdmin">
        <button className="btn btn-secondary">Înapoi </button>
      </Link>
      <form onSubmit={submitData} className='registerForm'>
        <input placeholder="Email.." onChange={(event) => setEmail(event.target.value)}></input>
        <input placeholder="CNP.." onChange={(event) => setCNP(event.target.value)}></input>
        <input placeholder="Password.." onChange={(event) => setPassword(event.target.value)}></input>
        <input placeholder="Firstname" onChange={(event) => setFirstName(event.target.value)}></input>
        <input placeholder="Lastname.." onChange={(event) => setLastName(event.target.value)}></input>
        <input placeholder="Phone number.." onChange={(event) => setPhoneNumber(event.target.value)}></input>
        <select onChange={changeHospitalName}>
            <option disabled>Choose medical center</option>
          {medicalCenters.map((medicalCenter) => 
            <option value={medicalCenter.name}>{medicalCenter.name}</option>
          )}
        </select>
        <button type="submit" className="submitButton">Register</button>
      </form>
    </div>
  );
};

export default HospitalAdminAdd;
