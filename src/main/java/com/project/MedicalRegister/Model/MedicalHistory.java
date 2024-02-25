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
@Table(name = "medicalhistory")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id", nullable = false)
    private Long medicalHistoryId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "diagnostic", nullable = false)
    private String diagnostic;

    @Column(name = "treatment", nullable = false)
    private String treatment;

    @Column(name = "file_name", nullable = true)
    private String fileName;

    @Lob
    @Column(name = "file_content", nullable = true)
    private byte[] fileContent;

}
