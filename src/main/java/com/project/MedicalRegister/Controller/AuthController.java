package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.Config.UserAuthenticationProvider;
import com.project.MedicalRegister.DTO.CredentialsDto;
import com.project.MedicalRegister.DTO.SignUpDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Mappers.UserMapper;
import com.project.MedicalRegister.Model.Notification;
import com.project.MedicalRegister.Model.User;
import com.project.MedicalRegister.Model.UserRole;
import com.project.MedicalRegister.Repository.NotificationRepo;
import com.project.MedicalRegister.Repository.RoleUsersRepository;
import com.project.MedicalRegister.Service.UserService;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    private final RoleUsersRepository roleUsersRepository;
    private final NotificationRepo notificationRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final int ROLE_ID_PATIENT = 1;

    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/login")
    public Object login(@RequestBody @Valid CredentialsDto credentialsDto) throws Exception {
        System.out.println("USER LOGGING IN");
        System.out.println(credentialsDto.getCnp() + " " + credentialsDto.getPassword());
        UserDto userDto = userService.login(credentialsDto);
        User user = userMapper.toUser(userDto);
        System.out.println(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        System.out.println(user.getPassword());
        user.setCnp(userDto.getCnp().toString());
        user.setRoleId(ROLE_ID_PATIENT);
        user.setRole(UserRole.PATIENT);

        userDto.setToken(userAuthenticationProvider.createToken(user));
        System.out.println("USER LOGGED IN");
        Integer roleId = userDto.getRoleId();
        String roleName = roleUsersRepository.findByRoleId(roleId).get().getRoleName();

        List<String> messages = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();
        if (roleName.equals("Patient")) {
            notifications = notificationRepo.findAllByPatientId(userDto.getUser_id()).get();
        }
        else if (roleName.equals("Doctor")) {
            notifications = notificationRepo.findAllByDoctorId(userDto.getUser_id()).get();
        }

        for (Notification notification : notifications) {
            messages.add(notification.getMessage());
            notificationRepo.delete(notification);
        }
        Map<String, Object> response = Map.of("user", userDto, "role", roleName, "notification", messages);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user) throws Exception {
        UserDto createdUser = userService.register(user);
        User convertedUser = userMapper.toUser(createdUser);
        convertedUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(createdUser.getPassword())));
        convertedUser.setCnp(createdUser.getCnp().toString());
        convertedUser.setRoleId(ROLE_ID_PATIENT);
        convertedUser.setRole(UserRole.PATIENT);
        
        String token = userAuthenticationProvider.createToken(convertedUser);
        createdUser.setToken(token);
        System.out.println("Token: " + token);
        // ???
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUser_id())).body(createdUser);
    }

}
