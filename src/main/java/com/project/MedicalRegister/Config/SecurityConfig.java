package com.project.MedicalRegister.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	@Autowired
	JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http 	.csrf(csrf -> csrf.disable())
				.exceptionHandling(auth -> auth.authenticationEntryPoint(userAuthenticationEntryPoint))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/admin/**").permitAll()

//						// patient
//						.requestMatchers(HttpMethod.GET, "/api/patient/**").hasAnyRole("PATIENT","DOCTOR")
//						.requestMatchers(HttpMethod.PUT, "/api/patient/**").hasAnyRole("PATIENT")
//						.requestMatchers(HttpMethod.DELETE, "/api/patient/**").hasRole("ADMIN")
//
//						// doctor
//						.requestMatchers(HttpMethod.GET, "/api/doctor/**").hasAnyRole("HOSPITAL_ADMIN","PATIENT","RECEPTIONIST")
//						.requestMatchers(HttpMethod.POST, "/api/doctor/**").hasRole("HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/doctor/**").hasAnyRole("HOSPITAL_ADMIN","DOCTOR")
//						.requestMatchers(HttpMethod.DELETE, "/api/doctor/**").hasRole("HOSPITAL_ADMIN")
//
//						// receptionist
//						.requestMatchers(HttpMethod.GET, "/api/receptionist/**").hasRole("HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.POST, "/api/receptionist/**").hasRole("HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/receptionist/**").hasAnyRole("HOSPITAL_ADMIN","RECEPTIONIST")
//						.requestMatchers(HttpMethod.DELETE, "/api/receptionist/**").hasRole("HOSPITAL_ADMIN")
//
//						// hospital admin
//						.requestMatchers(HttpMethod.GET, "/api/hospitalAdmin/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.POST, "/api/hospitalAdmin/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/hospitalAdmin/**").hasAnyRole("ADMIN","HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.DELETE, "/api/hospitalAdmin/**").hasRole("ADMIN")
//
//						// investigatii
//						.requestMatchers(HttpMethod.GET, "/api/investigation/**").hasAnyRole("HOSPITAL_ADMIN","PATIENT")
//						.requestMatchers(HttpMethod.POST, "/api/investigation/**").hasRole("HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/investigation/**").hasRole("HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.DELETE, "/api/investigation/**").hasRole("HOSPITAL_ADMIN")
//
//						// doctor availability
//						.requestMatchers(HttpMethod.GET, "/api/availability/**").hasAnyRole("DOCTOR","HOSPITAL_ADMIN","PATIENT","RECEPTIONIST")
//						.requestMatchers(HttpMethod.POST, "/api/availability/**").hasAnyRole("DOCTOR","HOSPITAL_ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/availability/**").hasRole("HOSPITAL_ADMIN")
//
//						// programari
//						.requestMatchers(HttpMethod.GET, "/api/appointment/**").hasAnyRole("DOCTOR","PATIENT","RECEPTIONIST")
//						.requestMatchers(HttpMethod.POST, "/api/appointment/**").hasAnyRole("PATIENT","RECEPTIONIST")
//						.requestMatchers(HttpMethod.DELETE, "/api/appointment/**").hasRole("PATIENT")
//
//						// istoric medical
//						.requestMatchers(HttpMethod.GET, "/api/medicalHistory/**").hasAnyRole("PATIENT","DOCTOR")
//						.requestMatchers(HttpMethod.POST, "/api/medicalHistory/**").hasRole("DOCTOR")
//						.requestMatchers(HttpMethod.PUT, "/api/medicalHistory/**").hasRole("DOCTOR")
//						.requestMatchers(HttpMethod.DELETE, "/api/medicalHistory/**").hasRole("DOCTOR")
//
//						// specializari
//						.requestMatchers(HttpMethod.GET, "/api/specialization/**").hasAnyRole("ADMIN","PATIENT")
//						.requestMatchers(HttpMethod.POST, "/api/specialization/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/specialization/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.DELETE, "/api/specialization/**").hasRole("ADMIN")
//
//						// centre medicale
//						.requestMatchers(HttpMethod.GET, "/api/medicalCenter/**").hasAnyRole("ADMIN","PATIENT")
//						.requestMatchers(HttpMethod.POST, "/api/medicalCenter/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.PUT, "/api/medicalCenter/**").hasRole("ADMIN")
//						.requestMatchers(HttpMethod.DELETE, "/api/medicalCenter/**").hasRole("ADMIN")
//
//						// pentru userul neautentificat
//						.requestMatchers(HttpMethod.GET, "/api/medicalCenter/**").permitAll()
//						.requestMatchers(HttpMethod.GET, "/api/investigation/**").permitAll()
//						.requestMatchers(HttpMethod.GET, "/api/investigation/**").permitAll());
//
						.anyRequest().authenticated());


		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}
