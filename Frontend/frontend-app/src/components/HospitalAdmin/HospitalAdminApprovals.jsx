import React, { useState, useEffect } from 'react';
import Navbar from './Navbar'; // Import the Navbar component
import '../../css/ReceptionistListaDoctori.css';
import api from '../../api/api';

function HospitalAdminApprovals() {
  const [waitingApprovals, setWaitingApprovals] = useState([]);
  const [approvedSchedules, setApprovedSchedules] = useState([]);

  useEffect(() => {
    fetchWaitingApprovals();
    // You may want to fetch and set already approved schedules if needed
  }, []);

  const fetchWaitingApprovals = async () => {
    console.log('1. api.availabilityFetchForCurrentHA();');
    const data = await api.availabilityFetchForCurrentHA();
    console.log('1. resolved', data);

    setWaitingApprovals(data);
  };

  // UNUSED
  const fetchDoctorDetails = async (doctorId) => {
    const data = api.fetchDoctorDataById(doctorId);
    return data.doctorInfo;
  };

  const handleApproveSchedule = async (waitingApproval) => {
    console.log('2. api.availabilityApproveById(waitingApprovals.waitingApprovalsID);');
    await api.availabilityApproveById(waitingApproval.waitingApprovalsID);
    console.log('2. resolved');

    setApprovedSchedules((prevSchedules) => [...prevSchedules, waitingApproval.waitingApprovalsID]);

    console.log(`Schedule with ID ${waitingApproval.waitingApprovalsID} has been approved.`);
  };

  return (
    <>
      <Navbar activeItem={'HospitalAdminApprovals'} setActiveItem={() => {}} />
      <div className="approvals_hospital_admin">
        <div className='filler'>
          <h1 class="joinHeader">Waiting Approvals</h1>
        </div>
        {waitingApprovals.length > 0 ? (
          <ul>
            {waitingApprovals.map((waitingApproval) => (
              <li key={waitingApproval.waitingApprovalsID} class="doctor-appointments_recep">
                {/* <div>Doctor: {doctorsDetails.find(item => item.doctorID = waitingApproval.doctorID).doctorName || 'Unknown Doctor'}</div> */}
                <div>Doctor: {'Unknown Doctor'}</div>
                <div>Day: {waitingApproval.day}</div>
                <div>Time Start: {waitingApproval.timeStart}</div>
                <div>Time End: {waitingApproval.timeEnd}</div>
                {approvedSchedules.includes(waitingApproval.waitingApprovalsID) ? (
                  <div>Approved</div>
                ) : (
                  <button onClick={() => handleApproveSchedule(waitingApproval)} class="buton">
                    Approve Schedule
                  </button>
                )}
              </li>
            ))}
          </ul>
        ) : (
          <p>No waiting approvals to approve.</p>
        )}
      </div>
    </>
  );
}

export default HospitalAdminApprovals;
