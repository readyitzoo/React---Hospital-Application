package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.ReceptionistDto;
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
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReceptionistService {

    private final UserRepository userRepository;
    private final MedicalCenterRepo medicalCenterRepository;
    private final ContractRepository contractRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final int ROLE_ID_RECEPTIONIST = 3;

    @Transactional
    public UserDto addReceptionist(ReceptionistDto receptionist) throws Exception {
        Optional<User> optionalUser = userRepository.findByCnp(Encryption.encrypt(receptionist.getCnp().toString()));
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        //	insert in Users table
        User user = userMapper.ReceptionistToUser(receptionist);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(receptionist.getPassword())));
        user.setCnp(Encryption.encrypt(receptionist.getCnp().toString()));
        user.setRoleId(ROLE_ID_RECEPTIONIST);
        user.setRole(UserRole.RECEPIONIST);

        User savedUser = userRepository.save(user);

        // insert in ContractMedicalCenter table
        ContractMedicalCenter contract = new ContractMedicalCenter();
        contract.setUserId(savedUser.getUser_id());
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(receptionist.getHospitalName());
        if (!optionalMedicalCenter.isPresent()) {
            userRepository.delete(savedUser);
            throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);

        } else {
            Long medicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
            contract.setMedicalCenterId(medicalCenterId);

            contractRepository.save(contract);
        }

        UserDto result = userMapper.toUserDto(savedUser);
        result.setCnp(Long.parseLong(Encryption.decrypt(savedUser.getCnp())));
        return result;
    }

    @Transactional
    public void deleteReceptionist(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (!optionalUser.isPresent() || !optionalContract.isPresent()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            contractRepository.delete(optionalContract.get());
            userRepository.delete(optionalUser.get());
        }

    }

    public Object getReceptionist(Long id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (!optionalUser.isPresent() || !optionalContract.isPresent()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            ReceptionistDto receptionistDto = userMapper.toReceptionistDto(optionalUser.get());
            receptionistDto.setCnp(Long.parseLong(Encryption.decrypt(optionalUser.get().getCnp())));
            receptionistDto.setHospitalName(medicalCenterRepository.findById(optionalContract.get().getMedicalCenterId()).get().getName());
            Map<String, Object> json = Map.of("userId", id,"receptionist", receptionistDto);
            return json;
        }
    }

    @Transactional
    public void updateReceptionist(Long id, ReceptionistDto newReceptionistDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<ContractMedicalCenter> optionalContract = contractRepository.findByUserId(id);
        if (!optionalUser.isPresent() || !optionalContract.isPresent()) {
            throw new AppException("User does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            User updatedUser = optionalUser.get();
            updatedUser.setFirstName(newReceptionistDto.getFirstName());
            updatedUser.setLastName(newReceptionistDto.getLastName());
            updatedUser.setEmail(newReceptionistDto.getEmail());
            updatedUser.setCnp(Encryption.encrypt(newReceptionistDto.getCnp().toString()));
            updatedUser.setPhoneNumber(newReceptionistDto.getPhoneNumber());
            updatedUser.setRoleId(ROLE_ID_RECEPTIONIST);
            updatedUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(newReceptionistDto.getPassword())));

            userRepository.save(updatedUser);

            ContractMedicalCenter newContract = optionalContract.get();
            Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(newReceptionistDto.getHospitalName());
            if (!optionalMedicalCenter.isPresent()) {
                throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);
            } else {
                Long newMedicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
                newContract.setMedicalCenterId(newMedicalCenterId);

                contractRepository.save(newContract);
            }
        }
    }

    public List<Object> getAllByHospitalName(String hospitalName) throws Exception {
        List<Object> result = new ArrayList<>();
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByName(hospitalName);
        if(optionalMedicalCenter.isEmpty()){
            throw new AppException("Hospital does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            Long medicalCenterId = optionalMedicalCenter.get().getMedicalCenterId();
            List<ContractMedicalCenter> contracts = contractRepository.findAllByMedicalCenterId(medicalCenterId);
            for (ContractMedicalCenter contract : contracts) {
                Optional<User> optionalUser = userRepository.findById(contract.getUserId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    if (user.getRoleId() == ROLE_ID_RECEPTIONIST) {
                        ReceptionistDto receptionistDto = userMapper.toReceptionistDto(user);
                        receptionistDto.setCnp(Long.parseLong(Encryption.decrypt(user.getCnp())));
                        receptionistDto.setHospitalName(medicalCenterRepository.findById(contract.getMedicalCenterId()).get().getName());
                        Map<String, Object> json = Map.of("userId", user.getUser_id(),"receptionist", receptionistDto);
                        result.add(json);
                    }
                }
            }
        }
        Collections.sort(result, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
               String lastName1 = ((ReceptionistDto)((Map<String, Object>)o1).get("receptionist")).getLastName();
               String lastName2 = ((ReceptionistDto)((Map<String, Object>)o2).get("receptionist")).getLastName();
               String firstName1 = ((ReceptionistDto)((Map<String, Object>)o1).get("receptionist")).getFirstName();
                String firstName2 = ((ReceptionistDto)((Map<String, Object>)o2).get("receptionist")).getFirstName();
                if(lastName1.compareTo(lastName2) == 0){
                    return firstName1.compareTo(firstName2);
                }
                else {
                    return lastName1.compareTo(lastName2);
                }
            }
        });
        return result;
    }
}