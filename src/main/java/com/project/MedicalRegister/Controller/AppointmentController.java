package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.WaitingAppointmentDto;
import com.project.MedicalRegister.Model.Appointment;
import com.project.MedicalRegister.Model.WaitingAppointment;
import com.project.MedicalRegister.Service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // se trimite catre receptionist
    @PostMapping("/createByPatient")
    public ResponseEntity<String> createAppointmentByPatient(@RequestBody WaitingAppointmentDto incompleteAppointmentDto) {
        appointmentService.addAppointmentByPatient(incompleteAppointmentDto);

        return ResponseEntity.ok("Appointment was sent successfully");
    }

    @GetMapping("/readAllForReceptionist")
    public ResponseEntity<Iterable<WaitingAppointment>> getAllAppointmentsForReceptionist(@RequestParam("receptionistId") Long receptionistId) {
        Iterable<WaitingAppointment> appointments = appointmentService.getAllAppointmentsForReceptionist(receptionistId);
        return ResponseEntity.ok(appointments);
    }

    // id ul este din tabelul waiting appointments
    // se seteaza data si ora programarii
    @PostMapping("createByReceptionist")
    public ResponseEntity<String> createAppointmentByReceptionist(@RequestParam("appointmentId") Long appointmentId, @RequestBody String DateTime) {
        appointmentService.addAppointmentByReceptionist(appointmentId, DateTime);

        return ResponseEntity.ok("Appointment created successfully");
    }


    @GetMapping("/readAll")
    public ResponseEntity<Iterable<Appointment>> getAllAppointmentsByDoctorId(@RequestParam("doctorId") Long doctorId) {
        Iterable<Appointment> appointments = appointmentService.getAllAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/readDateStart")
    public ResponseEntity<Iterable<Appointment>> getAllAppointmentsStartingFromToday(@RequestParam("patientId") Long patientId) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = currentDate.format(formatter);
        Iterable<Appointment> appointments = appointmentService.getAllAppointmentsStartsFromTodayForPatient(patientId, today);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully with ID: " + id);
    }


}
