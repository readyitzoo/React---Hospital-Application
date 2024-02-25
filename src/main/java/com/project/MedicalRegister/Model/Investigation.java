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
@Table(name = "medicalinvestigation")
public class Investigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investigation_id", nullable = false)
    private Long investigationId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "nume_investigatie", nullable = false)
    private String investigationName;

    @Column(name = "pret", nullable = false)
    private Long price;

}