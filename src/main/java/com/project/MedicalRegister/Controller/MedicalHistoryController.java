package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.MedicalHistoryDto;
import com.project.MedicalRegister.Model.MedicalHistory;
import com.project.MedicalRegister.Service.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/medicalHistory")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @PostMapping("/create")
    public ResponseEntity<String> createMedicalHistory (@ModelAttribute MedicalHistoryDto medicalHistoryDto) throws Exception {
        MedicalHistory createdMedicalHistory = medicalHistoryService.addMedicalHistory(medicalHistoryDto);

        return ResponseEntity.ok("Medical history added successfully with ID: " + createdMedicalHistory.getMedicalHistoryId());
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("medicalHistoryId") Long medicalHistoryId) {
        try {
            MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistoryById(medicalHistoryId);
            HttpHeaders headers = new HttpHeaders();

            if(medicalHistory.getFileName().endsWith(".pdf")){
                headers.setContentType(MediaType.APPLICATION_PDF);
            } else if (medicalHistory.getFileName().endsWith(".docx")) {
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            } else {
                // Handle other file types if needed
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            headers.setContentDispositionFormData("attachment", medicalHistory.getFileName());

//            return new ResponseEntity<>(medicalHistory.getFileContent(), headers, HttpStatus.OK);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(medicalHistory.getFileContent());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<MedicalHistory> readMedicalHistoryById (@PathVariable("id") Long id) throws Exception {
        MedicalHistory medicalHistory = medicalHistoryService.getMedicalHistoryById(id);

        return ResponseEntity.ok(medicalHistory);
    }

    @GetMapping("/readAll")
    public ResponseEntity<Iterable<MedicalHistory>> readMedicalHistoryByPatientId (@RequestParam("patientId") Long patientId) {
        Iterable<MedicalHistory> medicalHistories = medicalHistoryService.getMedicalHistoryByPatientId(patientId);

        return ResponseEntity.ok(medicalHistories);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMedicalHistory (@PathVariable("id") Long id, @RequestBody MedicalHistoryDto medicalHistoryDto) {
        medicalHistoryService.updateMedicalHistory(id, medicalHistoryDto);

        return ResponseEntity.ok("Medical history updated successfully with ID: " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicalHistory (@PathVariable("id") Long id) {
        medicalHistoryService.deleteMedicalHistory(id);

        return ResponseEntity.ok("Medical history deleted successfully with ID: " + id);
    }

}
