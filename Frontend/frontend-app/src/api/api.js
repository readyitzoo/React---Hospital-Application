

const api = {

    base : "http://localhost:8080/",

    // AUTH SECTION ////////////////////////////////////////////////////////////////////
    register : async (body) => {
        const bodyJSON = JSON.stringify(body);

        const options = {
            method: "POST",
            body: bodyJSON,
            headers: {"Content-Type": "application/json",}
        }

        const res = await fetch(api.base + "api/auth/register", options);
        return await res.json();
    },

    registerByAccountType : async (body, accountType) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "POST",
            body : JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        }

        console.log(`route : http://localhost:8080/api/${accountType}/create`);
        console.log({body: JSON.stringify(body)});
        
        let res = await fetch(`http://localhost:8080/api/${accountType}/create`, options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);
        
        res = await res.json();

    },

    login : async (body) => {
        const bodyJSON = JSON.stringify(body);

        const options = {
            method : "POST",
            body: bodyJSON,
            headers : {"Content-Type": "application/json",}
        }

        let res = await fetch(api.base + "api/auth/login", options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

    
        const {notification, user, role} = res;

        sessionStorage.setItem('notification', JSON.stringify(notification));
        sessionStorage.setItem('user', JSON.stringify(user));
        sessionStorage.setItem('role', JSON.stringify(role));


        switch(role){
            case 'Patient': window.location.href = "http://localhost:3000/UserNavbar";
                break;
            case 'Receptionist': window.location.href = "http://localhost:3000/ReceptionistNavbar";
                break;
            case 'Hospital Administrator': window.location.href = "http://localhost:3000/Navbar";
                break;
            case 'Doctor' : window.location.href = "http://localhost:3000/DoctorNavbar";
                break;  
            case 'Admin' : window.location.href = "http://localhost:3000/AdminPage";
            //     break;
            // default : window.location.href = "http://localhost:3000/Login";

        }

        return res;
    },

    logout : () => {
        sessionStorage.removeItem('notification');
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('role');

        window.location.href = "http://localhost:3000/Login";
    },
    // PATIENT SECTION ////////////////////////////////////////////////////////////////////

    fetchPatientDataById : async (patientId) =>{
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/patient/read/${patientId}`, options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    }
    ,

    fetchPatientData : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/patient/read/${id}`, options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);
        
        res = await res.json();


        return res;
    },

    updatePatientData: async (updatedData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method: 'PUT',
            body: JSON.stringify(updatedData),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        }

        let res = await fetch(`http://localhost:8080/api/patient/update/${id}`, options);
        
        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);
    
        res = await res.json();

        return res;
    },

    // DOCTOR SECTION ////////////////////////////////////////////////////////////////////
    fetchAllDoctors : async () =>{
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch('http://localhost:8080/api/doctor/readAll',options)

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    fetchDoctorData : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {"Content-Type": "application/json",
                        "Authorization" : `Bearer ${token}`}
        }

        let res = await fetch(`http://localhost:8080/api/doctor/read/${id}`, options);


        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    }
    ,

    fetchDoctorDataById : async (doctorId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/doctor/read/${doctorId}`, options)

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    }
    ,

    updateDoctorData: async (updatedData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options ={
        method: 'PUT',
        body: JSON.stringify(updatedData),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }
    let res = await fetch(`http://localhost:8080/api/doctor/update/${id}`, options);

    if (!res.ok)
    throw new Error(`HTTP error! status: ${res.status}`);

    res = await res.json();

    return res;
},

    fetchDoctorsByHospital : async (hospitalName) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/doctor/readAllByHospital?hospitalName=${hospitalName}`, options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);
    
        res = await res.json();
    
        return res;
        
        },

    //RECEPTIONIST SECTION ////////////////////////////////////////////////////////////////////

    fetchReceptionistData : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
        }

        let res = await fetch(`http://localhost:8080/api/receptionist/read/${id}`, options);
    
        if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
        }

        res = await res.json();

        return res;

    },

    updateReceptionistData : async (updatedData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(updatedData)
        }

        let res = await fetch(`http://localhost:8080/api/receptionist/update/${id}`, options);

    
        if (!res.ok) {
            throw new Error(`HTTP error! status: ${res.status}`);
        }

        res = res.json();

        return res;

    },

    //MEDICAL HISTORY SECTION ////////////////////////////////////////////////////////////////////

    medicalHistoryReadAllByPatient : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;
        
        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/medicalHistory/readAll?patientId=${id}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    fetchMedicalHistoryById : async (patientId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/medicalHistory/readAll?patientId=${patientId}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    medicalHistoryUpdate : async (medicalHistoryId, updatedMedicalHistory) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "PUT",
            headers : {
                "Authorization" : `Bearer ${token}`,
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(updatedMedicalHistory)
        }

        let res = await fetch(`http://localhost:8080/api/medicalHistory/update/${medicalHistoryId}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    medicalHistoryCreate : async (newHistoryData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "POST",
            headers : {
                "Authorization" : `Bearer ${token}`,
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(newHistoryData)
        }

        let res = await fetch('http://localhost:8080/api/medicalHistory/create', options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    //HOSPITAL ADMIN SECTION ////////////////////////////////////////////////////////////////////
    hospitalAdminReadAll : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {"Content-Type": "application/json",
                        "Authorization" : `Bearer ${token}`}
        }

        let res = await fetch(api.base + "api/HospitalAdmin/readAll", options);

        res = await res.json();

        return res;
    },

    fetchHospitalAdminData : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/HospitalAdmin/read/${id}`, options);

        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    updateHospitalAdminData: async (updatedData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method: 'PUT',
            body: JSON.stringify(updatedData),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        }

        let res = await fetch(`http://localhost:8080/api/HospitalAdmin/update/${id}`, options);
        
        if (!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);
    
        res = await res.json();

        return res;
    },

    

    //AVAILABITY SECTION ////////////////////////////////////////////////////////////////////

    availabilitySendForApproval : async (data) =>  {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "POST",
            headers : {
                "Authorization" : `Bearer ${token}`,
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(data)
        }

        let res = await fetch('http://localhost:8080/api/availability/sendForApproval', options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    availabilityForCurrentDoctor : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/availability/read?doctorId=${id}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    availabilityFetchForCurrentHA : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/availability/readAllForApproval?hospitalAdminId=${id}`, options)

        
        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    availabilityApproveById : async (waitingApprovalsID) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "PUT",
            headers : {
                "Authorization" : `Bearer ${token}`
            }        
        }

        let res = await fetch(`http://localhost:8080/api/availability/approve?approveId=${waitingApprovalsID}`, options)

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

    return res;
    },

//MEDICAL CENTERS ////////////////////////////////////////////////////////////////////
    fetchMedicalCenters : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {'Authorization': `Bearer ${token}`}
        }

        let res = await fetch("http://localhost:8080/api/medicalCenter/readAll", options)
        res = await res.json();

        return res;
    },

