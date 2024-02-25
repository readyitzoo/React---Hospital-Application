import './css/App.css';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import {BrowserRouter, Routes,  Route} from 'react-router-dom';


import ReceptionistGuard from './components/Guards/ReceptionistGuard';
import ReceptionistProfile from "./components/Receptionist/ReceptionistProfile";
import ReceptionistNavbar from './components/Receptionist/ReceptionistNavbar';

import PatientGuard from './components/Guards/PatientGuard';
import UserProfile from "./components/User/UserProfile";
import UserNavbar from "./components/User/UserNavbar";
import UnregisteredUser from './components/User/UnregisteredUser';
import UserCancelAppointments from './components/User/UserCancelAppointments';
import ReceptionistAppointments from './components/Receptionist/ReceptionistAppointments';

import HospitalAdminGuard from './components/Guards/HospitalAdminGuard';
import Navbar from './components/HospitalAdmin/Navbar';
import Approvals from './components/HospitalAdmin/HospitalAdminApprovals';
import RegisterReceptionist from './components/HospitalAdmin/HospitalAdminRegisterReceptionist';
import HospitalAdminProfile from './components/HospitalAdmin/HospitalAdminProfile';

import DoctorGuard from './components/Guards/DoctorGuard';
import DoctorProfile from './components/Doctor/DoctorProfile';
import DoctorNavbar from './components/Doctor/DoctorNavbar';

import AdminGuard from './components/Guards/AdminGuard';
import AdminPage from './components/Admin/AdminPage';
import HospitalAdmin from './components/Admin/HospitalAdmin';
import MedicalCenterAdmin from './components/Admin/MedicalCenterAdmin';
import HospitalAdminAdd from './components/Admin/HospitalAdminAdd';
import HospitalAdminView from './components/Admin/HospitalAdminView';
import HospitalAdminEdit from './components/Admin/HospitalAdminEdit'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>

          {/*AUTH*/}
          <Route exact path="/" element={<UnregisteredUser />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Register" element={<Register />} />
          
          {/* RECEPTIONIST
          <Route path="/ReceptionistProfile" element={<ReceptionistGuard Element={ReceptionistProfile} />} />
          <Route path="/ReceptionistNavbar" element={<ReceptionistGuard Element={ReceptionistNavbar} />} />   
          <Route path="/ReceptionistAppointments" element={<ReceptionistGuard Element={ReceptionistAppointments} />} />    */}


          {/* USER
          <Route path="/UserNavbar" element={<PatientGuard Element={UserNavbar} />} />
          <Route path="/UserProfile" element={<PatientGuard Element={UserProfile} />} />

          <Route path="/UserCancelAppointments" element={<PatientGuard Element={UserCancelAppointments}/>} /> */}

          {/*HOSPITAL ADMIN*/}
          {/*HOSPITAL ADMIN*/}
          <Route path="/Navbar" element={<Navbar />} />
          <Route path="/HospitalAdminApprovals" element={<Approvals />} />
          <Route path="/HospitalAdminRegisterReceptionist" element={<RegisterReceptionist />}/>
          <Route path="/HospitalAdminProfile" element={<HospitalAdminProfile />} />

          {/*DOCTOR*/}
          <Route path="/DoctorProfile" element={<DoctorGuard Element={DoctorProfile}/>} />
          <Route path="/DoctorNavbar" element={<DoctorGuard Element={DoctorNavbar}/>} />

          {/*ADMIN*/}
          <Route path="/AdminPage" element={<AdminGuard Element={AdminPage}/>} />

          <Route path="/HospitalAdmin" element={<AdminGuard Element={HospitalAdmin}/>} />
          <Route path="/HospitalAdminAdd" element={<AdminGuard Element={HospitalAdminAdd}/>} />
          <Route path="/HospitalAdminView" element={<AdminGuard Element={HospitalAdminView}/>} />
          <Route path="/HospitalAdminEdit" element={<AdminGuard Element={HospitalAdminEdit}/>} />

          <Route path="/MedicalCenterAdmin" element={<AdminGuard Element={MedicalCenterAdmin}/>} />

        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
