package com.skillhub.TaskProject.serviceImp;

import com.skillhub.TaskProject.entity.Users;
import com.skillhub.TaskProject.payload.UserDto;
import com.skillhub.TaskProject.repository.UserRepository;
import com.skillhub.TaskProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        //User Dto is not an entity of users
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users user = userDtoToEntity(userDto); // Converted userDto to Users
        Users savedUser = userRepository.save(user);
        return entityToUserDto(savedUser);
    }
    private Users userDtoToEntity(UserDto userDto){
        Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        return users;
    }

    private UserDto entityToUserDto(Users savedUser){
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPassword(savedUser.getPassword());
        return userDto;
    }
}
