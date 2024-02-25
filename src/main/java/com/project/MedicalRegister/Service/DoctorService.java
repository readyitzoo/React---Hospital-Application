package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.DoctorDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Mappers.UserMapper;
import com.project.MedicalRegister.Model.*;
import com.project.MedicalRegister.Repository.*;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final UserRepository userRepository;
    private final MedicalCenterRepo medicalCenterRepository;
    private final ContractRepository contractRepository;
    private final SpecializationRepo specializationRepository;
    private final DoctorRepository doctorRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final int ROLE_ID_DOCTOR = 2;

    @Transactional
    public UserDto addDoctor(DoctorDto doctor) throws Exception {
        Optional<User> optionalUser = userRepository.findByCnp(Encryption.encrypt(doctor.getCnp().toString()));
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        //	insert in Users table
        User user = userMapper.doctorToUser(doctor);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(doctor.getPassword())));
        user.setCnp(Encryption.encrypt(doctor.getCnp().toString()));
        user.setRoleId(ROLE_ID_DOCTOR);
        user.setRole(UserRole.DOCTOR);

        User savedUser = userRepository.save(user);

        // insert in 'doctors' table
        Doctor doctorContract = new Doctor();
        doctorContract.setDoctorId(savedUser.getUser_id());
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(doctor.getHospitalName());
        Optional<Specialization> optionalSpecialization = specializationRepository.findBySpecializationName(doctor.getSpecializationName());

        if (optionalSpecialization.isEmpty() || optionalMedicalCenter.isEmpty()) {
            userRepository.delete(savedUser);
            throw new AppException("Specialization or Hospital does not exist", HttpStatus.BAD_REQUEST);

        } else {
            Long medicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
            Long specializationId = optionalSpecialization.get().getSpecializationId();
            doctorContract.setMedicalCenterId(medicalCenterId);
            doctorContract.setSpecializationId(specializationId);

            doctorRepository.save(doctorContract);
        }

        UserDto result = userMapper.toUserDto(savedUser);
        result.setCnp(Long.parseLong(Encryption.decrypt(savedUser.getCnp())));
        return result;

    }

    @Transactional
    public void deleteDoctor(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Doctor> optionalDoctor = doctorRepository.findByDoctorId(id);

        if (optionalUser.isEmpty() || optionalDoctor.isEmpty()) {
            throw new AppException("Doctor does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            doctorRepository.delete(optionalDoctor.get());
            userRepository.delete(optionalUser.get());
        }
    }

    public DoctorDto getDoctor(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Doctor> optionalDoctor = doctorRepository.findByDoctorId(id);

        if (optionalUser.isEmpty() || optionalDoctor.isEmpty()) {
            throw new AppException("Doctor does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            DoctorDto doctorDto = userMapper.toDoctorDto(optionalUser.get());
            doctorDto.setCnp(Long.parseLong(Encryption.decrypt(optionalUser.get().getCnp())));
            doctorDto.setHospitalName(medicalCenterRepository.findById(optionalDoctor.get().getMedicalCenterId()).get().getName());
            doctorDto.setSpecializationName(specializationRepository.findById(optionalDoctor.get().getSpecializationId()).get().getSpecializationName());
            return doctorDto;
        }
    }

    @Transactional
    public void updateDoctor(Long id, DoctorDto doctorDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Doctor> optionalDoctor = doctorRepository.findByDoctorId(id);

        if (optionalUser.isEmpty() || optionalDoctor.isEmpty()) {
            throw new AppException("Doctor does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            User updatedUser = optionalUser.get();

            updatedUser.setFirstName(doctorDto.getFirstName());
            updatedUser.setLastName(doctorDto.getLastName());
            updatedUser.setPhoneNumber(doctorDto.getPhoneNumber());
            updatedUser.setEmail(doctorDto.getEmail());
            updatedUser.setCnp(Encryption.encrypt(doctorDto.getCnp().toString()));
            updatedUser.setRoleId(ROLE_ID_DOCTOR);
            updatedUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(doctorDto.getPassword())));

            Doctor updatedDoctor = optionalDoctor.get();
            Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(doctorDto.getHospitalName());
            Optional<Specialization> optionalSpecialization = specializationRepository.findBySpecializationName(doctorDto.getSpecializationName());
            if (optionalSpecialization.isEmpty()) {
                throw new AppException("Specialization does not exist", HttpStatus.BAD_REQUEST);
            }
            else if (optionalMedicalCenter.isEmpty()) {
                throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);
            }
            else {
                updatedDoctor.setMedicalCenterId(optionalMedicalCenter.get().getMedicalCenterId());
                updatedDoctor.setSpecializationId(optionalSpecialization.get().getSpecializationId());

            }

            userRepository.save(updatedUser);
            doctorRepository.save(updatedDoctor);
        }
    }

    public List<Object> getAllDoctors() throws Exception {
        List<Object> result = new ArrayList<>();
        Optional<Iterable<User>> users = userRepository.findAllByRoleId(ROLE_ID_DOCTOR);
        if (users.isEmpty()) {
            throw new AppException("No doctors found", HttpStatus.BAD_REQUEST);
        }
        else {
            Iterable<User> usersList = users.get();
            Iterable<DoctorDto> doctorsList = userMapper.toDoctorDtoList(usersList);
            for (DoctorDto doctorDto : doctorsList) {
                doctorDto.setCnp(Long.parseLong(Encryption.decrypt(userRepository.findByEmail(doctorDto.getEmail()).get().getCnp())));
                Optional<Doctor> optionalDoctor = doctorRepository.findByDoctorId(userRepository.findByCnp(Encryption.encrypt(doctorDto.getCnp().toString())).get().getUser_id());
                if (optionalDoctor.isEmpty()) {
                    throw new AppException("Doctor Info does not exist", HttpStatus.BAD_REQUEST);
                }
                doctorDto.setCnp(Long.parseLong(Encryption.decrypt(userRepository.findByCnp(Encryption.encrypt(doctorDto.getCnp().toString())).get().getCnp())));
                doctorDto.setHospitalName(medicalCenterRepository.findById(optionalDoctor.get().getMedicalCenterId()).get().getName());
                doctorDto.setSpecializationName(specializationRepository.findById(optionalDoctor.get().getSpecializationId()).get().getSpecializationName());
                Map<String, Object> json = Map.of("userId", optionalDoctor.get().getDoctorId(),"doctorInfo", doctorDto);
                result.add(json);
            }
            result.sort(new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String lastName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getLastName();
                    String lastName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getLastName();
                    String firstName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getFirstName();
                    String firstName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getFirstName();
                    if (lastName1.compareTo(lastName2) == 0) {
                        return firstName1.compareTo(firstName2);
                    } else {
                        return lastName1.compareTo(lastName2);
                    }
                }
            });
            return result;
        }
    }

    public List<Object> getAllDoctorsByHospital(String hospitalName) throws Exception {
        List<Object> result = new ArrayList<>();
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(hospitalName);
        if (optionalMedicalCenter.isEmpty()) {
            throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            Long medicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
            Optional<Iterable<Doctor>> doctors = doctorRepository.findAllByMedicalCenterId(medicalCenterId);
            if (doctors.isEmpty()) {
                throw new AppException("No doctors found", HttpStatus.BAD_REQUEST);
            }
            else {
                Iterable<Doctor> existingDoctors = doctors.get();
                for (Doctor doctor : existingDoctors) {
                    Long doctorId = doctor.getDoctorId();
                    Optional<User> optionalUser = userRepository.findById(doctorId);
                    if (optionalUser.isEmpty()) {
                        throw new AppException("Doctor does not exist", HttpStatus.BAD_REQUEST);
                    }
                    else {
                        User user = optionalUser.get();
                        DoctorDto doctorDto = userMapper.toDoctorDto(user);
                        doctorDto.setCnp(Long.parseLong(Encryption.decrypt(userRepository.findByCnp(user.getCnp()).get().getCnp())));
                        doctorDto.setHospitalName(hospitalName);
                        doctorDto.setSpecializationName(specializationRepository.findById(doctorRepository.findByDoctorId(userRepository.findByCnp(Encryption.encrypt(doctorDto.getCnp().toString())).get().getUser_id()).get().getSpecializationId()).get().getSpecializationName());
                        Map<String, Object> json = Map.of("userId", doctorId,"doctorInfo", doctorDto);
                        result.add(json);
                    }
                }
            }
        }
        Collections.sort(result, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String lastName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getLastName();
                String lastName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getLastName();
                String firstName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getFirstName();
                String firstName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getFirstName();
                if (lastName1.compareTo(lastName2) == 0) {
                    return firstName1.compareTo(firstName2);
                }
                else {
                    return lastName1.compareTo(lastName2);
                }
            }
        });
        return result;
    }

    public List<Object> getAllDoctorsBySpecialization(String specializationName) throws Exception {
        List<Object> result = new ArrayList<>();
        Optional<Specialization> optionalSpecialization = specializationRepository.findBySpecializationName(specializationName);
        if (!optionalSpecialization.isPresent()) {
            throw new AppException("Specialization does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            Long specializationId = optionalSpecialization.get().getSpecializationId();
            Optional<Iterable<Doctor>> doctors = doctorRepository.findAllBySpecializationId(specializationId);
            if (!doctors.isPresent()) {
                throw new AppException("No doctors found", HttpStatus.BAD_REQUEST);
            }
            else {
                Iterable<Doctor> existingDoctors = doctors.get();
                for (Doctor doctor : existingDoctors) {
                    Long doctorId = doctor.getDoctorId();
                    Optional<User> optionalUser = userRepository.findById(doctorId);
                    if (!optionalUser.isPresent()) {
                        throw new AppException("Doctor does not exist", HttpStatus.BAD_REQUEST);
                    }
                    else {
                        User user = optionalUser.get();
                        DoctorDto doctorDto = userMapper.toDoctorDto(user);
                        doctorDto.setCnp(Long.parseLong(Encryption.decrypt(userRepository.findByCnp(user.getCnp()).get().getCnp())));
                        doctorDto.setHospitalName(medicalCenterRepository.findById(doctorRepository.findByDoctorId(userRepository.findByCnp(Encryption.encrypt(doctorDto.getCnp().toString())).get().getUser_id()).get().getMedicalCenterId()).get().getName());
                        doctorDto.setSpecializationName(specializationName);
                        Map<String, Object> json = Map.of("userId", doctorId,"doctorInfo", doctorDto);
                        result.add(json);
                    }
                }
            }
        }
        result.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String lastName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getLastName();
                String lastName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getLastName();
                String firstName1 = ((DoctorDto) ((Map) o1).get("doctorInfo")).getFirstName();
                String firstName2 = ((DoctorDto) ((Map) o2).get("doctorInfo")).getFirstName();
                if (lastName1.compareTo(lastName2) == 0) {
                    return firstName1.compareTo(firstName2);
                } else {
                    return lastName1.compareTo(lastName2);
                }
            }
        });
        return result;
    }
}
