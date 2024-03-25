package com.cafe.com.cafe.management;

import com.cafe.com.cafe.management.Entity.BillEntity;
import com.cafe.com.cafe.management.Entity.UserEntity;
import com.cafe.com.cafe.management.Passwordgenerator.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@SpringBootApplication
public class Application {
	@Bean
	public ModelMapper modelmapper(){
		return new ModelMapper();
	}
	@Bean
	public UserEntity userEntity() {
		return new UserEntity();
	}
	@Bean
	public BillEntity billEntity(){ return  new BillEntity();
	}

	@Bean
	public PasswordGenerator passwordGenerator(){
		return new PasswordGenerator();
	}



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
