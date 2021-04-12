package com.ecom.appSf.services;

import com.ecom.appSf.dto.AddressDto;
import com.ecom.appSf.dto.UserDto;
import com.ecom.appSf.entities.UserEntity;
import com.ecom.appSf.exceptions.UserException;
import com.ecom.appSf.repositories.UserRepository;
import com.ecom.appSf.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private Utils utils;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity checkUserEmail = userRepository.findByEmail(userDto.getEmail());

        if (checkUserEmail != null) throw new UserException("Email is already exists !");

        AddressDto addressDto = userDto.getAddress();
        addressDto.setUser(userDto);
        userDto.setAddress(addressDto);

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity =  modelMapper.map(userDto, UserEntity.class);

        userEntity.setUserId(utils.generatePublicUserId(32));

        userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity1 = userRepository.save(userEntity);

        UserDto userDto1 = modelMapper.map(userEntity1, UserDto.class);

        return userDto1;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException("Email not found !");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException("Email: " + email + " not found !");

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException("User with Id: " + userId + " not found !");

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException("User with Id: " + userId + " not found !");

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity userUpdated = userRepository.save(userEntity);

        UserDto userDto1 = new UserDto();

        BeanUtils.copyProperties(userUpdated, userDto1);

        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException("User with Id: " + userId + " not found !");

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        if (page > 0) page -= 1;

        List<UserDto> usersDto = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, limit);

        Page<UserEntity> userPage = userRepository.findAll(pageable);


        List<UserEntity> users = userPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto user = new UserDto();
            BeanUtils.copyProperties(userEntity, user);

            usersDto.add(user);
        }

        return usersDto;
    }
}
