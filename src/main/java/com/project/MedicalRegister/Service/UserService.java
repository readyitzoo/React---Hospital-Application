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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;
	private final int ROLE_ID_PATIENT = 1;


	public UserDto login(CredentialsDto credentialsDto) throws Exception {
		Optional<User> optionalUser = userRepository.findByCnp(credentialsDto.getCnp().toString());
		if (optionalUser.isEmpty()) {
			throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
			UserDto result = userMapper.toUserDto(user);
			result.setCnp(Long.parseLong(user.getCnp()));
			return result;
		}
		throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	}

	public UserDto register(SignUpDto userDto) throws Exception {

		Optional<User> optionalUser = userRepository.findByCnp(userDto.getCnp().toString());

		if (optionalUser.isPresent()) {
			throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
		}

		User user = userMapper.signUpToUser(userDto);
		user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
		user.setCnp(userDto.getCnp().toString());
		user.setRoleId(ROLE_ID_PATIENT);
		user.setRole(UserRole.PATIENT);

		User savedUser = userRepository.save(user);
		UserDto result = userMapper.toUserDto(savedUser);
		result.setCnp(Long.parseLong(userDto.getCnp().toString()));

		return result;
	}


}