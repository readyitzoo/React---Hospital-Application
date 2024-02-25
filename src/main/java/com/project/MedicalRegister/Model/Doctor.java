package com.project.MedicalRegister.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "val_id", nullable = false)
    private Long tableId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "specialization_id", nullable = false)
    private Long specializationId;

    @Column(name = "medical_center_id", nullable = false)
    private Long medicalCenterId;

}
