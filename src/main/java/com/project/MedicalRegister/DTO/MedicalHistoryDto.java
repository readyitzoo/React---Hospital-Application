package com.project.MedicalRegister.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedicalHistoryDto {

    @NotEmpty
    private Long patientId;
    @NotEmpty
    private Long doctorId;
    @NotEmpty
    private String diagnostic;
    @NotEmpty
    private String treatment;

    private MultipartFile file;
}
