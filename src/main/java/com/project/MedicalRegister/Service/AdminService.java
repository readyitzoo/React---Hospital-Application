package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.AdminDto;
import com.project.MedicalRegister.DTO.HospitalAdminDto;
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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final int ROLE_ID_ADMIN = 5;


    @Transactional
    public UserDto create(AdminDto adminDto) throws Exception {
        Optional<User> optionalUser = userRepository.findByCnp(Encryption.encrypt(adminDto.getCnp().toString()));
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        //	insert in Users table
        User user = userMapper.adminToUser(adminDto);
        user.setCnp(Encryption.encrypt(adminDto.getCnp().toString()));
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(adminDto.getPassword())));
        user.setRoleId(ROLE_ID_ADMIN);
        user.setRole(UserRole.ADMIN);

        User savedUser = userRepository.save(user);

        UserDto result = userMapper.toUserDto(savedUser);
        result.setCnp(Long.parseLong(Encryption.decrypt(savedUser.getCnp())));
        return result;
    }
}
