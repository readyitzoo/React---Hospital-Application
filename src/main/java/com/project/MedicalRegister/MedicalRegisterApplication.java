package com.project.MedicalRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableWebMvc
public class MedicalRegisterApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		SpringApplication.run(MedicalRegisterApplication.class, args);
	}

}
