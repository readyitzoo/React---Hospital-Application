package com.project.MedicalRegister.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WaitingAppointmentDto {

    @NotEmpty
    private Long medicalCenterId;
    @NotEmpty
    private Long patientId;
    @NotEmpty
    private Long doctorId;
    @NotEmpty
    private Long investigationId;
}