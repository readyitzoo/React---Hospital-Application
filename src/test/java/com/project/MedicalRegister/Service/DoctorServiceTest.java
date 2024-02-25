package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.DoctorDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Mappers.UserMapper;
import com.project.MedicalRegister.Model.*;
import com.project.MedicalRegister.Repository.*;
import com.project.MedicalRegister.Util.Encryption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private MedicalCenterRepo medicalCenterRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private SpecializationRepo specializationRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDoctor_WhenDoctorExists_ShouldReturnDoctorDto() throws Exception {

        Long userId = 1L;
        Long doctorId = 1L;

        User user = User.builder()
                .user_id(userId)
                .cnp(Encryption.encrypt("1234567890123"))
                .password("password")
                .role(UserRole.DOCTOR)
                .email("doctor@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber(123456789L)
                .roleId(2)
                .build();

        Doctor doctor = Doctor.builder()
                .tableId(1L)
                .doctorId(doctorId)
                .specializationId(1L)
                .medicalCenterId(1L)
                .build();

        when(medicalCenterRepository.findById(1L)).thenReturn(Optional.of(MedicalCenter.builder().name("Medical Center 1").build()));
        when(specializationRepository.findById(1L)).thenReturn(Optional.of(Specialization.builder().specializationName("Cardiology").build()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(doctorRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(doctor));
        when(userMapper.toDoctorDto(user)).thenReturn(
                DoctorDto.builder()
                        .cnp(1234567890123L)
                        .email("doctor@example.com")
                        .password("password")
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber(123456789L)
                        .hospitalName("Medical Center 1")
                        .specializationName("Cardiology")
                        .build()
        );


        DoctorDto result = doctorService.getDoctor(userId);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(1234567890123L, result.getCnp());
        assertEquals("doctor@example.com", result.getEmail());
        assertEquals("Medical Center 1", result.getHospitalName());
        assertEquals("Cardiology", result.getSpecializationName());

        verify(userRepository, times(1)).findById(userId);
        verify(doctorRepository, times(1)).findByDoctorId(doctorId);
    }

    @Test
    void getDoctor_WhenDoctorDoesNotExist_ShouldThrowAppException() {

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        assertThrows(AppException.class, () -> doctorService.getDoctor(userId));


        verify(userRepository, times(1)).findById(userId);

        verify(doctorRepository, times(1)).findByDoctorId(anyLong());
        verify(medicalCenterRepository, never()).findById(anyLong());
        verify(specializationRepository, never()).findById(anyLong());
    }

    @Test
    void getAllDoctorsByHospital_WhenHospitalExistsAndDoctorsExist_ShouldReturnDoctorList() throws Exception {

        String hospitalName = "Medical Center 1";
        String location = "Location 1";
        Long medicalCenterId = 1L;
        Long doctorId = 1L;

        MedicalCenter medicalCenter = new MedicalCenter(medicalCenterId, hospitalName, location);

        when(medicalCenterRepository.findByName(hospitalName)).thenReturn(Optional.of(medicalCenter));

        Iterable<Doctor> existingDoctors = Arrays.asList(
                Doctor.builder()
                        .tableId(1L)
                        .doctorId(doctorId)
                        .specializationId(1L)
                        .medicalCenterId(medicalCenterId)
                        .build()
        );

        when(doctorRepository.findAllByMedicalCenterId(medicalCenterId)).thenReturn(Optional.of(existingDoctors));

        User user = User.builder()
                .user_id(doctorId)
                .cnp(Encryption.encrypt("1234567890123"))
                .password("password")
                .role(UserRole.DOCTOR)
                .email("doctor@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber(123456789L)
                .roleId(2)
                .build();

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(user));

        when(userMapper.toDoctorDto(user)).thenReturn(
                DoctorDto.builder()
                        .cnp(1234567890123L)
                        .email("doctor@example.com")
                        .password("password")
                        .firstName("John")
                        .lastName("Doe")
                        .phoneNumber(123456789L)
                        .hospitalName(hospitalName)
                        .specializationName("Cardiology")
                        .build()
        );

        when(userRepository.findByCnp((String) any())).thenReturn(Optional.of(user));
        when(doctorRepository.findByDoctorId(doctorId)).thenReturn(Optional.of(Doctor.builder().doctorId(doctorId).specializationId(1L).medicalCenterId(medicalCenterId).build()));
        when(specializationRepository.findById(1L)).thenReturn(Optional.of(new Specialization(1L, "Cardiology")));


        List<Object> result = doctorService.getAllDoctorsByHospital(hospitalName);


        assertEquals(1, result.size());

        Map<String, Object> doctorInfo = (Map<String, Object>) result.get(0);
        assertEquals(doctorId, doctorInfo.get("userId"));

        DoctorDto doctorDto = (DoctorDto) doctorInfo.get("doctorInfo");
        assertEquals("John", doctorDto.getFirstName());
        assertEquals("Doe", doctorDto.getLastName());
        assertEquals(1234567890123L, doctorDto.getCnp());
        assertEquals("doctor@example.com", doctorDto.getEmail());
        assertEquals(hospitalName, doctorDto.getHospitalName());
        assertEquals("Cardiology", doctorDto.getSpecializationName());


        verify(medicalCenterRepository, times(1)).findByName(hospitalName);
        verify(doctorRepository, times(1)).findAllByMedicalCenterId(medicalCenterId);
        verify(userRepository, times(1)).findById(doctorId);
        verify(userMapper, times(1)).toDoctorDto(user);
        verify(specializationRepository, times(1)).findById(1L);
    }

    @Test
    void getAllDoctorsByHospital_WhenHospitalDoesNotExist_ShouldThrowAppException() {

        String hospitalName = "Nonexistent Hospital";

        when(medicalCenterRepository.findByName(hospitalName)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> doctorService.getAllDoctorsByHospital(hospitalName));

        verify(medicalCenterRepository, times(1)).findByName(hospitalName);
        verify(doctorRepository, never()).findAllByMedicalCenterId(anyLong());
        verify(userRepository, never()).findById(anyLong());
        verify(userMapper, never()).toDoctorDto(any());
        verify(specializationRepository, never()).findById(anyLong());
    }

    @Test
    void getAllDoctorsByHospital_WhenDoctorsDoNotExist_ShouldThrowAppException() {

        String hospitalName = "Medical Center 1";
        String location = "Location 1";
        Long medicalCenterId = 1L;

        MedicalCenter medicalCenter = new MedicalCenter(medicalCenterId, hospitalName, location);

        when(medicalCenterRepository.findByName(hospitalName)).thenReturn(Optional.of(medicalCenter));
        when(doctorRepository.findAllByMedicalCenterId(medicalCenterId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> doctorService.getAllDoctorsByHospital(hospitalName));


        verify(medicalCenterRepository, times(1)).findByName(hospitalName);
        verify(doctorRepository, times(1)).findAllByMedicalCenterId(medicalCenterId);
        verify(userRepository, never()).findById(anyLong());
        verify(userMapper, never()).toDoctorDto(any());
        verify(specializationRepository, never()).findById(anyLong());
    }
}

