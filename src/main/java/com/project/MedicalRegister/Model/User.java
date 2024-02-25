package com.project.MedicalRegister.Model;

import com.project.MedicalRegister.DTO.HospitalAdminDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(of = "user_id")
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "CNP"))
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;

	@Column(name = "cnp", nullable = false)
	private String cnp;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(nullable = false)
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "phone_number")
	private Long phoneNumber;

	@Column(name = "role_id")
	private Integer roleId;

//	public User(Long cnp, String password, UserRole role) {
//		this.cnp = cnp;
//		this.password = password;
//		this.role = role;
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if (this.role == UserRole.HOSPITAL_ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_HOSPITAL_ADMIN"));
		}
		if (this.role == UserRole.DOCTOR) {
			return List.of(new SimpleGrantedAuthority("ROLE_DOCTOR"));
		}
		if (this.role == UserRole.RECEPIONIST) {
			return List.of(new SimpleGrantedAuthority("ROLE_RECEPTIONIST"));
		}
		return List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));
	}

	@Override
	public String getUsername() {
		return cnp.toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
	