import React, { useState, useEffect } from 'react';
import Navbar from './Navbar'; // Import the Navbar component
import '../../css/RegisterReceptionist.css';
import api from '../../api/api';

export default function RegisterReceptionist() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [CNP, setCNP] = useState("");
    const [hospitalName, sethospitalName] = useState("");
    const [specializationName, setSpecializationName] = useState("");
    const [userType, setUserType] = useState("receptionist"); // Added state for user type
    const [medicalCenters, setMedicalCenters] = useState([]);
    const [specializations, setSpecializations] = useState([]);

    useEffect(() => {
        
        const fetchMedicalCenters = async () => {

            console.log('2. api.fetchMedicalCenters();');
            const medicalCenters = await api.fetchMedicalCenters();
            console.log('2. resolved', medicalCenters);
    
            setMedicalCenters(medicalCenters);
        }

        const fetchSpecilizations = async () => {
            
            console.log('2. api.fetchSpecizations();');
            const specializations = await api.fetchSpecializations();
            console.log('2. resolved', {specializations});
    
            setSpecializations(specializations);
        }

        fetchSpecilizations();
        fetchMedicalCenters();

    },[])

    async function submitData(event) {
        event.preventDefault();

        const result = {
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            cnp: CNP,
            hospitalName,
            specializationName: userType === "doctor" ? specializationName : null, // Include specialization if registering as a doctor
        };
        console.log(result);

        async function fetchToken () {

            console.log('1. api.registerByAccountType(result, tipcont); ');
            const data = await api.registerByAccountType(result, userType); 
            console.log('1. resolved', data);
        }

        let response;
        try {
            response = await fetchToken();
            console.log(response);
        } catch {
            response = "error";
        }
    }

    return (
        <>
            <Navbar activeItem={'Register Staff'} setActiveItem={() => {}} />
            <div className="registerBody">
                <div className="registerWrapper">
                    <div className="filler">
                        <h3 className='joinHeader'>Create an account here</h3>
                    </div>
                    <form onSubmit={submitData} className='registerForm'>
                    <input
                        placeholder="Email.."
                        onChange={(event) => { setEmail(event.target.value) }}
                    />
                    <input
                        placeholder="Password.."
                        onChange={(event) => { setPassword(event.target.value) }}
                    />
                    <input
                        placeholder="CNP.."
                        onChange={(event) => { setCNP(event.target.value) }}
                    />
                    <input
                        placeholder="Firstname.."
                        onChange={(event) => { setFirstName(event.target.value) }}
                    />
                    <input
                        placeholder="Lastname.."
                        onChange={(event) => { setLastName(event.target.value) }}
                    />
                    <input
                        placeholder="Phone number.."
                        onChange={(event) => { setPhoneNumber(event.target.value) }}
                    />
                    <label className="formLabel">
                        <select
                            value={hospitalName}
                            onChange={(event) => sethospitalName(event.target.value)}
                            className="formInput"
                        >
                            <option disabled value="">Select a hospital</option>   
                            {medicalCenters.map(hospital => 
                                <option value={hospital.name}>{hospital.name}</option>)}
                        </select>
                    </label>
                    <div className='userTypeSelection'>
                        <label className="formLabel">
                            <input
                                type="radio"
                                value="receptionist"
                                checked={userType === "receptionist"}
                                onChange={() => setUserType("receptionist")}
                            />
                            Register as a Receptionist
                        </label>
                        <label className="formLabel">
                            <input
                                type="radio"
                                value="doctor"
                                checked={userType === "doctor"}
                                onChange={() => setUserType("doctor")}
                            />
                            Register as a Doctor
                        </label>
                    </div>
                    {userType === "doctor" && (
                        <label className="formLabel">
                            <select
                                value={specializationName}
                                onChange={(event) => setSpecializationName(event.target.value)}
                                className="formInput"
                            >
                                <option disabled value="">Select a specialization</option>
                                {specializations.map(specialization => 
                                    <option value={specialization.specializationName}                                   
                                    >{specialization.specializationName}</option>)}
                            </select>
                        </label>
                    )}
                    <button type='submit' className="submitButton">Register</button>
                </form>
                </div>
            </div>
        </>
    );
}
