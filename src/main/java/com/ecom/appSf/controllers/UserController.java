package com.ecom.appSf.controllers;


import com.ecom.appSf.dto.UserDto;
import com.ecom.appSf.requests.UserRequest;
import com.ecom.appSf.responses.UserResponse;
import com.ecom.appSf.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value ="/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
									   @RequestParam(value = "limit", defaultValue = "20") int limit) {
		List<UserResponse> listUsers = new ArrayList<UserResponse>();

		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserResponse user = new UserResponse();
			BeanUtils.copyProperties(userDto, user);
			listUsers.add(user);
		}

		return listUsers;
	}

	@GetMapping(path = "/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
		UserDto userDto = userService.getUserByUserId(userId);

		UserResponse userResponse = new UserResponse();

		BeanUtils.copyProperties(userDto, userResponse);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);

		UserDto createUser = userService.createUser(userDto);

		UserResponse userResponse = modelMapper.map(createUser, UserResponse.class);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest) {
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userRequest, userDto);
		UserDto updateUser = userService.updateUser(userId, userDto);

		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(updateUser, userResponse);

		System.out.println("UserDto: " + updateUser);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) {
		userService.deleteUser(userId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
