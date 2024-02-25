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
public class AvailabilityDto {

    @NotEmpty
    private Long doctorId;
    @NotEmpty
    private String day;
    @NotEmpty
    private String timeStart;
    @NotEmpty
    private String timeEnd;
}
