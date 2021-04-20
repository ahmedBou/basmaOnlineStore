package com.ecom.appSf;

import com.ecom.appSf.controllers.UserController;
import com.ecom.appSf.dto.AddressDto;
import com.ecom.appSf.dto.UserDto;
import com.ecom.appSf.requests.UserRequest;
import com.ecom.appSf.services.UserService;
import org.apache.tomcat.jni.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private UserService userService;
	private UserDto userDto = new UserDto();

	AddressDto address = new AddressDto();

	@Test
	void contextLoads() {
		address.setCity("city");
		address.setCountry("country");
		userDto.setFirstName("ahmed");
		userDto.setLastName("bout");
		userDto.setAddress(address);
		userDto.setEmail("ahmed01@gmail.com");
		userDto.setPassword("user123123");

		userService.updateUser("gIxy8q4yzizCNIhgMWok50667xSMnPAi", userDto);
	}



}
