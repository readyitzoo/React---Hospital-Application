package com.project.MedicalRegister.Mappers;

import com.project.MedicalRegister.DTO.*;
import com.project.MedicalRegister.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "cnp", ignore = true)
    UserDto toUserDto(User user);
    @Mapping(target = "cnp", ignore = true)
    ReceptionistDto toReceptionistDto(User user);
    @Mapping(target = "cnp", ignore = true)
    DoctorDto toDoctorDto(User user);
    @Mapping(target = "cnp", ignore = true)
    PatientDto toPatientDto(User user);

    Iterable<DoctorDto> toDoctorDtoList(Iterable<User> usersList);
    @Mapping(target = "cnp", ignore = true)
    HospitalAdminDto toHospitalAdminDto(User user);
    @Mapping(target = "cnp", ignore = true)
    Iterable<HospitalAdminDto> toHospitalAdminDtoList(Iterable<User> users) throws Exception;


    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User hospitalAdminToUser(HospitalAdminDto hospitalAdminDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User doctorToUser(DoctorDto doctorDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User ReceptionistToUser(ReceptionistDto receptionistDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User patientToUser(PatientDto patientDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cnp", ignore = true)
    User adminToUser(AdminDto adminDto);
}