package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());

                    // assign roles
                    Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new ResourceNotFoundException("Role ADMIN not found"));
                    Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("Role USER not found"));
                    user.getRoles().add(adminRole);
                    user.getRoles().add(userRole);

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistException(request.getEmail() + " already exists"));
    }
    @Override
    public User signUp(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());

                    // assign user role
                    Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("Role USER not found"));
                    user.getRoles().add(userRole);

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistException(request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("user not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete, ()->{
                    throw new ResourceNotFoundException("user not found");
                }
        );
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticationUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