//APPOINTMENTS ////////////////////////////////////////////////////////////////////
    appointmentReadAllForReceptionist : async () =>{
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/appointment/readAllForReceptionist?receptionistId=${id}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    appointmentReadForPacient : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/appointment/readDateStart?patientId=${id}`, options)

        if(!res.ok)
          throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    appointmentCreateByReceptionist : async (newAppointmentDateTime, appointmentId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body : JSON.stringify(newAppointmentDateTime)
        }

        let res = await fetch(`http://localhost:8080/api/appointment/createByReceptionist?appointmentId=${appointmentId}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    appointmentReadAllForDoctor : async (doctorId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers: {
                'Authorization': `Bearer ${token}`
            },
        }

        let res = await fetch(`http://localhost:8080/api/appointment/readAll?doctorId=${doctorId}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    appointmentReadAllForCurrentDoctor : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/appointment/readAll?doctorId=${id}`, options);

        if(!res.ok)
        throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    },

    appointmentByPatient : async (appointmentData) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;
        const id = user.user_id;

        const options = {
            method : "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(appointmentData)
        }

        let res = await fetch('http://localhost:8080/api/appointment/createByPatient', options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    appointmentDeleteById : async (appointmentId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "DELETE",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/appointment/delete/${appointmentId}`, options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    //INVESTIGATIONS ////////////////////////////////////////////////////////////////////

    investigationsReadAllbyDoctor : async (doctorId) => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch(`http://localhost:8080/api/investigations/readAll?doctorId=${doctorId}`, options);

        if(!res.ok)
        throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;

    },

    //INVESTIGATIONS ////////////////////////////////////////////////////////////////////

    fetchSpecializations : async () => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        const token = user.token;

        const options = {
            method : "GET",
            headers : {
                "Authorization" : `Bearer ${token}`
            }
        }

        let res = await fetch('http://localhost:8080/api/specialization/readAll', options);

        if(!res.ok)
            throw new Error(`HTTP error! status: ${res.status}`);

        res = await res.json();

        return res;
    }


}

export default api;