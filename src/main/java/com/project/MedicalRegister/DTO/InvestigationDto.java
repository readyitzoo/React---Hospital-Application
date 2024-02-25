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
public class InvestigationDto {

    @NotEmpty
    private Long doctorId;
    @NotEmpty
    private String investigationName;
    @NotEmpty
    private Long price;
}
