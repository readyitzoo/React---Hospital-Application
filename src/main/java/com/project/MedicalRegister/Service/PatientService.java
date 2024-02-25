package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.PatientDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Mappers.UserMapper;
import com.project.MedicalRegister.Model.User;
import com.project.MedicalRegister.Repository.UserRepository;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final int ROLE_ID_PATIENT = 4;

    public Object getPatientById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            PatientDto patient = userMapper.toPatientDto(user.get());
            patient.setCnp(Long.parseLong(Encryption.decrypt(user.get().getCnp())));
            Map<String, Object> json = Map.of("userId", id,"patientInfo", patient);
            return json;
        } else {
            throw new AppException("Patient not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void updatePatient(Long id, PatientDto patientDto) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User patient = userMapper.patientToUser(patientDto);
            patient.setPassword(passwordEncoder.encode(CharBuffer.wrap(patientDto.getPassword())));
            patient.setCnp(Encryption.encrypt(patientDto.getCnp().toString()));
            patient.setRoleId(ROLE_ID_PATIENT);
            patient.setUser_id(id);
            userRepository.save(patient);
        } else {
            throw new AppException("Patient not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deletePatient(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new AppException("Patient not found", HttpStatus.BAD_REQUEST);
        }
    }
}
