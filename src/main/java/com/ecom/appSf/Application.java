package com.ecom.appSf;

import com.ecom.appSf.shared.Beans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Beans springApplicationContext() {
		return new Beans();
	}

}
