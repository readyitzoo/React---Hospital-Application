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
@Table(name = "waitingapprovalavailability")
public class WaitingApprovals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long waitingApprovalsID;

    @Column(name = "id_medical_center", nullable = false)
    private Long medicalCenterID;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "time_start", nullable = false)
    private String timeStart;

    @Column(name = "time_end", nullable = false)
    private String timeEnd;
}
