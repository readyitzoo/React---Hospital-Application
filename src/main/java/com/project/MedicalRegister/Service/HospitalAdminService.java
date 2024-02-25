package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.CredentialsDto;
import com.project.MedicalRegister.DTO.HospitalAdminDto;
import com.project.MedicalRegister.DTO.SignUpDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Mappers.UserMapper;
import com.project.MedicalRegister.Model.ContractMedicalCenter;
import com.project.MedicalRegister.Model.MedicalCenter;
import com.project.MedicalRegister.Model.User;
import com.project.MedicalRegister.Model.UserRole;
import com.project.MedicalRegister.Repository.ContractRepository;
import com.project.MedicalRegister.Repository.MedicalCenterRepo;
import com.project.MedicalRegister.Repository.UserRepository;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HospitalAdminService {

    private final UserRepository userRepository;
    private final MedicalCenterRepo medicalCenterRepository;
    private final ContractRepository contractRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final int ROLE_ID_HOSPITAL_ADMIN = 4;


    @Transactional
    public UserDto addHospitalAdmin(HospitalAdminDto hospitalAdmin) throws Exception {
        Optional<User> optionalUser = userRepository.findByCnp(hospitalAdmin.getCnp().toString());
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        //	insert in Users table
        User user = userMapper.hospitalAdminToUser(hospitalAdmin);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(hospitalAdmin.getPassword())));
        user.setCnp(hospitalAdmin.getCnp().toString());
        user.setRoleId(ROLE_ID_HOSPITAL_ADMIN);
        user.setRole(UserRole.HOSPITAL_ADMIN);

        User savedUser = userRepository.save(user);

        // insert in ContractMedicalCenter table
        ContractMedicalCenter contract = new ContractMedicalCenter();
        contract.setUserId(savedUser.getUser_id());
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(hospitalAdmin.getHospitalName());
        if (optionalMedicalCenter.isEmpty()) {
            userRepository.delete(savedUser);
            throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);

        } else {
            Long medicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
            contract.setMedicalCenterId(medicalCenterId);

            contractRepository.save(contract);
        }

        UserDto result = userMapper.toUserDto(savedUser);
        result.setCnp(Long.parseLong(savedUser.getCnp()));
        return result;
    }

    @Transactional
    public void deleteHospitalAdmin(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (optionalUser.isEmpty()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        } else if (optionalContract.isEmpty()) {
            throw new AppException("Hospital admin does not exist", HttpStatus.BAD_REQUEST);
        } else {
            contractRepository.delete(optionalContract.get());
            userRepository.delete(optionalUser.get());
        }
    }

    public Object getHospitalAdmin(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (!optionalUser.isPresent()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        } else if (!optionalContract.isPresent()) {
            throw new AppException("Hospital admin does not exist", HttpStatus.BAD_REQUEST);
        } else {
            HospitalAdminDto hospitalAdminDto = userMapper.toHospitalAdminDto(optionalUser.get());
            hospitalAdminDto.setCnp(Long.parseLong(optionalUser.get().getCnp()));
            hospitalAdminDto.setHospitalName(medicalCenterRepository.findById(optionalContract.get().getMedicalCenterId()).get().getName());
            Map<String, Object> response = Map.of("userId", id,"Hospital Admin", hospitalAdminDto);
            return response;
        }
    }

    public List<Object> getAllHospitalAdmins() throws Exception {
        List<Object> result = new ArrayList<>();
        Optional<Iterable<User>> optionalUsers = userRepository.findAllByRoleId(ROLE_ID_HOSPITAL_ADMIN);
        if (!optionalUsers.isPresent()) {
            throw new AppException("No hospital admins found", HttpStatus.BAD_REQUEST);
        } else {
            Iterable<User> users = optionalUsers.get();
            Iterable<HospitalAdminDto> hospitalAdmins = userMapper.toHospitalAdminDtoList(users);
            for (HospitalAdminDto hospitalAdmin : hospitalAdmins) {
//                System.out.println(hospitalAdmin.getEmail());
                hospitalAdmin.setCnp(Long.parseLong(userRepository.findByEmail(hospitalAdmin.getEmail()).get().getCnp()));

                User user = userRepository.findByEmail(hospitalAdmin.getEmail()).get();
                ContractMedicalCenter contract = contractRepository.findByUserId(user.getUser_id()).get();
                hospitalAdmin.setHospitalName(medicalCenterRepository.findById(contract.getMedicalCenterId()).get().getName());
                Map<String, Object> json = Map.of("userId", userRepository.findByCnp(hospitalAdmin.getCnp().toString()).get().getUser_id(),"Hospital Admin", hospitalAdmin);
                result.add(json);
            }
            return result;
        }
    }

    @Transactional
    public void updateHospitalAdmin(Long id, HospitalAdminDto hospitalAdminDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (!optionalUser.isPresent()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        } else if (!optionalContract.isPresent()) {
            throw new AppException("Hospital admin does not exist", HttpStatus.BAD_REQUEST);
        } else {
            User updatedUser = optionalUser.get();
            updatedUser.setFirstName(hospitalAdminDto.getFirstName());
            updatedUser.setLastName(hospitalAdminDto.getLastName());
            updatedUser.setEmail(hospitalAdminDto.getEmail());
            updatedUser.setCnp(hospitalAdminDto.getCnp().toString());
            updatedUser.setPhoneNumber(hospitalAdminDto.getPhoneNumber());
            updatedUser.setRoleId(ROLE_ID_HOSPITAL_ADMIN);
            updatedUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(hospitalAdminDto.getPassword())));

            userRepository.save(updatedUser);

            ContractMedicalCenter updatedContract = optionalContract.get();
            Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(hospitalAdminDto.getHospitalName());
            if (!optionalMedicalCenter.isPresent()) {
                throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);
            } else {
                updatedContract.setMedicalCenterId(optionalMedicalCenter.get().getMedicalCenterId());
                contractRepository.save(updatedContract);
            }
        }
    }
}
