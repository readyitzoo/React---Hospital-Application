package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.WaitingAppointmentDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.Appointment;
import com.project.MedicalRegister.Model.Notification;
import com.project.MedicalRegister.Model.WaitingAppointment;
import com.project.MedicalRegister.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final ContractRepository contractRepository;
    private final WaitingAppointmentRepo waitingAppointmentRepo;
    private final NotificationRepo notificationRepo;
    private final MedicalCenterRepo medicalCenterRepo;

    @Transactional
    public void addAppointmentByPatient(WaitingAppointmentDto waitingAppointmentDto) {
        WaitingAppointment waitingAppointment = new WaitingAppointment();
        waitingAppointment.setMedicalCenterId(waitingAppointmentDto.getMedicalCenterId());
        waitingAppointment.setPatientId(waitingAppointmentDto.getPatientId());
        waitingAppointment.setDoctorId(waitingAppointmentDto.getDoctorId());
        waitingAppointment.setInvestigationId(waitingAppointmentDto.getInvestigationId());
        waitingAppointmentRepo.save(waitingAppointment);
    }


    public Iterable<WaitingAppointment> getAllAppointmentsForReceptionist(Long receptionistId) {
        Long medicalCenterIdForRecep = contractRepository.findByUserId(receptionistId).get().getMedicalCenterId();
        Optional<List<WaitingAppointment>> waitingAppointments = waitingAppointmentRepo.findAllByMedicalCenterId(medicalCenterIdForRecep);
        if (waitingAppointments.isPresent()) {
            List<WaitingAppointment> waitingAppointmentsList = waitingAppointments.get();
            waitingAppointmentsList.sort(Comparator.comparing(WaitingAppointment :: getWaitingAppointmentId));
            return waitingAppointmentsList;
        }
        return null;
    }

    @Transactional
    public void addAppointmentByReceptionist(Long appointmentId, String dateTime) {
        Optional<WaitingAppointment> waitingAppointment = waitingAppointmentRepo.findById(appointmentId);
        if (waitingAppointment.isPresent()) {
            Appointment newAppointment = new Appointment();
            newAppointment.setPatientId(waitingAppointment.get().getPatientId());
            newAppointment.setDoctorId(waitingAppointment.get().getDoctorId());
            newAppointment.setInvestigationId(waitingAppointment.get().getInvestigationId());
            newAppointment.setMedicalCenterId(waitingAppointment.get().getMedicalCenterId());
            newAppointment.setAppointmentDateTime(dateTime);
            appointmentRepo.save(newAppointment);
            Notification notification = new Notification();
            notification.setPatientId(newAppointment.getPatientId());
            notification.setDoctorId(newAppointment.getDoctorId());
            notification.setMessage("New appointment is scheduled for " + newAppointment.getAppointmentDateTime() + " at " + medicalCenterRepo.findById(newAppointment.getMedicalCenterId()).get().getName());
            notificationRepo.save(notification);
            waitingAppointmentRepo.deleteById(appointmentId);
        }
        else {
            throw new AppException("Appointment not found", HttpStatus.BAD_REQUEST);
        }
    }

    public Iterable<Appointment> getAllAppointmentsByDoctorId(Long doctorId) {
        Optional<Iterable<Appointment>> appointments = appointmentRepo.findAllByDoctorId(doctorId);
        return appointments.orElseGet(ArrayList::new);
    }

    public Iterable<Appointment> getAllAppointmentsStartsFromTodayForPatient(Long patientId, String today) {
        Optional<Iterable<Appointment>> appointments = appointmentRepo.findAllByPatientId(patientId);
        if (appointments.isPresent()) {
            List<Appointment> appointmentsInFuture = new ArrayList<>();

            for (Appointment appointment : appointments.get()) {
                DateTimeFormatter formatterDataSiOra = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                DateTimeFormatter formatterDoarData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String dataSiOraString = appointment.getAppointmentDateTime();
                LocalDateTime dataSiOra = LocalDateTime.parse(dataSiOraString, formatterDataSiOra);
                String doarDataString = dataSiOra.format(formatterDoarData);
                LocalDate data = LocalDate.parse(doarDataString, formatterDoarData);

                if (data.isAfter(LocalDate.parse(today))) {
                    appointmentsInFuture.add(appointment);
                }
            }
            return appointmentsInFuture;
        }
        return appointments.orElseGet(ArrayList::new);
    }

    public void deleteAppointment(Long id) {
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        if (appointment.isPresent()) {
            appointmentRepo.deleteById(id);
        }
        else {
            throw new AppException("Appointment not found", HttpStatus.BAD_REQUEST);
        }
    }
}
