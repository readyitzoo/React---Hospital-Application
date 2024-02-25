package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.AvailabilityDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.Availability;
import com.project.MedicalRegister.Model.Doctor;
import com.project.MedicalRegister.Model.WaitingApprovals;
import com.project.MedicalRegister.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AvailabilityService {

    private final AvailabilityRepo availabilityRepo;
    private final WaitingApprovalsRepo waitingApprovalRepo;
    private final DoctorRepository doctorRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final int HOSPITAL_ADMIN_ID = 4;

    @Transactional
    public void addAvailabilityForDoctor(List<AvailabilityDto> availabilityDtoList) {
        for (AvailabilityDto availabilityDto : availabilityDtoList) {
            Availability availability = new Availability();
            availability.setDoctorId(availabilityDto.getDoctorId());
            availability.setDay(availabilityDto.getDay());
            availability.setTimeStart(availabilityDto.getTimeStart());
            availability.setTimeEnd(availabilityDto.getTimeEnd());
            availabilityRepo.save(availability);
        }
    }

    @Transactional
    public void sendForApproval(List<AvailabilityDto> availabilityDtoList) {
        for (AvailabilityDto availabilityDto : availabilityDtoList) {
                // adaugare in listra de aprobari
                Optional<Doctor> doctorInfo = doctorRepository.findByDoctorId(availabilityDto.getDoctorId());
                if (doctorInfo.isPresent()) {
                    Long medicalCenterId = doctorInfo.get().getMedicalCenterId();
                    WaitingApprovals waitingApproval = new WaitingApprovals();
                    waitingApproval.setMedicalCenterID(medicalCenterId);
                    waitingApproval.setDoctorId(availabilityDto.getDoctorId());
                    waitingApproval.setDay(availabilityDto.getDay());
                    waitingApproval.setTimeStart(availabilityDto.getTimeStart());
                    waitingApproval.setTimeEnd(availabilityDto.getTimeEnd());
                    waitingApprovalRepo.save(waitingApproval);
                } else {
                    throw new AppException("Doctor not found", HttpStatus.BAD_REQUEST);
                }
        }
    }

    public Iterable<WaitingApprovals> getAllWaitingForApproval(Long hospitalAdminId) {
        List<WaitingApprovals> result = new ArrayList<>();
        if(!userRepository.findById(hospitalAdminId).get().getRoleId().equals(HOSPITAL_ADMIN_ID)) {
            throw new AppException("User is not a hospital admin", HttpStatus.BAD_REQUEST);
        }
        Long medicalCenterIdForAdmin = contractRepository.findByUserId(hospitalAdminId).get().getMedicalCenterId();
        Optional<Iterable<WaitingApprovals>> waitingApprovals = waitingApprovalRepo.findAllByMedicalCenterID(medicalCenterIdForAdmin);
        if (waitingApprovals.isPresent()) {
            result = (List<WaitingApprovals>) waitingApprovals.get();
            // order result after doctorId
            result.sort(Comparator.comparing(WaitingApprovals::getDoctorId));
        }
        return result;

    }

    @Transactional
    public void updateAvailability(Long waitingApprovalID) {
        Optional<WaitingApprovals> waitingApproval = waitingApprovalRepo.findById(waitingApprovalID);
        if (waitingApproval.isPresent()) {
            WaitingApprovals toBeApproved = waitingApproval.get();
            Optional<Availability> oldAvailability = availabilityRepo.findByDoctorIdAndDay(toBeApproved.getDoctorId(), toBeApproved.getDay());
            if (oldAvailability.isPresent()) {
                Availability newAvailability = oldAvailability.get();
                newAvailability.setTimeStart(toBeApproved.getTimeStart());
                newAvailability.setTimeEnd(toBeApproved.getTimeEnd());
                availabilityRepo.save(newAvailability);
                waitingApprovalRepo.deleteById(waitingApprovalID);
            }
            else {
                throw new AppException("Availability not found", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new AppException("Waiting approval not found", HttpStatus.BAD_REQUEST);
        }
    }

    public Iterable<Availability> getAvailabilityByDoctorId(Long doctorId) {
        Optional<Iterable<Availability>> availabilities = availabilityRepo.findAllByDoctorId(doctorId);
        return availabilities.orElseGet(ArrayList::new);
    }
}
