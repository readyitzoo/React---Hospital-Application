package com.project.MedicalRegister.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

	private Long user_id;
	private Long cnp;
	private String firstName;
	private String lastName;
	private String token;
	private Integer roleId;
	private String role;
	private String email;
	private String password;
	private Long phoneNumber;

}